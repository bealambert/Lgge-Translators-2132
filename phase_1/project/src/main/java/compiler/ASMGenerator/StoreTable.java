package compiler.ASMGenerator;

import compiler.ASTNode;
import compiler.Semantic.SymbolTable;

import java.util.HashMap;

public class StoreTable {

    StoreTable previous;
    HashMap<String, Integer> storeTable = new HashMap<>();

    public StoreTable(StoreTable previous) {
        this.previous = previous;
    }
}
