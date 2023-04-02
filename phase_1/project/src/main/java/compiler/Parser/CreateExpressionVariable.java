package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

public class CreateExpressionVariable extends CreateVariables {

    Expression expression;

    public CreateExpressionVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type, Expression expression) {
        super(stateKeyword, variableIdentifier, type);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "CreateExpressionVariable{" +
                "expression=" + expression +
                '}';
    }
}
