package compiler.Parser;

public class ExpressionParameter extends FunctionCallParameter {

    Expression expression;

    public ExpressionParameter(Expression expression) {
        super();
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
