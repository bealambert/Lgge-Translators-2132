package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

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

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }


}
