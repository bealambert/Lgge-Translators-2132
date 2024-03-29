package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.Token;
import org.objectweb.asm.MethodVisitor;

public class OperatorAdd extends Operator {

    public OperatorAdd() {
        super();
        this.setPrecedence_level(2);
        this.allowed_types = new String[]{
                Token.IntIdentifier.getName(), Token.NaturalNumber.getName(),
                Token.RealNumber.getName(), Token.RealIdentifier.getName(),
                Token.Strings.getName(), Token.StringIdentifier.getName()};
        this.operator = "+";

    }

    @Override
    public String[] getAllowed_types() {
        return this.allowed_types;
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }

    @Override
    public String getOperator() {
        return this.operator;
    }
}
