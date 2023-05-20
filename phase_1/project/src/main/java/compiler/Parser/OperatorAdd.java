package compiler.Parser;

import compiler.ASMGenerator.ASMClassWriterVisitor;
import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import org.objectweb.asm.MethodVisitor;

public class OperatorAdd extends Operator {
    OperatorAdd() {
        super();
        this.setPrecedence_level(2);
    }
    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }

}
