package compiler;

import compiler.Semantic.*;

import java.util.ArrayList;

public abstract class ASTNode {

    private ASTNode next;
    private SymbolTable symbolTable = new SymbolTable(null);

    public ASTNode() {

    }

    public ASTNode getNext() {
        return next;
    }

    public void setNext(ASTNode next) {
        this.next = next;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
    public abstract void accept(Visitor visitor, SymbolTable symbolTable);

    public abstract void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException;
}
