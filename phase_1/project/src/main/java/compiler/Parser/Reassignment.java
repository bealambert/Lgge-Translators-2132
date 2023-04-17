package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Reassignment extends ASTNode implements Visitable {

    private Identifier identifier;
    private ArrayOfExpression arrayOfExpression;

    public Reassignment(Identifier identifier, ArrayOfExpression arrayOfExpression) {
        super();
        this.identifier = identifier;
        this.arrayOfExpression = arrayOfExpression;
    }

    public ArrayOfExpression getArrayOfExpression() {
        return arrayOfExpression;
    }

    public Identifier getIdentifier() {
        return identifier;
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
