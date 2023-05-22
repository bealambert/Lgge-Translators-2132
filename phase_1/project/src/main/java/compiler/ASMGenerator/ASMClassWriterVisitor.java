package compiler.ASMGenerator;

import compiler.*;
import compiler.Lexer.Identifier;
import compiler.Parser.*;
import compiler.Parser.Type;
import compiler.Semantic.ExpressionTypeVisitor;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import org.checkerframework.checker.units.qual.A;
import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static org.objectweb.asm.Opcodes.*;

public class ASMClassWriterVisitor implements SemanticVisitor {

    Stack<MethodVisitor> methodVisitorStack;
    Stack<Integer> flags;
    MethodVisitor mv;
    ClassWriter cw;
    int flag;
    int storeCount;
    ASMUtils asmUtils;
    StoreTable storeTable;
    String className;

    public ASMClassWriterVisitor() {
        storeCount = 1;
        methodVisitorStack = new Stack<>();
        flags = new Stack<>();
        asmUtils = new ASMUtils();
        storeTable = new StoreTable(null);
    }

    public void setCw(ClassWriter cw, String className) {
        this.cw = cw;
        this.className = className;
    }


    public void addMethodVisitor(MethodVisitor methodVisitor, int flag) {
        methodVisitorStack.add(methodVisitor);
        flags.add(flag);

        this.mv = methodVisitor;
        this.flag = flag;
    }


    public void visit(Variable variable) throws SemanticAnalysisException {
        Pair pair = asmUtils.getFirstDeclarationInsideStoreStable(variable.getIdentifier(), storeTable);
        Integer store_index = pair.getValue();
        SymbolTable symbolTable = variable.getSymbolTable();
        Type type = treatSemanticCases.getFirstDeclarationInsideSymbolTable
                (variable.getIdentifier(), symbolTable).accept(ExpressionTypeVisitor.typeCheckingVisitor);
        int mapped_type = ALOAD;
        if (!(type instanceof ArrayType)) {
            mapped_type = asmUtils.mapLoadType.getOrDefault(type.getAttribute(), ALOAD);
        }

        // assumption -> only 2 storeTable (no function inside function but in java it is forbidden)
        if (pair.getStaticField()) {
            String desc = asmUtils.mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A");
            String name = variable.getIdentifier().getAttribute();
            mv.visitFieldInsn(GETSTATIC, this.className, name, desc);
        } else {
            mv.visitVarInsn(mapped_type, store_index);
        }

    }

    @Override
    public void visit(SubExpression subExpression) throws SemanticAnalysisException {
        BinaryTree binaryTree = subExpression.getSubExpression().getMyTree();
        binaryTree.getRoot().accept(this);
    }

    @Override
    public void visit(ReturnVoid returnVoid) throws SemanticAnalysisException {
        mv.visitInsn(RETURN);
    }

    @Override
    public void visit(AssignVariable assignVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(AssignToIndexArray assignToIndexArray) throws SemanticAnalysisException {

        Identifier identifier = assignToIndexArray.getAccessToIndexArray().getIdentifier();
        Pair pair = asmUtils.getFirstDeclarationInsideStoreStable
                (assignToIndexArray.getAccessToIndexArray().getIdentifier(), storeTable);

        Type type = assignToIndexArray.getAssignmentExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);

        if (!pair.getStaticField()) {

            int mapLoadType = ALOAD; //asmUtils.mapLoadType.get(type.getAttribute());
            int store_index = storeTable.storeTable.get(identifier.getAttribute());
            mv.visitVarInsn(mapLoadType, store_index);
        } else {
            String desc = "[" + asmUtils.mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A");
            String name = assignToIndexArray.getAccessToIndexArray().getIdentifier().getAttribute();
            //cw.visitField(access, name, desc, null, null);
            mv.visitFieldInsn(GETSTATIC, this.className, name, desc);

        }

        assignToIndexArray.getAccessToIndexArray().getArrayOfExpression().accept(this);
        assignToIndexArray.getAssignmentExpression().accept(this);

        int array_store = asmUtils.mapAssignToIndex.getOrDefault(type.getAttribute(), -1);
        mv.visitInsn(array_store);
    }

    @Override
    public void visit(AssignToRecordAttribute assignToRecordAttribute) throws SemanticAnalysisException {

    }

    @Override
    public void visit(AssignToRecordAttributeAtIndex assignToRecordAttributeAtIndex) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ExpressionParameter expressionParameter) throws SemanticAnalysisException {
        // handled in the createProcedure
    }

    @Override
    public void visit(ArrayInitializerParameter arrayInitializerParameter) throws SemanticAnalysisException {
        // handled in the createProcedure
    }

    @Override
    public void visit(FunctionCallParameter functionCallParameter) throws SemanticAnalysisException {
        // handled in the createProcedure
    }

    @Override
    public void visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {

        SymbolTable symbolTable = accessToIndexArray.getIdentifier().getSymbolTable();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(accessToIndexArray.getIdentifier(), symbolTable);
        Type array_type = astNode.accept(ExpressionTypeVisitor.typeCheckingVisitor);

        Pair pair = asmUtils.getFirstDeclarationInsideStoreStable(accessToIndexArray.getIdentifier(), storeTable);
        String name = accessToIndexArray.getIdentifier().getName();
        String desc = "[" + asmUtils.mapTypeToASMTypes.get(array_type.getAttribute());
        if (pair.getStaticField()) {
            mv.visitFieldInsn(GETSTATIC, this.className, name, desc);
        } else {
            int mapLoadType = ALOAD; //asmUtils.mapLoadType.get(accessToIndexArray.getIdentifier().getAttribute());
            int store_index = storeTable.storeTable.get(accessToIndexArray.getIdentifier().getAttribute());
            mv.visitVarInsn(mapLoadType, store_index);
        }
        accessToIndexArray.getArrayOfExpression().accept(this);
        int load_instruction = asmUtils.mapLoadArray.getOrDefault(array_type.getAttribute(), AALOAD);
        mv.visitInsn(load_instruction);
    }

    @Override
    public void visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException {
        // no need to implement
    }

    @Override
    public void visit(ArrayType arrayType) throws SemanticAnalysisException {
        // handled
    }

    @Override
    public void visit(Block block) throws SemanticAnalysisException {
        ArrayList<ASTNode> astNodes = block.getAttribute();
        for (int i = 0; i < astNodes.size(); i++) {
            ASTNode astNode = astNodes.get(i);
            astNode.accept(this);
        }
    }

    @Override
    public void visit(Condition condition) throws SemanticAnalysisException {
        BinaryTree binaryTree = condition.getArrayOfExpression().getMyTree();
        binaryTree.getRoot().accept(this);
    }

    @Override
    public void visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException {
        String desc = "[" + asmUtils.mapTypeToASMTypes.get(createArrayVariable.getType().getAttribute());
        String name = createArrayVariable.getVariableIdentifier().getIdentifier().getAttribute();
        if (flag == PUTSTATIC) {
            cw.visitField(ACC_PUBLIC | ACC_STATIC, name, desc, null, null).visitEnd();
        }
        createArrayVariable.getArrayInitializer().getArraySize().accept(this);
        int newArrayValue = asmUtils.mapArrayType.getOrDefault(createArrayVariable.getType().getAttribute(), -1);
        if (newArrayValue == -1) {
            // check type
        } else {
            mv.visitIntInsn(NEWARRAY, newArrayValue);
            if (flag == PUTSTATIC) {
                mv.visitFieldInsn(flag, this.className,
                        name,
                        desc);
            } else {
                int array_store = asmUtils.mapStoreType.get(createArrayVariable.getType().getAttribute());
                mv.visitVarInsn(ASTORE, storeCount);
            }
        }

        storeTable.storeTable.put(createArrayVariable.getVariableIdentifier().getIdentifier().getAttribute(), storeCount);
        storeCount++;

    }

    @Override
    public void visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException {

        String keyword = createExpressionVariable.getStateKeyword().getAttribute();
        int access = asmUtils.getAccess(keyword);
        String variableName = createExpressionVariable.getVariableIdentifier().getIdentifier().getAttribute();
        String desc = asmUtils.mapTypeToASMTypes.getOrDefault(createExpressionVariable.getType().getAttribute(), "A");

        if (flag != PUTSTATIC) {
            BinaryTree binaryTree = createExpressionVariable.getArrayOfExpression().getMyTree();
            binaryTree.getRoot().accept(this);
            Type type = binaryTree.accept(ExpressionTypeVisitor.typeCheckingVisitor);
            int store_type = asmUtils.mapStoreType.get(type.getAttribute());
            mv.visitVarInsn(store_type, storeCount);
        } else {
            access |= ACC_STATIC;
            cw.visitField(access, variableName, desc, null, null);
            BinaryTree binaryTree = createExpressionVariable.getArrayOfExpression().getMyTree();
            binaryTree.getRoot().accept(this);
            mv.visitFieldInsn(flag, this.className, variableName, desc);

        }
        storeTable.storeTable.put(createExpressionVariable.getVariableIdentifier().getIdentifier().getAttribute(), storeCount);
        storeCount++;


    }

    @Override
    public void visit(CreateProcedure createProcedure) throws SemanticAnalysisException {
        storeTable.storeTable.clear();
        String desc = asmUtils.createDescFromParam(createProcedure.getParams(), createProcedure.getReturnType());
        MethodVisitor methodVisitor = cw.visitMethod(ACC_STATIC, createProcedure.getProcedureName().getAttribute(), desc, null, null);
        this.addMethodVisitor(methodVisitor, PUTFIELD);
        mv.visitCode();
        ArrayList<Param> params = createProcedure.getParams();
        for (int i = 0; i < params.size(); i++) {
            Param param = params.get(i);
            Type paramType = param.getType();
            int load_value = ALOAD;
            int store_value = ASTORE;
            if (!(paramType instanceof ArrayType)) {
                load_value = asmUtils.mapLoadType.getOrDefault(param.getType().getAttribute(), ALOAD);
                store_value = asmUtils.mapStoreType.getOrDefault(param.getType().getAttribute(), -1);
            }
            mv.visitVarInsn(load_value, i);
            mv.visitVarInsn(store_value, i);
            storeTable.storeTable.put(param.getIdentifier().getAttribute(), i);
        }
        storeCount = params.size();
        Block body = createProcedure.getBody();
        body.accept(this);
        mv.visitInsn(asmUtils.mapReturnType.getOrDefault(createProcedure.getReturnType().getAttribute(), ARETURN));
        mv.visitMaxs(-1, -1);
        mv.visitEnd();
        methodVisitorStack.pop();
        flags.pop();
    }

    @Override
    public void visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException {
        // if enough time
    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {
        // not used anymore
    }

    @Override
    public void visit(CreateVariables createVariables) throws SemanticAnalysisException {
        // parent class
    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {
        String keyword = createVoidVariable.getStateKeyword().getAttribute();
        int access = asmUtils.getAccess(keyword);
        String variableName = createVoidVariable.getVariableIdentifier().getIdentifier().getAttribute();
        String desc = asmUtils.mapTypeToASMTypes.getOrDefault(createVoidVariable.getType().getAttribute(), "A");

        if (flag != PUTSTATIC) {
            storeTable.storeTable.put(createVoidVariable.getVariableIdentifier().getIdentifier().getAttribute(), -1);
        } else {
            access |= ACC_STATIC;
            cw.visitField(access, variableName, desc, null, null);
            //mv.visitFieldInsn(flag, "Test", variableName, desc);
        }

    }

    @Override
    public void visit(Expression expression) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(ForLoop forLoop) throws SemanticAnalysisException {
        // parent class
    }

    @Override
    public void visit(ForLoopAssignVariable forLoopAssignVariable) throws SemanticAnalysisException {

        Label startLabel = new Label();
        Label endLabel = new Label();

        OperatorAdd operatorAdd = new OperatorAdd();
        OperatorLessThan operatorLessThan = new OperatorLessThan();
        Type type = forLoopAssignVariable.getStart().accept(ExpressionTypeVisitor.typeCheckingVisitor);


        forLoopAssignVariable.getStart().accept(this);
        mv.visitVarInsn(ISTORE, storeCount);
        storeTable.storeTable.put(forLoopAssignVariable.getIdentifier().getAttribute(), storeCount);
        int loopIdentifierStoreCount = storeCount;
        storeCount++;

        mv.visitLabel(startLabel);

        forLoopAssignVariable.getEnd().accept(this);
        mv.visitVarInsn(ILOAD, loopIdentifierStoreCount);
        operatorLessThan.accept(makeOperationVisitor, type, mv);


        mv.visitJumpInsn(Opcodes.IFEQ, endLabel); // Branch to endLabel if condition is false (0)

        forLoopAssignVariable.getBody().accept(this); // Generate loop body

        mv.visitVarInsn(ILOAD, loopIdentifierStoreCount);
        forLoopAssignVariable.getIncrementBy().accept(this);
        //mv.visitIincInsn(loopIdentifierStoreCount, );
        operatorAdd.accept(makeOperationVisitor, type, mv);
        mv.visitVarInsn(ISTORE, loopIdentifierStoreCount);


        mv.visitJumpInsn(Opcodes.GOTO, startLabel); // Go back to the start of the loop

        mv.visitLabel(endLabel);
    }

    @Override
    public void visit(ForLoopCreateVariable forLoopCreateVariable) throws SemanticAnalysisException {
        // for var i i=1 to 100 by 2
    }

    @Override
    public void visit(FunctionCall functionCall) throws SemanticAnalysisException {
        // to implement
        ArrayList<ArrayOfExpression> arrayOfExpressions = functionCall.getParams();
        for (int i = 0; i < arrayOfExpressions.size(); i++) {
            arrayOfExpressions.get(i).accept(this);
        }
        // Make the function call
        SymbolTable symbolTable = functionCall.getSymbolTable();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionCall.getIdentifier(), symbolTable);//(CreateProcedure) symbolTable.getSymbolTable()(functionCall.getIdentifier().getAttribute());
        String desc = asmUtils.createDescFromParam(createProcedure.getParams(), createProcedure.getReturnType());
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, functionCall.getIdentifier().getAttribute(), desc, false);

    }

    @Override
    public void visit(IfCondition ifCondition) throws SemanticAnalysisException {
        ifCondition.getCondition().getArrayOfExpression().accept(this);

        Label ifLabel = new Label();
        Label endLabel = new Label();

        mv.visitJumpInsn(IFEQ, endLabel);
        mv.visitLabel(ifLabel);

        // Visit the if block
        ifCondition.getIfBlock().accept(this);

        // Visit the end label
        mv.visitLabel(endLabel);
    }

    @Override
    public void visit(IfElse ifElse) throws SemanticAnalysisException {
        ifElse.getCondition().getArrayOfExpression().accept(this);

        Label ifLabel = new Label();
        Label elseLabel = new Label();
        Label endLabel = new Label();

        mv.visitJumpInsn(IFEQ, elseLabel);

        mv.visitLabel(ifLabel);
        ifElse.getIfBlock().accept(this);
        mv.visitJumpInsn(GOTO, endLabel);

        mv.visitLabel(elseLabel);
        ifElse.getElseBlock().accept(this);

        mv.visitLabel(endLabel);
    }

    @Override
    public void visit(InitializeRecords initializeRecords) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(MethodCall methodCall) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(Param param) throws SemanticAnalysisException {
        // everything is done inside the createProcedure
    }

    @Override
    public void visit(Reassignment reassignment) throws SemanticAnalysisException {
        Pair pair = asmUtils.getFirstDeclarationInsideStoreStable(reassignment.getIdentifier(), storeTable);
        Type type = reassignment.getArrayOfExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);

        if (pair.getStaticField()) {
            String desc = "";
            if (type instanceof ArrayType) {
                desc = desc + "[";
            }
            desc = desc + asmUtils.mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A");
            reassignment.getArrayOfExpression().accept(this);
            mv.visitFieldInsn(PUTSTATIC, className, reassignment.getIdentifier().getAttribute(), desc);
        } else {
            reassignment.getArrayOfExpression().accept(this);
            int store_type = asmUtils.mapStoreType.get(type.getAttribute());
            int index_storage = pair.getValue();
            if (index_storage == -1) {
                // create void variable didn't do anything because it's a local variable
                // need to create the object
                index_storage = storeCount;


            }
            mv.visitVarInsn(store_type, index_storage);
            storeTable.storeTable.put(reassignment.getIdentifier().getAttribute(), storeCount);
            storeCount++;
        }

    }

    @Override
    public void visit(RecordCall recordCall) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(RecordParameter recordParameter) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(Records records) throws SemanticAnalysisException {
        //
    }

    /*Type type = param.getType();
    int loadType = asmUtils.mapLoadType.get(type.getAttribute());

    mv.visitVarInsn(loadType, );*/
    @Override
    public void visit(ReturnStatement returnStatement) throws SemanticAnalysisException {
        BinaryTree binaryTree = returnStatement.getArrayOfExpression().getMyTree();
        binaryTree.getRoot().accept(this);

        Type type = binaryTree.getRoot().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        int returnType = asmUtils.mapReturnType.getOrDefault(type.getAttribute(), ARETURN);
        mv.visitInsn(returnType);

    }

    @Override
    public void visit(Type type) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Values values) throws SemanticAnalysisException {

        Type type = values.getType();
        String typeAttribute = type.getAttribute();

        if (typeAttribute.equals(Token.IntIdentifier.getName()) || typeAttribute.equals(Token.NaturalNumber.getName())) {
            Integer value = (Integer) values.getSymbol().getAttribute();
            mv.visitLdcInsn(value);
        } else if (typeAttribute.equals(Token.RealIdentifier.getName()) || typeAttribute.equals(Token.RealNumber.getName())) {
            mv.visitLdcInsn(((Double) values.getSymbol().getAttribute()).floatValue());
        } else if (typeAttribute.equals(Token.Boolean.getName()) || typeAttribute.equals(Token.BooleanIdentifier.getName())) {
            if (values.getSymbol().getAttribute().equals("0")) {
                mv.visitInsn(ICONST_0);
            } else {
                mv.visitInsn(ICONST_1);
            }

        } else if (typeAttribute.equals(Token.Strings.getName()) || typeAttribute.equals(Token.StringIdentifier.getName())) {
            mv.visitLdcInsn(values.getSymbol().getAttribute());
        }

    }

    @Override
    public void visit(WhileLoop whileLoop) throws SemanticAnalysisException {

        Label startLabel = new Label();
        Label endLabel = new Label();

        mv.visitLabel(startLabel);

        whileLoop.getCondition().accept(this);
        mv.visitJumpInsn(Opcodes.IFEQ, endLabel); // Branch to endLabel if condition is false (0)

        whileLoop.getBody().accept(this); // Generate loop body

        mv.visitJumpInsn(Opcodes.GOTO, startLabel); // Go back to the start of the loop

        mv.visitLabel(endLabel);
    }

    @Override
    public void visit(Identifier identifier) throws SemanticAnalysisException {

    }

    @Override
    public void visit(BinaryTree binaryTree) throws SemanticAnalysisException {
        //binaryTree.getRoot().accept(this);
    }

    @Override
    public void visit(MyNode myNode) throws SemanticAnalysisException {
        if (myNode.isLeaf()) {
            myNode.getValue().accept(this);
        } else {
            // IF ELSE
            myNode.getLeft().accept(this);
            myNode.getRight().accept(this);

            myNode.getValue().accept(makeOperationVisitor, myNode.getLeft().accept(ExpressionTypeVisitor.typeCheckingVisitor), mv);
        }
    }

    @Override
    public void visit(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException {
        BinaryTree binaryTree = arrayOfExpression.getMyTree();
        binaryTree.getRoot().accept(this);
    }

    public void visit(OperatorAdd operatorAdd, MyNode myNode) {
        /*if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
            mv.visitInsn(IADD);
        }
        else {
            mv.visitInsn(FADD);
        }*/
    }
}
