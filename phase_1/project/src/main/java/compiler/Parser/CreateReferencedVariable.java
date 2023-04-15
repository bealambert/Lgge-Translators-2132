package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class CreateReferencedVariable extends CreateVariables {

    Identifier referencedIdentifier;

    public CreateReferencedVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type, Identifier referencedIdentifier) {
        super(stateKeyword, variableIdentifier, type);
        this.referencedIdentifier = referencedIdentifier;
    }

    @Override
    public String toString() {
        return "CreateReferencedVariable{" +
                "referencedIdentifier=" + referencedIdentifier +
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
