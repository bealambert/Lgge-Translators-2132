package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;
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

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

}
