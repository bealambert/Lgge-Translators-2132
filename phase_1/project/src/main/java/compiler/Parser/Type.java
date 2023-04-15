package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class Type extends ASTNode {

    Identifier attribute;

    public Type(Identifier attribute) {
        super();
        this.attribute = attribute;
    }

    public Identifier getAttribute() {
        return attribute;
    }

    public String getName() {
        return "Type";
    }


    @Override
    public String toString() {
        return "Type{" +
                "attribute=" + attribute +
                '}';
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
