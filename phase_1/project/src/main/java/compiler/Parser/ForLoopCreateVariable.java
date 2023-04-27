package compiler.Parser;

import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class ForLoopCreateVariable extends ForLoop {

    CreateVariables createVariables;

    public ForLoopCreateVariable(CreateVariables createVariables, Expression end, Expression incrementBy, Block body) {
        super(end, incrementBy, body);
        this.createVariables = createVariables;

    }

    public CreateVariables getCreateVariables() {
        return createVariables;
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
