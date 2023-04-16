package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Condition extends ASTNode implements Visitable {

    // value != 3
    // Subset of Expression
    Expression expression;

    public Condition(Expression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "expression=" + expression +
                '}';
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
