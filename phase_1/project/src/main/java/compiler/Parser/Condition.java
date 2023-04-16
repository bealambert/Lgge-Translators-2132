package compiler.Parser;

import compiler.ASTNode;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Condition extends ASTNode implements Visitable {

    // value != 3
    // Subset of Expression
    ArrayOfExpression arrayOfExpression;

    public Condition(ArrayOfExpression arrayOfExpression) {
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
}
