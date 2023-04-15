package compiler;

import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.Visitor;

import java.util.ArrayList;

public abstract class ASTNode {

    private ASTNode next;
    private SymbolTable symbolTable;

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


}
