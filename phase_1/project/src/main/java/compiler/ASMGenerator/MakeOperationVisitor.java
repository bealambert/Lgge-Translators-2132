package compiler.ASMGenerator;

import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;
import compiler.Token;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class MakeOperationVisitor implements OperatorVisitor {

    @Override
    public void visit(Operator operator, Type type, MethodVisitor mv) {
        // parent class
    }

    @Override
    public void visit(OperatorAdd operatorAdd, Type type, MethodVisitor mv) {
        if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
            mv.visitInsn(IADD);
        } else if (type.getAttribute().equals(Token.RealIdentifier.getName()) || type.getAttribute().equals(Token.RealNumber.getName())) {
            mv.visitInsn(FADD);
        } else {
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        }
    }

    @Override
    public void visit(OperatorDivide operatorDivide, Type type, MethodVisitor mv) {
        if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
            mv.visitInsn(IDIV);
        } else {
            mv.visitInsn(FDIV);
        }
    }

    @Override
    public void visit(OperatorMultiply operatorMultiply, Type type, MethodVisitor mv) {
        if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
            mv.visitInsn(IMUL);
        } else {
            mv.visitInsn(FMUL);
        }
    }

    @Override
    public void visit(OperatorMinus operatorMinus, Type type, MethodVisitor mv) {
        if (type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName())) {
            mv.visitInsn(ISUB);
        } else {
            mv.visitInsn(FSUB);
        }
    }

    @Override
    public void visit(OperatorEquality operatorEquality, Type type, MethodVisitor mv) {
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
    }

    @Override
    public void visit(OperatorNotEqual operatorNotEqual, Type type, MethodVisitor mv) {
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
    }

    @Override
    public void visit(OperatorLessThan operatorLessThan, Type type, MethodVisitor mv) {
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

    }

    @Override
    public void visit(OperatorLessThanOrEqual operatorLessThanOrEqual, Type type, MethodVisitor mv) {
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
    }

    @Override
    public void visit(OperatorGreaterThan operatorGreaterThan, Type type, MethodVisitor mv) {
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
    }

    @Override
    public void visit(OperatorGreaterThanOrEqual operatorGreaterThanOrEqual, Type type, MethodVisitor mv) {
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
    }

    @Override
    public void visit(OperatorModulo operatorModulo, Type type, MethodVisitor mv) {
        mv.visitInsn(IREM);
    }

    @Override
    public void visit(MyNode myNode, Type type, MethodVisitor methodVisitor) {

    }

    @Override
    public void visit(Expression expression, Type accept, MethodVisitor mv) {

    }
}