package compiler.Parser;

import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class RecordCall extends Records implements Visitable {

    Expression expression;

    public RecordCall(String attribute, Expression expression) {
        super(attribute);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "RecordCall{" +
                "expression=" + expression +
                '}';
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
