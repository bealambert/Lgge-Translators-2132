package compiler.Lexer;

public class Boolean implements Symbol{

    private final String tokenName = "Boolean";
    private final String[] acceptedAttributes = new String [] {"true", "false"};

    private final String attribute;
    private final Token token;

    public Boolean(Token token, String attribute) {
        this.token = token;
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public Token getToken() {
        return this.token;
    }

    @Override
    public String getName() {
        return this.tokenName;
    }

    @Override
    public String toString() {
        return "<" + tokenName + ", " + this.getAttribute() + ">";
    }
}
