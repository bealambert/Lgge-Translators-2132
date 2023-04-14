package compiler.Parser;

import compiler.Lexer.Identifier;

public class MethodCallFromIndexArray extends MethodCall {

    AccessToIndexArray accessToIndexArray;

    public MethodCallFromIndexArray(AccessToIndexArray accessToIndexArray, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.accessToIndexArray = accessToIndexArray;
    }
}
