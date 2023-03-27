package compiler.Parser;

import compiler.ASTNode;

public class WhileLoop extends ASTNode {

    // no assignation in the condition ?
    Condition condition;
    Block body;


    public WhileLoop(Condition condition, Block body) {
        this.condition = condition;
        this.body = body;
    }
}
