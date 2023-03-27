package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class ForLoop extends ASTNode {

    Identifier identifier;
    Expression start;
    Expression end;
    Expression incrementBy;
    Block body;

    CreateVariable createVariable;


    public ForLoop(Identifier identifier, Expression start, Expression end, Expression incrementBy, Block body) {
        this.identifier = identifier;
        this.start = start;
        this.end = end;
        this.incrementBy = incrementBy;
        this.body = body;
    }

    public ForLoop(CreateVariable createVariable, Expression end, Expression incrementBy, Block body) {
        this.createVariable = createVariable;
        this.end = end;
        this.incrementBy = incrementBy;
        this.body = body;

    }


}
