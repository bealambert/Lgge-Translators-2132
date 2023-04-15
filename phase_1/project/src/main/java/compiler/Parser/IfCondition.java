package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

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

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
