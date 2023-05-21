package compiler.Parser;

import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class AssignToIndexArray extends AssignVariable {

    // a[3] = 123;
    AccessToIndexArray accessToIndexArray;

    public AssignToIndexArray(AccessToIndexArray accessToIndexArray, ArrayOfExpression assignmentExpression) {
        super(assignmentExpression);
        this.accessToIndexArray = accessToIndexArray;
    }


    public AccessToIndexArray getAccessToIndexArray() {
        return accessToIndexArray;
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
