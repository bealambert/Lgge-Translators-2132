package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class FunctionCall extends Expression {

    Symbol identifier;


    public FunctionCall(Symbol identifier, ArrayList<Object> params) {
        super(params);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                identifier +
                '}' + ", (" + this.getExpression() + ')';
    }
}
