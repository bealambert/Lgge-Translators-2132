package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class Param extends ASTNode {

    Type type;
    Identifier identifier;

    public Param(Type type, Identifier identifier) {
        super();
        this.type = type;
        this.identifier = identifier;
    }


}
