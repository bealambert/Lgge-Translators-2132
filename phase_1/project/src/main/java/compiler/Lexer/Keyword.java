package compiler.Lexer;

public class Keyword implements Symbol {

    private final String attribute;
    public final String[] acceptedAttributes = new String[]{
            "const", "record", "var", "val", "proc",
            "for", "to", "by", "while", "if", "else",
            "return", "and", "or"
    };
    private final String name = "Keyword";

    public Keyword(String attribute) {
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

    public String[] getAcceptedAttributes() {
        return acceptedAttributes;
    }

    @Override
    public String toString() {
        return "<" + this.name + ", " + this.getAttribute() + ">";
    }

}