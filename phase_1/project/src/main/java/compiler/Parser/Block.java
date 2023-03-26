package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class Block extends ASTNode {

    private final ArrayList<Symbol> attribute;

    public Block(ArrayList<Symbol> attribute) {
        super();
        this.attribute = attribute;
    }


}
