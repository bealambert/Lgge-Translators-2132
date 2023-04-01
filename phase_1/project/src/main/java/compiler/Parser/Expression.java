package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression extends ASTNode {

    private final ArrayList<Object> expression;

    public Expression(ArrayList<Object> symbolArrayList) {
        super();
        this.expression = symbolArrayList;
    }

    @Override
    public String toString() {
        return this.expression.toString();
    }

    public ArrayList<Object> getExpression() {
        return expression;
    }
}
