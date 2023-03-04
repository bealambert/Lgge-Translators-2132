package compiler.Lexer;

public class Constant implements Symbol{

    private final String attribute;
    private final Token token;
    private final String tokenName = "Constant";

    public Constant(Token token , String attribute) {
        this.token = token;
        this.attribute = attribute;
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
}