package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;

public class CreateExpressionVariable extends CreateVariables implements Visitable {

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

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
