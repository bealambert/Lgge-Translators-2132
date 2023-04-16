package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression extends ASTNode implements Visitable {

    private final ArrayList<Object> expression;

    public Expression(ArrayList<Object> symbolArrayList) {
        super();
        this.expression = symbolArrayList;
    }

    @Override
    public String toString() {
        return this.expression.toString();
    }

    public ArrayList<Object> getExpression() {
        return expression;
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
