package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class CreateExpressionVariable extends CreateVariables implements Visitable {

    ArrayOfExpression arrayOfExpression;

    public CreateExpressionVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type, ArrayOfExpression arrayOfExpression) {
        super(stateKeyword, variableIdentifier, type);
        this.arrayOfExpression = arrayOfExpression;
    }

    public ArrayOfExpression getArrayOfExpression() {
        return arrayOfExpression;
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
