package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class IdentifierVariable extends ASTNode {

    Identifier identifier;

    public IdentifierVariable(Identifier identifier) {
        if (identifier.getName().equals(Identifier.class.getName())) {
            this.identifier = identifier;
        } else {
            this.identifier = (AccessToIndexArray) identifier;
        }
    }


}
