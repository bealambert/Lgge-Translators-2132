package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class AssignToRecordAttribute extends AssignVariable {

    MethodCallFromIdentifier methodCallFromIdentifier;

    public AssignToRecordAttribute(MethodCallFromIdentifier methodCallFromIdentifier, ArrayOfExpression assignmentExpression) {
        super(assignmentExpression);
        this.methodCallFromIdentifier = methodCallFromIdentifier;
    }

    public MethodCallFromIdentifier getMethodCallFromIdentifier() {
        return methodCallFromIdentifier;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException {
        semanticVisitor.visit(this);
    }

    @Override
    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return null;
    }
}
