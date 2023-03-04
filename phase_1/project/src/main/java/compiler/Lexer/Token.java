package compiler.Lexer;

public class Token {

    private final String tokenValue;
    public Token(String input){
        this.tokenValue = input;
    }

    public Token getToken() {
        return this;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return "<" + this.getToken() + ">";
    }
}
