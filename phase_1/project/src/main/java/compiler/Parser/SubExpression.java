package compiler.Parser;

import compiler.MyNode;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class SubExpression extends Expression {
    ArrayOfExpression subExpression;

    public SubExpression(ArrayOfExpression subExpression) {
        this.subExpression = subExpression;
    }

    public ArrayOfExpression getSubExpression() {
        return subExpression;
    }

    public MyNode getRoot() {
        return this.subExpression.getMyTree().getRoot();
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }


}
