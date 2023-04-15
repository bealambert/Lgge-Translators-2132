package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class Reassignment extends ASTNode implements Visitable {

    private Identifier identifier;
    private Expression expression;

    public Reassignment(Identifier identifier, Expression expression) {
        super();
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Reassignment{ " +
                identifier +
                " " + expression +
                " }";
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
