package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;

public class AssignmentOperator extends ASTNode implements Visitable {

    private Symbol symbol;

    public AssignmentOperator(Symbol symbol) {
        super();
        this.symbol = symbol;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
