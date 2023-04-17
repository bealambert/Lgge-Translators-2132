package compiler.Parser;

public class Operator extends Expression {
    int precedence_level;

    public int getPrecedenceLevel() {
        return precedence_level;
    }
    public void setPrecedence_level(int precedenceLevel){
        this.precedence_level = precedenceLevel;
    }
}
