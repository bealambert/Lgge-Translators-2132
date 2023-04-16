package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

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

    public Identifier getIdentifier() {
        return identifier;
    }

    public Block getBody() {
        return body;
    }

    public CreateVariables getCreateVariables() {
        return createVariables;
    }

    public Expression getEnd() {
        return end;
    }

    public Expression getIncrementBy() {
        return incrementBy;
    }

    public Expression getStart() {
        return start;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }


}
