package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class MethodCallFromIdentifier extends MethodCall implements Visitable {

    Identifier objectIdentifier;

    public MethodCallFromIdentifier(Identifier objectIdentifier, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.objectIdentifier = objectIdentifier;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
