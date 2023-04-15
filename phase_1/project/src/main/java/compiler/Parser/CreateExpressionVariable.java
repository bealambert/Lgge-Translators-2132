package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

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

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
