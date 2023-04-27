package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class ForLoopAssignVariable extends ForLoop {

    Identifier identifier;
    Expression start;

    public ForLoopAssignVariable(Identifier identifier, Expression start, Expression end, Expression incrementBy, Block body) {
        super(end, incrementBy, body);
        this.identifier = identifier;
        this.start = start;

    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getStart() {
        return start;
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
