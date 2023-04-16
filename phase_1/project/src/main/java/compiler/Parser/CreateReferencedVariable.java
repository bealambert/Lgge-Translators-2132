package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class CreateReferencedVariable extends CreateVariables implements Visitable {

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
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }
}
