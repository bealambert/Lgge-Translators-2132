package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class Type extends ASTNode {

    Identifier attribute;

    public Type(Identifier attribute) {
        super();
        this.attribute = attribute;
    }

    public Identifier getAttribute() {
        return attribute;
    }

    public String getName() {
        return "Type";
    }


    @Override
    public String toString() {
        return "Type{" +
                "attribute=" + attribute +
                '}';
    }
}
