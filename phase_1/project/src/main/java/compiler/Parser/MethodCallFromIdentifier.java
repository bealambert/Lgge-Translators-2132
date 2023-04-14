package compiler.Parser;

import compiler.Lexer.Identifier;

public class MethodCallFromIdentifier extends MethodCall {

    Identifier objectIdentifier;

    public MethodCallFromIdentifier(Identifier objectIdentifier, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.objectIdentifier = objectIdentifier;
    }
}
