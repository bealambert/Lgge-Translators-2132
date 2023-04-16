package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Values extends ASTNode implements Visitable {


    public Values() {
        super();
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
