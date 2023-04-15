package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

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

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
