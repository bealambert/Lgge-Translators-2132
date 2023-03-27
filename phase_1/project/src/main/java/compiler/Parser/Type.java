package compiler.Parser;

import compiler.ASTNode;

public class Type extends ASTNode {

    String attribute;
    boolean isArray;

    public Type(String attribute, boolean isArray) {
        super();
        this.attribute = attribute;
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        return attribute;

    }
}
