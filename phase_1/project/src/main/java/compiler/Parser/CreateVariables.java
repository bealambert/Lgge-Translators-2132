package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public abstract class CreateVariables extends ASTNode {

    Identifier variableIdentifier;
    Keyword stateKeyword;
    Type type;

    public CreateVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        this.variableIdentifier = variableIdentifier;
        this.stateKeyword = stateKeyword;
        this.type = type;
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
