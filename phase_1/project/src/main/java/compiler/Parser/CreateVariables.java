package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;

public abstract class CreateVariables extends ASTNode implements Visitable {

    Identifier variableIdentifier;
    Keyword stateKeyword;
    Type type;

    public CreateVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        this.variableIdentifier = variableIdentifier;
        this.stateKeyword = stateKeyword;
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

}
