package compiler.Parser;

import compiler.Semantic.TypeCheckingVisitor;
import compiler.SemanticAnalysisException;

public class Operator extends Expression {
    int precedence_level;

    public int getPrecedenceLevel() {
        return precedence_level;
    }

    public void setPrecedence_level(int precedenceLevel) {
        this.precedence_level = precedenceLevel;
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
