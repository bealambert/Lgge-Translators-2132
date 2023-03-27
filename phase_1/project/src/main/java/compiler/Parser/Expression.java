package compiler.Parser;

import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class Expression {

    private final ArrayList<Object> expression;

    public Expression(ArrayList<Object> symbolArrayList) {
        super();
        this.expression = symbolArrayList;
    }

    @Override
    public String toString() {
        return this.expression.toString();
    }
}
