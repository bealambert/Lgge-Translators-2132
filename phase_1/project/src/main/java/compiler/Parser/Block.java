package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;

public class Block extends ASTNode {

    private final ArrayList<ASTNode> attribute;

    public Block(ArrayList<ASTNode> attribute) {
        super();
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Block{" +
                "attribute=" + attribute +
                '}';
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
