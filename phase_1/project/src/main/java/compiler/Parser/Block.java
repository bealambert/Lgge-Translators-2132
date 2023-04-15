package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;

import java.util.ArrayList;

public class Block extends ASTNode implements Visitable {

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
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
