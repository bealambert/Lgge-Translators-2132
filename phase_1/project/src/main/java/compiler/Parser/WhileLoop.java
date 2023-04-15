package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class WhileLoop extends ASTNode {

    // no assignation in the condition ?
    Condition condition;
    Block body;


    public WhileLoop(Condition condition, Block body) {
        this.condition = condition;
        this.body = body;
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
