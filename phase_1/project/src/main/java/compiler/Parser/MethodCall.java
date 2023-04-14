package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class MethodCall extends Expression {

    Identifier methodIdentifier;

    public MethodCall(Identifier methodIdentifier) {
        super(new ArrayList<>());

        this.methodIdentifier = methodIdentifier;
    }

    @Override
    public String toString() {
        return "MethodCall{" +
                "methodIdentifier=" + methodIdentifier +
                '}';
    }
}
