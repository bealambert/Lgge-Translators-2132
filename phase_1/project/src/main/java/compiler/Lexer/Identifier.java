package compiler.Lexer;

public class Identifier implements Symbol{

    private final String attribute;
    private final String name = "Identifier";

    public Identifier(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getAttribute() {
        return this.attribute;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "<" + this.name + ", " + this.getAttribute() + ">";
    }


}