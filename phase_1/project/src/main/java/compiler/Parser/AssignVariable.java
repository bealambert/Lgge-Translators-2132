package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class AssignVariable extends ASTNode implements Visitable {

    ArrayOfExpression assignmentExpression;

    public AssignVariable(ArrayOfExpression assignmentExpression) {
        this.assignmentExpression = assignmentExpression;
    }

    public ArrayOfExpression getAssignmentExpression() {
        return assignmentExpression;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        //visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException {

    }

    @Override
    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return null;
    }
}
