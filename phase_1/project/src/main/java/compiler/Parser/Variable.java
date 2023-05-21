package compiler.Parser;

import compiler.ASMGenerator.ASMClassWriterVisitor;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Variable extends Expression {

    private final Identifier identifier;


    public Variable(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.identifier.toString();
    }
}
