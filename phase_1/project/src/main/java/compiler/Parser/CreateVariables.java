package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public abstract class CreateVariables extends ASTNode implements Visitable {

    Identifier variableIdentifier;
    Keyword stateKeyword;
    Type type;

    public CreateVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type) {
        this.variableIdentifier = variableIdentifier;
        this.stateKeyword = stateKeyword;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Identifier getVariableIdentifier() {
        return variableIdentifier;
    }

    public Keyword getStateKeyword() {
        return stateKeyword;
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
