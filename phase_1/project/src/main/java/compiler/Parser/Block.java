package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;

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
}
