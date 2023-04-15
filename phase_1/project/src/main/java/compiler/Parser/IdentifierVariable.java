package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class IdentifierVariable extends ASTNode {

    Identifier identifier;

    public IdentifierVariable(Identifier identifier) {
        if (identifier.getName().equals(Identifier.class.getName())) {
            this.identifier = identifier;
        } else {
            this.identifier = (AccessToIndexArray) identifier;
        }
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
