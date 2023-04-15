package compiler.Parser;

import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;

public class RecordCall extends Records {

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
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
