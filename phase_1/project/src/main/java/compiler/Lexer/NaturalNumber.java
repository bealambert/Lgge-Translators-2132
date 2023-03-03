package compiler.Lexer;

public class NaturalNumber extends Token{

    private final String natural;
    public NaturalNumber(String attribute) {
        super("NaturalNumber");
        this.natural = attribute;
    }

    public String getNatural() {
        return natural;
    }
}
