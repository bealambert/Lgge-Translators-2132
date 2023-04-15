package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class Param extends ASTNode {

    Type type;
    Identifier identifier;

    public Param(Type type, Identifier identifier) {
        super();
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "<" + type +
                ", " + identifier + "";
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
