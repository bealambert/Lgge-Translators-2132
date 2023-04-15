package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class ForLoop extends ASTNode implements Visitable {

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

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }


}
