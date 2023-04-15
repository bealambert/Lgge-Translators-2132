package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;

public class CreateVoidVariable extends CreateVariables implements Visitable {


    public CreateVoidVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        super(stateKeyword, variableIdentifier, type);
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
