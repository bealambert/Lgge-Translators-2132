package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class Values extends ASTNode {


    public Values() {
        super();
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
