package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import compiler.Token;
import org.objectweb.asm.MethodVisitor;

public class OperatorOr extends OperatorComparator {
    public OperatorOr() {
        super();
        this.setPrecedence_level(4);
        this.allowed_types = new String[]{
                Token.BooleanIdentifier.getName(), Token.Boolean.getName(),
        };
        this.operator = "or";
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }

    @Override
    public String[] getAllowed_types() {
        return this.allowed_types;
    }

    @Override
    public String getOperator() {
        return this.operator;
    }
}
