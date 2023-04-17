package compiler.Parser;

import compiler.Semantic.TypeCheckingVisitor;
import compiler.SemanticAnalysisException;

public class ExpressionParameter extends FunctionCallParameter {

    Expression expression;

    public ExpressionParameter(Expression expression) {
        super();
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
