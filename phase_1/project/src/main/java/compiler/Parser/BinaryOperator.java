package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public abstract class BinaryOperator extends Expression implements Visitable {

    private final Expression left;
    private final Expression right;

    public BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        this.accept(visitor, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        this.accept(visitor);
    }
}
