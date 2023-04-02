package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

public class CreateReferencedVariable extends CreateVariables {

    Identifier referencedIdentifier;

    public CreateReferencedVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type, Identifier referencedIdentifier) {
        super(stateKeyword, variableIdentifier, type);
        this.referencedIdentifier = referencedIdentifier;
    }

    @Override
    public String toString() {
        return "CreateReferencedVariable{" +
                "referencedIdentifier=" + referencedIdentifier +
                '}';
    }
}
