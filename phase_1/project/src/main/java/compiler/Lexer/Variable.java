package compiler.Lexer;

public class Variable extends Token{

    private  String variable;
    public Variable(String attribute) {
        super("Strings");
        this.variable = attribute;
    }

    public String getVariable() {
        return variable;
    }
}