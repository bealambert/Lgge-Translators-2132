package compiler.Parser;

import compiler.Lexer.Symbol;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression {

    private final ArrayList<Symbol> expression;

    public Expression(ArrayList<Symbol> symbolArrayList) {
        super();
        this.expression = symbolArrayList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < expression.size(); i++) {
            stringBuilder.append(expression.get(i));
            stringBuilder.append(" ");
        }
        return String.valueOf(stringBuilder);
    }
}
