package compiler.ASMGenerator;

import compiler.ASTNode;
import compiler.Semantic.SymbolTable;

import java.util.HashMap;

public class StoreTable {

    SymbolTable previous;
    HashMap<String, Integer> storeTable = new HashMap<>();

    public StoreTable(SymbolTable previous) {
        this.previous = previous;
    }
}
