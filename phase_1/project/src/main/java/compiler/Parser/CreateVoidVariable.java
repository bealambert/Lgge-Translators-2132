package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class CreateVoidVariable extends CreateVariables implements Visitable {


    public CreateVoidVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        super(stateKeyword, variableIdentifier, type);
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
