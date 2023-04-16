package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class FunctionCallParameter extends ASTNode {


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        this.accept(visitor, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException {
        this.accept(semanticVisitor);
    }
}
