package compiler.ASMGenerator;

import compiler.MyNode;
import compiler.Parser.*;
import org.objectweb.asm.MethodVisitor;

public interface OperatorVisitor {

    void visit(Operator operator, Type type, MethodVisitor mv);

    void visit(OperatorAdd operatorAdd, Type type, MethodVisitor mv);

    void visit(OperatorDivide operatorDivide, Type type, MethodVisitor mv);

    void visit(OperatorMultiply operatorMultiply, Type type, MethodVisitor mv);

    void visit(OperatorMinus operatorMinus, Type type, MethodVisitor mv);

    void visit(OperatorEquality operatorEquality, Type type, MethodVisitor mv);

    void visit(OperatorNotEqual operatorNotEqual, Type type, MethodVisitor mv);

    void visit(OperatorLessThan operatorLessThan, Type type, MethodVisitor mv);

    void visit(OperatorLessThanOrEqual operatorLessThanOrEqual, Type type, MethodVisitor mv);

    void visit(OperatorGreaterThan operatorGreaterThan, Type type, MethodVisitor mv);

    void visit(OperatorGreaterThanOrEqual operatorGreaterThanOrEqual, Type type, MethodVisitor mv);

    void visit(OperatorModulo operatorModulo, Type type, MethodVisitor mv);

    void visit(MyNode myNode, Type type, MethodVisitor methodVisitor);

    void visit(Expression expression, Type accept, MethodVisitor mv);
}
