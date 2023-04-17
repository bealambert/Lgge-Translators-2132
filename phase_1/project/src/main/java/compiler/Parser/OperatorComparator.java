package compiler.Parser;

import compiler.Semantic.TypeCheckingVisitor;
import compiler.SemanticAnalysisException;

public class OperatorComparator extends Operator {
    OperatorComparator() {
        super();
        this.setPrecedence_level(3);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
