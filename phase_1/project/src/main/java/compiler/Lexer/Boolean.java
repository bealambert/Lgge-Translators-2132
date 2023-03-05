package compiler.Lexer;

public class Boolean implements Symbol{

    private final String name = "Boolean";
    private final String[] acceptedAttributes = new String[]{"true", "false"};

    private final String attribute;

    public Boolean(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
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
