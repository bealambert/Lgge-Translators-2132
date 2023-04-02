package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

public abstract class CreateVariables extends ASTNode {

    Identifier variableIdentifier;
    Keyword stateKeyword;
    Type type;

    public CreateVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        this.variableIdentifier = variableIdentifier;
        this.stateKeyword = stateKeyword;
        this.type = type;
    }

}
