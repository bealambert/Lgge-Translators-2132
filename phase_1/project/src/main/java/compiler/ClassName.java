package compiler;

import compiler.Parser.AccessToIndexArray;

public enum ClassName {

    Type("Type"),
    ArrayType("ArrayType"),
    AccessToIndexArray("AccessToIndexArray"),
    Identifier("Identifier");

    private final String name;

    private ClassName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
