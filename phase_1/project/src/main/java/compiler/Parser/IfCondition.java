package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class IfCondition extends ASTNode {

    Condition condition;
    Block ifBlock;

    public IfCondition(Condition condition, Block ifBlock) {
        super();
        this.condition = condition;
        this.ifBlock = ifBlock;
    }

    @Override
    public String toString() {
        return "IfCondition{" +
                "condition=" + condition +
                ", ifBlock=" + ifBlock +
                '}';
    }
}
