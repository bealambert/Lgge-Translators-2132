package compiler.Semantic;

import compiler.Lexer.Identifier;


import java.util.HashMap;

public class SymbolTable {

    SymbolTable previous;
    HashMap<Identifier, SemanticTypes> SymbolTable;

    public SymbolTable(SymbolTable previous) {
        this.previous = previous;
    }


}
