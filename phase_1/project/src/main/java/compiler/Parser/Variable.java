package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.Visitable;

public class Variable extends Expression {

    private final Identifier identifier;


    public Variable(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }


    @Override
    public Expression getExpression() {
        return this;
    }
}