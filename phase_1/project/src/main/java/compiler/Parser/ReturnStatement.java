package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;

public class ReturnStatement extends ASTNode implements Visitable {

    Expression expression;

    public ReturnStatement(Expression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ReturnStatement{" +
                "expression=" + expression +
                '}';
    }


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
