package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class ForLoop extends ASTNode implements Visitable {

    Expression end;
    Expression incrementBy;
    Block body;


    public ForLoop(Expression end, Expression incrementBy, Block body) {
        this.end = end;
        this.incrementBy = incrementBy;
        this.body = body;
    }


    public Block getBody() {
        return body;
    }


    public Expression getEnd() {
        return end;
    }

    public Expression getIncrementBy() {
        return incrementBy;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }


}
