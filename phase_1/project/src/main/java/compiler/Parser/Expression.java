package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression extends ASTNode {

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
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
