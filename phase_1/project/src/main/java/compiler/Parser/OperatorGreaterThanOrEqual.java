package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import compiler.Token;
import org.objectweb.asm.MethodVisitor;

public class OperatorGreaterThanOrEqual extends OperatorComparator {
    int precedence_level = 3;

    public OperatorGreaterThanOrEqual() {
        this.allowed_types = new String[]{
                Token.IntIdentifier.getName(), Token.NaturalNumber.getName(),
                Token.RealNumber.getName(), Token.RealIdentifier.getName()
        };
        this.operator = ">=";
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
