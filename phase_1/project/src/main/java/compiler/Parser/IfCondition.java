package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class IfCondition extends ASTNode implements Visitable {

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

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
