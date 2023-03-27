package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class Type extends ASTNode {

    Identifier attribute;
    boolean isArray;

    public Type(Identifier attribute) {
        super();
        this.attribute = attribute;
    }

    public Type(Identifier attribute, boolean isArray) {
        super();
        this.attribute = attribute;
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        return "Type{" +
                "attribute=" + attribute +
                ", isArray=" + isArray +
                '}';
    }
}
