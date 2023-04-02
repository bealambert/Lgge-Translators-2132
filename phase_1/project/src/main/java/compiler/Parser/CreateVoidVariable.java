package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

public class CreateVoidVariable extends CreateVariables {


    public CreateVoidVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        super(stateKeyword, variableIdentifier, type);
    }
}
