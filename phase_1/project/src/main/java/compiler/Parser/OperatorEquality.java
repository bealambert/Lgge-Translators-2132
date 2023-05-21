package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import compiler.Token;
import org.objectweb.asm.MethodVisitor;

public class OperatorEquality extends OperatorComparator {

    OperatorEquality() {
        this.allowed_types = new String[]{
                Token.IntIdentifier.getName(), Token.NaturalNumber.getName(),
                Token.RealNumber.getName(), Token.RealIdentifier.getName(),
                Token.BooleanIdentifier.getName(), Token.Boolean.getName(),
                Token.Strings.getName()
        };
        this.operator = "==";
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }

    @Override
    public String[] getAllowed_types() {
        return this.getAllowed_types();
    }

    @Override
    public String getOperator() {
        return this.operator;
    }

}
