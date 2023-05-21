package compiler.Semantic;

import compiler.ASTNode;
import compiler.Lexer.Identifier;


import java.util.HashMap;

public class SymbolTable {

    SymbolTable previous;
    HashMap<String, ASTNode> symbolTable = new HashMap<>();

    public SymbolTable(SymbolTable previous) {
        this.previous = previous;
    }
}
