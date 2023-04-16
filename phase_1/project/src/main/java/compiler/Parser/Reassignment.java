package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Reassignment extends ASTNode implements Visitable {

    private Identifier identifier;
    private Expression expression;

    public Reassignment(Identifier identifier, Expression expression) {
        super();
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Reassignment{ " +
                identifier +
                " " + expression +
                " }";
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }
}
