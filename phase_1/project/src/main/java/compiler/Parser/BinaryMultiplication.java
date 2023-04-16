package compiler.Parser;

import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class BinaryMultiplication extends BinaryOperator {


    public BinaryMultiplication(Expression left, Expression right) {
        super(left, right);
    }


}
