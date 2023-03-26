package compiler.Parser;

import compiler.ASTNode;

public class ReturnStatement extends ASTNode {

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
}
