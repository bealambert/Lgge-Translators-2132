package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.Visitable;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public abstract class BinaryOperator extends ASTNode implements Visitable {

    private final Expression left;
    private final Expression right;

    public BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getRight() {
        return right;
    }

    public Expression getLeft() {
        return left;
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
