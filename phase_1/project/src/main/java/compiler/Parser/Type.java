package compiler.Parser;

import compiler.ASTNode;

public class Type extends ASTNode {

    String attribute;

    public Type(String attribute) {
        super();
        this.attribute = attribute;
    }
}
