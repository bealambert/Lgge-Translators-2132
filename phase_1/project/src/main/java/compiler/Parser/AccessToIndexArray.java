package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class AccessToIndexArray extends Variable implements Visitable {

    ArrayOfExpression arrayOfExpression;
    private final String name = "AccessToIndexArray";

    public AccessToIndexArray(Identifier identifier, ArrayOfExpression arrayOfExpression) {
        super(identifier);
        this.arrayOfExpression = arrayOfExpression;
    }

    public String getName() {
        return name;
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

    public Type accept(ExpressionTypeVisitor expressionTypeVisitor) throws SemanticAnalysisException {
        return expressionTypeVisitor.visit(this);
    }
}
