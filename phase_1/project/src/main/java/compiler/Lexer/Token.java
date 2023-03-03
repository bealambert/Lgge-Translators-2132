package compiler.Lexer;

public class Token {

    private final String token;
    public Token(String input){
        this.token = input;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "<" + this.getToken() + ">";
    }
}
