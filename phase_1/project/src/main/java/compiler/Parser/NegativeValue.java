package compiler.Parser;

public class NegativeValue extends Expression{
    Expression value;
    NegativeValue(Expression value){
        this.value=value;
    }

    @Override
    public String toString() {
        return "- "+this.value.toString();
    }
}
