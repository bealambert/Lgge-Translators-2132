package compiler.Lexer;


public class Keyword extends Token{

    private final String keyword;
    public Keyword(String attribute) {
        super("Keyword");
        this.keyword = attribute;
    }

    public String getKeyword() {
        return keyword;
    }
}
