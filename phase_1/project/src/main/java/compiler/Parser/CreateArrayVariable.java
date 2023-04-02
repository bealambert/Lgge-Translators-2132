package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

public class CreateArrayVariable extends CreateVariables {

    ArrayInitializer arrayInitializer;

    public CreateArrayVariable(Keyword stateKeyword, Identifier variableIdentifier, ArrayType type, ArrayInitializer arrayInitializer) {
        super(stateKeyword, variableIdentifier, type);
        this.arrayInitializer = arrayInitializer;
    }

    @Override
    public String toString() {
        return "CreateArrayVariable{" +
                "arrayInitializer=" + arrayInitializer +
                '}';
    }
}
