package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class ReturnStatement extends ASTNode {

    Expression expression;

    public ReturnStatement(Expression expression) {
        super();
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ReturnStatement{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
