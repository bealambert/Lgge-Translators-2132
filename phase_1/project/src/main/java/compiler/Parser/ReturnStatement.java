package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class ReturnStatement extends ASTNode implements Visitable {

    ArrayOfExpression arrayOfExpression;

    public ReturnStatement(ArrayOfExpression arrayOfExpression) {
        super();
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
