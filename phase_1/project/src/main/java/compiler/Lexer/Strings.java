package compiler.Lexer;

public class Strings extends Token{

    private final String strings;
    public Strings(String attribute) {
        super("Strings");
        this.strings = attribute;
    }

    public String getStrings() {
        return strings;
    }
}