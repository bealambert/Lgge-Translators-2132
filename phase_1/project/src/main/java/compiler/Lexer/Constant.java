package compiler.Lexer;

public class Constant extends Token{

    private final String constant_variable;
    public Constant(String attribute) {
        super("Constant");
        this.constant_variable = attribute;
    }

    public String getConstant_variable() {
        return getConstant_variable();
    }
}