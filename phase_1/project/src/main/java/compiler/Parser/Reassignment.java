package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class Reassignment extends ASTNode {

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
}
