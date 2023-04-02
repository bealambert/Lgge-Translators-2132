package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class ForLoop extends ASTNode {

    Identifier identifier;
    Expression start;
    Expression end;
    Expression incrementBy;
    Block body;

    CreateVariables createVariables;


    public ForLoop(Identifier identifier, Expression start, Expression end, Expression incrementBy, Block body) {
        this.identifier = identifier;
        this.start = start;
        this.end = end;
        this.incrementBy = incrementBy;
        this.body = body;
    }

    public ForLoop(CreateVariables createVariables, Expression end, Expression incrementBy, Block body) {
        this.createVariables = createVariables;
        this.end = end;
        this.incrementBy = incrementBy;
        this.body = body;

    }


}
