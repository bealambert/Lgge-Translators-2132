package compiler.Parser;

import compiler.ASTNode;

public class Condition extends ASTNode {

    // value != 3
    // Subset of Expression
    Expression expression;

    public Condition(Expression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "expression=" + expression +
                '}';
    }
}
