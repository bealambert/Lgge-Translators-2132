package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class AssignToRecordAttributeAtIndex extends AssignVariable {

    MethodCallFromIndexArray methodCallFromIndexArray;

    public AssignToRecordAttributeAtIndex(MethodCallFromIndexArray methodCallFromIndexArray, ArrayOfExpression assignmentExpression) {
        super(assignmentExpression);
        this.methodCallFromIndexArray = methodCallFromIndexArray;
    }

    public MethodCallFromIndexArray getMethodCallFromIndexArray() {
        return methodCallFromIndexArray;
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
