package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import org.objectweb.asm.MethodVisitor;

public class OperatorOr extends OperatorComparator {
    OperatorOr() {
        super();
        this.setPrecedence_level(4);
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }
}
