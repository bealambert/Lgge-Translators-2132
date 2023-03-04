package compiler.Lexer;

public class Comment implements Symbol{

    private final String attribute;
    private final Token token;
    private final String tokenName = "Comment";

    public Comment(Token token, String attribute) {
        this.attribute = attribute;
        this.token = token;
    }


    @Override
    public String getAttribute() {
        return this.attribute;
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
        return "<" + this.getName() + ">";
    }
}
