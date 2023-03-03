package compiler.Lexer;

public class RealNumber extends Token{

    private final String real;
    public RealNumber(String attribute) {
        super("RealNumber");
        this.real = attribute;
    }

    public String getReal() {
        return real;
    }
}
