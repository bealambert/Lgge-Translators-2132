package compiler.ASMGenerator;

import compiler.*;
import compiler.Lexer.Identifier;
import compiler.Parser.*;
import compiler.Semantic.ExpressionTypeVisitor;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import org.checkerframework.checker.units.qual.A;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

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
        Integer store_index = asmUtils.getFirstDeclarationInsideStoreStable(variable.getIdentifier(), storeTable);
        SymbolTable symbolTable = variable.getSymbolTable();
        Type type = treatSemanticCases.getFirstDeclarationInsideSymbolTable
                (variable.getIdentifier(), symbolTable).accept(ExpressionTypeVisitor.typeCheckingVisitor);
        int mapped_type = asmUtils.mapLoadType.getOrDefault(type.getAttribute(), ALOAD);

        // assumption -> only 2 storeTable (no function inside function but in java it is forbidden)
        if (storeTable.previous == null || (storeTable.storeTable.getOrDefault(variable.getIdentifier().getAttribute(), -1) == -1 && storeTable.previous != null)) {
            String desc = asmUtils.mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A");
            String name = variable.getIdentifier().getAttribute();
            mv.visitFieldInsn(GETSTATIC, this.className, name, desc);
        } else {
            mv.visitVarInsn(mapped_type, store_index);
        }

    }

    @Override
    public void visit(SubExpression subExpression) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ReturnVoid returnVoid) throws SemanticAnalysisException {

    }

    @Override
    public void visit(AssignVariable assignVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(AssignToIndexArray assignToIndexArray) throws SemanticAnalysisException {

        Identifier identifier = assignToIndexArray.getAccessToIndexArray().getIdentifier();
        int array_reference = storeTable.storeTable.get(identifier.getAttribute());
        if (flag != PUTSTATIC) {

            int mapLoadType = asmUtils.mapLoadType.get(identifier.getAttribute());
            int store_index = storeTable.storeTable.get(identifier.getAttribute());
            mv.visitVarInsn(mapLoadType, store_index);
        } else {
            Type type = assignToIndexArray.getAssignmentExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);
            String desc = asmUtils.mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A");
            String name = assignToIndexArray.getAccessToIndexArray().getIdentifier().getAttribute();
            // TODO : change access
            int access = ACC_PUBLIC;
            //cw.visitField(access, name, desc, null, null);
            mv.visitFieldInsn(GETSTATIC, this.className, name, desc);

        }

        assignToIndexArray.getAccessToIndexArray().getArrayOfExpression().accept(this);
        assignToIndexArray.getAssignmentExpression().accept(this);
        Type type = assignToIndexArray.getAssignmentExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);


        //mv.visitVarInsn(asmUtils.mapStoreType.getOrDefault(type.getAttribute(), ASTORE), storeCount);
        //mv.visitVarInsn(asmUtils.mapLoadType.getOrDefault(type.getAttribute(), ALOAD), storeCount);
        storeCount++;

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

    }

    @Override
    public void visit(ArrayInitializerParameter arrayInitializerParameter) throws SemanticAnalysisException {

    }

    @Override
    public void visit(FunctionCallParameter functionCallParameter) throws SemanticAnalysisException {

    }

    @Override
    public void visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ArrayType arrayType) throws SemanticAnalysisException {

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

    }

    @Override
    public void visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException {
        String desc = "[" + asmUtils.mapTypeToASMTypes.get(createArrayVariable.getType().getAttribute());
        String name = createArrayVariable.getVariableIdentifier().getIdentifier().getAttribute();
        cw.visitField(ACC_PUBLIC, name, desc, null, null);
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
            }

            //mv.visitVarInsn(asmUtils.mapStoreType.get(createArrayVariable.getType().getAttribute()), storeCount);
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
            mv.visitVarInsn(ISTORE, storeCount);
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
            int load_value = asmUtils.mapLoadType.getOrDefault(param.getType().getAttribute(), ALOAD);
            mv.visitVarInsn(load_value, i);
            int store_value = asmUtils.mapStoreType.getOrDefault(param.getType().getAttribute(), -1);
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

    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(CreateVariables createVariables) throws SemanticAnalysisException {

    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {
        String keyword = createVoidVariable.getStateKeyword().getAttribute();
        int access = asmUtils.getAccess(keyword);
        String variableName = createVoidVariable.getVariableIdentifier().getIdentifier().getAttribute();
        String desc = asmUtils.mapTypeToASMTypes.getOrDefault(createVoidVariable.getType().getAttribute(), "A");

        if (flag != PUTSTATIC) {
            mv.visitInsn(ACONST_NULL);
            mv.visitVarInsn(ISTORE, storeCount);
        } else {
            access |= ACC_STATIC;
            cw.visitField(access, variableName, desc, null, null);
            //mv.visitFieldInsn(flag, "Test", variableName, desc);
        }
        storeTable.storeTable.put(createVoidVariable.getVariableIdentifier().getIdentifier().getAttribute(), storeCount);
        storeCount++;
    }

    @Override
    public void visit(Expression expression) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ForLoop forLoop) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ForLoopAssignVariable forLoopAssignVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ForLoopCreateVariable forLoopCreateVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(FunctionCall functionCall) throws SemanticAnalysisException {

    }

    @Override
    public void visit(IfCondition ifCondition) throws SemanticAnalysisException {

    }

    @Override
    public void visit(IfElse ifElse) throws SemanticAnalysisException {

    }

    @Override
    public void visit(InitializeRecords initializeRecords) throws SemanticAnalysisException {

    }

    @Override
    public void visit(MethodCall methodCall) throws SemanticAnalysisException {

    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException {

    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Param param) throws SemanticAnalysisException {

        /*Type type = param.getType();
        int loadType = asmUtils.mapLoadType.get(type.getAttribute());

        mv.visitVarInsn(loadType, );*/
    }

    @Override
    public void visit(Reassignment reassignment) throws SemanticAnalysisException {

    }

    @Override
    public void visit(RecordCall recordCall) throws SemanticAnalysisException {

    }

    @Override
    public void visit(RecordParameter recordParameter) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Records records) throws SemanticAnalysisException {

    }

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
            mv.visitIntInsn(BIPUSH, (Integer) values.getSymbol().getAttribute());
        } else if (typeAttribute.equals(Token.RealIdentifier.getName()) || typeAttribute.equals(Token.RealNumber.getName())) {
            mv.visitLdcInsn(((Double) values.getSymbol().getAttribute()).floatValue());
        } else if (typeAttribute.equals(Token.Boolean.getName()) || typeAttribute.equals(Token.BooleanIdentifier.getName())) {
            mv.visitLdcInsn(Boolean.parseBoolean((String) values.getSymbol().getAttribute()));
        } else if (typeAttribute.equals(Token.Strings.getName()) || typeAttribute.equals(Token.StringIdentifier.getName())) {
            mv.visitLdcInsn(values.getSymbol().getAttribute());
        }

    }

    @Override
    public void visit(WhileLoop whileLoop) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Identifier identifier) throws SemanticAnalysisException {

    }

    @Override
    public void visit(BinaryTree binaryTree) throws SemanticAnalysisException {

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
