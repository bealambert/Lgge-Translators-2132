package compiler.ASMGenerator;

import compiler.Parser.OperatorAdd;
import compiler.Parser.Type;
import compiler.Token;


public interface ASMExpressionVisitor {

    public void visit(OperatorAdd operatorAdd, Type type);
}
