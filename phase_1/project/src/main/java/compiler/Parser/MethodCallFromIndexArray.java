package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class MethodCallFromIndexArray extends MethodCall implements Visitable {

    AccessToIndexArray accessToIndexArray;

    public MethodCallFromIndexArray(AccessToIndexArray accessToIndexArray, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.accessToIndexArray = accessToIndexArray;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
