package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import org.objectweb.asm.MethodVisitor;

public class OperatorMultiply extends Operator {

    OperatorMultiply() {
        super();
        this.setPrecedence_level(1);
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }
}
