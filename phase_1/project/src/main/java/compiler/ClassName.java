package compiler;

public enum ClassName {

    Type("Type"),
    ArrayType("ArrayType");

    private final String name;

    private ClassName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
