package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;

public class CreateArrayVariable extends CreateVariables implements Visitable {

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

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
