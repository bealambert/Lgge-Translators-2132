package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitable;
import compiler.SemanticAnalysisException;

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

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    public String toString() {
        return this.identifier.toString();
    }
}
