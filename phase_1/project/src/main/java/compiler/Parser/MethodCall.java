package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class MethodCall extends ASTNode {

    Identifier objectIdentifier;
    Identifier methodIdentifier;

    public MethodCall(Identifier objectIdentifier, Identifier methodIdentifier) {


        if (objectIdentifier.getName().equals(methodIdentifier.getName())) {
            this.objectIdentifier = objectIdentifier;
        } else {
            this.objectIdentifier = (AccessToIndexArray) objectIdentifier;
        }
        this.methodIdentifier = methodIdentifier;
    }

    @Override
    public String toString() {
        return "MethodCall{" +
                "objectIdentifier=" + objectIdentifier +
                ", methodIdentifier=" + methodIdentifier +
                '}';
    }
}
