package compiler.Lexer;

public class Identifier extends Token{

    private final String identifier;
    public Identifier(String attribute) {
        super("Identifier");
        this.identifier = attribute;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}