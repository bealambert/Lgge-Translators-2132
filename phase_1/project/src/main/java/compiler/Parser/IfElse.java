package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class IfElse extends IfCondition implements Visitable {

    Block elseBlock;

    public IfElse(Condition condition, Block ifBlock, Block elseBlock) {
        super(condition, ifBlock);
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

    public Block getElseBlock() {
        return elseBlock;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
