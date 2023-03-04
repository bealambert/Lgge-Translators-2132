package compiler.Lexer;

public class Boolean extends Token{

    private String tokenName = "Boolean";
    private final String[] acceptedAttributes = new String [] {"true", "false"};
    private final String attribute;
    public Boolean(Token token, String attribute) {
        super("Boolean");
        this.attribute = attribute;
    }
    public Boolean( String attribute) {
        super("Boolean");
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return "<" + tokenName + ", " + this.getAttribute() + ">";
    }
}
