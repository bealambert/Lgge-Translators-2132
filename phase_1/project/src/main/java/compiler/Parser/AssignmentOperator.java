package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;

public class AssignmentOperator extends ASTNode {

    private Symbol symbol;

    public AssignmentOperator(Symbol symbol) {
        super();
        this.symbol = symbol;
    }
}
