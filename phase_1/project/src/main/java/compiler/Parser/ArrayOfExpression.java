package compiler.Parser;

import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.Visitable;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class ArrayOfExpression implements Visitable {

    ArrayList<Expression> expressions;

    public ArrayOfExpression(ArrayList<Expression> expressionArrayList) {
        this.expressions = expressionArrayList;
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {

    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {

    }
}
