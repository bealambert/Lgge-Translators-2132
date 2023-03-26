package compiler.Parser;

import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class Expression {

    private final ArrayList<Symbol> expression;

    public Expression(ArrayList<Symbol> symbolArrayList) {
        super();
        this.expression = symbolArrayList;
    }


}
