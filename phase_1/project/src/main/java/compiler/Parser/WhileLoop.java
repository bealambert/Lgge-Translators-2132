package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;

public class WhileLoop extends ASTNode implements Visitable {

    // no assignation in the condition ?
    Condition condition;
    Block body;


    public WhileLoop(Condition condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
