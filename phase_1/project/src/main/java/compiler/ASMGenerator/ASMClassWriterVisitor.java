package compiler.ASMGenerator;

import compiler.BinaryTree;
import compiler.Lexer.Identifier;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.Semantic.ExpressionTypeVisitor;
import compiler.Semantic.SemanticVisitor;
import compiler.SemanticAnalysisException;
import compiler.Token;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.HashMap;
import java.util.Stack;

import static org.objectweb.asm.Opcodes.*;

public class ASMClassWriterVisitor implements SemanticVisitor {

    Stack<MethodVisitor> methodVisitorStack;
    Stack<Integer> flags;
    int flag;
    MethodVisitor mv;
    ClassWriter cw;
    HashMap<String, String> mapTypeToASMTypes;
    HashMap<String, Integer> mapVariableToIndex;
    int i;

    public ASMClassWriterVisitor() {
        mapTypeToASMTypes = new HashMap<>();
        mapTypeToASMTypes.put(Token.Strings.getName(), "Ljava/lang/String");
        mapTypeToASMTypes.put(Token.StringIdentifier.getName(), "Ljava/lang/String;");
        mapTypeToASMTypes.put(Token.NaturalNumber.getName(), "I");
        mapTypeToASMTypes.put(Token.IntIdentifier.getName(), "I");
        mapTypeToASMTypes.put(Token.RealNumber.getName(), "F");
        mapTypeToASMTypes.put(Token.RealIdentifier.getName(), "F");
        mapTypeToASMTypes.put(Token.Boolean.getName(), "Z");
        mapTypeToASMTypes.put(Token.BooleanIdentifier.getName(), "Z");
        methodVisitorStack = new Stack<>();
        flags = new Stack<>();
        mapVariableToIndex = new HashMap<>();
        i = 2;

    }

    public void setCw(ClassWriter cw) {
        this.cw = cw;
    }

    public void addMethodVisitor(MethodVisitor methodVisitor, int flag) {
        methodVisitorStack.add(methodVisitor);
        flags.add(flag);

        this.mv = methodVisitor;
        this.flag = flag;

    }

    public int getAccess(String keyword) {
        int access = 0;
        if (keyword.equals(Token.VarKeyword.getName())) {
            access = ACC_PUBLIC;
        } else if (keyword.equals(Token.ValKeyword.getName())) {
            access = ACC_PUBLIC;
        } else if (keyword.equals(Token.ConstKeyword.getName())) {
            access |= ACC_PUBLIC | ACC_FINAL;
        }
        return access;
    }

    public void makeLeftRightValueInstruction(MyNode myNode) throws SemanticAnalysisException {
        myNode.getLeft().accept(this);
        myNode.getRight().accept(this);
    }

    public void makeOperatorInstruction(MyNode myNode, Type type) throws SemanticAnalysisException {
/*        Type leftType = myNode.getLeft().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Type rightType = myNode.getRight().accept(ExpressionTypeVisitor.typeCheckingVisitor);*/
        if (myNode.getValue() instanceof OperatorAdd) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
                mv.visitInsn(IADD);
            } else if (type.getAttribute().equals(Token.RealIdentifier.getName()) || type.getAttribute().equals(Token.RealNumber.getName())) {
                mv.visitInsn(FADD);
            } else {
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);

            }
        } else if (myNode.getValue() instanceof OperatorDivide) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
                mv.visitInsn(IDIV);
            } else {
                mv.visitInsn(FDIV);
            }
        } else if (myNode.getValue() instanceof OperatorMultiply) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
                mv.visitInsn(IMUL);
            } else {
                mv.visitInsn(FMUL);
            }
        } else if (myNode.getValue() instanceof OperatorMinus) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
                mv.visitInsn(ISUB);
            } else {
                mv.visitInsn(FSUB);
            }
        } else if (myNode.getValue() instanceof OperatorEquality) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()) ||
                    type.getAttribute().equals(Token.RealNumber.getName()) || type.getAttribute().equals(Token.RealIdentifier.getName()) ||
                    type.getAttribute().equals(Token.Boolean.getName()) || type.getAttribute().equals(Token.BooleanIdentifier.getName())) {
                Label label = new Label();
                mv.visitJumpInsn(IF_ACMPEQ, label);

                mv.visitInsn(ICONST_0);
                Label endLabel = new Label();
                mv.visitJumpInsn(GOTO, endLabel);

                mv.visitLabel(label);
                mv.visitInsn(ICONST_1);
                mv.visitLabel(endLabel);
            } else {
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
            }
        } else if (myNode.getValue() instanceof OperatorNotEqual) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()) ||
                    type.getAttribute().equals(Token.RealNumber.getName()) || type.getAttribute().equals(Token.RealIdentifier.getName()) ||
                    type.getAttribute().equals(Token.Boolean.getName()) || type.getAttribute().equals(Token.BooleanIdentifier.getName())) {
                Label label = new Label();
                mv.visitJumpInsn(IF_ICMPNE, label);

                mv.visitInsn(ICONST_0);
                Label endLabel = new Label();
                mv.visitJumpInsn(GOTO, endLabel);

                mv.visitLabel(label);
                mv.visitInsn(ICONST_1);
                mv.visitLabel(endLabel);

            } else {
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                mv.visitInsn(ICONST_1);
                mv.visitInsn(IXOR);
            }
        } else if (myNode.getValue() instanceof OperatorLessThan) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()) ||
                    type.getAttribute().equals(Token.RealIdentifier.getName()) || type.getAttribute().equals(Token.RealNumber.getName())) {

                Label label = new Label();
                mv.visitJumpInsn(IF_ICMPLT, label);

                mv.visitInsn(ICONST_0);
                Label endLabel = new Label();
                mv.visitJumpInsn(GOTO, endLabel);

                mv.visitLabel(label);
                mv.visitInsn(ICONST_1);
                mv.visitLabel(endLabel);
            }

        } else if (myNode.getValue() instanceof OperatorLessThanOrEqual) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()) ||
                    type.getAttribute().equals(Token.RealIdentifier.getName()) || type.getAttribute().equals(Token.RealNumber.getName())) {

                Label label = new Label();
                mv.visitJumpInsn(IF_ICMPLE, label);

                mv.visitInsn(ICONST_0);
                Label endLabel = new Label();
                mv.visitJumpInsn(GOTO, endLabel);

                mv.visitLabel(label);
                mv.visitInsn(ICONST_1);
                mv.visitLabel(endLabel);
            }

        } else if (myNode.getValue() instanceof OperatorGreaterThan) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()) ||
                    type.getAttribute().equals(Token.RealIdentifier.getName()) || type.getAttribute().equals(Token.RealNumber.getName())) {

                Label label = new Label();
                mv.visitJumpInsn(IF_ICMPGT, label);

                mv.visitInsn(ICONST_0);
                Label endLabel = new Label();
                mv.visitJumpInsn(GOTO, endLabel);

                mv.visitLabel(label);
                mv.visitInsn(ICONST_1);
                mv.visitLabel(endLabel);
            }

        } else if (myNode.getValue() instanceof OperatorGreaterThanOrEqual) {
            if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()) ||
                    type.getAttribute().equals(Token.RealIdentifier.getName()) || type.getAttribute().equals(Token.RealNumber.getName())) {

                Label label = new Label();
                mv.visitJumpInsn(IF_ICMPGE, label);

                mv.visitInsn(ICONST_0);
                Label endLabel = new Label();
                mv.visitJumpInsn(GOTO, endLabel);

                mv.visitLabel(label);
                mv.visitInsn(ICONST_1);
                mv.visitLabel(endLabel);
            }

        } else if (myNode.getValue() instanceof OperatorModulo) {
            mv.visitInsn(IREM);
        }
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
        int access = getAccess(keyword);
        String variableName = createExpressionVariable.getVariableIdentifier().getIdentifier().getAttribute();
        String desc = mapTypeToASMTypes.getOrDefault(createExpressionVariable.getType().getAttribute(), "A");

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
            makeOperatorInstruction(myNode, myNode.getLeft().accept(ExpressionTypeVisitor.typeCheckingVisitor));
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
