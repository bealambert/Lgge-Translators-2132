package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.SpecialCharacter;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class Operator extends ASTNode {

    private SpecialCharacter specialCharacter;

    public Operator(SpecialCharacter specialCharacter) {
        this.specialCharacter = specialCharacter;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "specialCharacter=" + specialCharacter +
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
