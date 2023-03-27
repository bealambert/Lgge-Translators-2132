package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class IfElse extends ASTNode {

    Condition condition;
    Block ifBlock;
    Block elseBlock;

    public IfElse(Condition condition, Block ifBlock, Block elseBlock) {
        super();
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return "IfElse{" +
                "condition=" + condition +
                ", ifBlock=" + ifBlock +
                ", elseBlock=" + elseBlock +
                '}';
    }
}
