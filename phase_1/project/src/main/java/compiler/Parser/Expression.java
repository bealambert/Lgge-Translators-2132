package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;
import compiler.BinaryTree;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression extends ASTNode implements Visitable {

    private final ArrayList<Object> expression;
    BinaryTree myTree;

    public Expression(ArrayList<Object> symbolArrayList) {
        super();
        this.expression = symbolArrayList;
        this.myTree =  null;
    }

    @Override
    public String toString() {
        return this.expression.toString();
    }

    public ArrayList<Object> getExpression() {
        return expression;
    }

    public BinaryTree buildTree(){
        this.myTree = new BinaryTree(this.expression);
        return myTree;
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
