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
        storeCount = 2;
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
        mv.visitVarInsn(mapped_type, store_index);

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

    }

    @Override
    public void visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException {

        String keyword = createExpressionVariable.getStateKeyword().getAttribute();
        int access = asmUtils.getAccess(keyword);
        String variableName = createExpressionVariable.getVariableIdentifier().getIdentifier().getAttribute();
        String desc = asmUtils.mapTypeToASMTypes.getOrDefault(createExpressionVariable.getType().getAttribute(), "A");

        if (flag != PUTSTATIC) {
            mv.visitVarInsn(ALOAD, 0);
        } else {
            access |= ACC_STATIC;
            cw.visitField(access, variableName, desc, null, null);
        }

        BinaryTree binaryTree = createExpressionVariable.getArrayOfExpression().getMyTree();
        binaryTree.getRoot().accept(this);
        mv.visitFieldInsn(PUTSTATIC, "Test", variableName, desc);

    }

    @Override
    public void visit(CreateProcedure createProcedure) throws SemanticAnalysisException {

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
        Block body = createProcedure.getBody();
        body.accept(this);
        mv.visitInsn(asmUtils.mapReturnType.getOrDefault(createProcedure.getReturnType().getAttribute(), ARETURN));
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

        mv.visitInsn(IRETURN);

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
