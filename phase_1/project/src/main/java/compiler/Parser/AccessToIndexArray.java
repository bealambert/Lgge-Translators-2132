package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class AccessToIndexArray extends Identifier {

    Expression expression;


    public AccessToIndexArray(String attribute, Expression expression) {
        super(attribute);
        this.expression = expression;
    }

    @Override
    public String getName() {
        return "AccessToIndexArray";
    }

    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public String toString() {
        return "AccessToIndexArray{" +
                "expression=" + expression +
                '}';
    }
}
