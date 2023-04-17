package compiler.Parser;

import compiler.BinaryTree;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.Visitable;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class ArrayOfExpression implements Visitable {

    ArrayList<Expression> expressions;
    BinaryTree myTree;

    public ArrayOfExpression(ArrayList<Expression> expressionArrayList) {
        this.expressions = expressionArrayList;
        this.myTree = null;
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public BinaryTree buildTree() {
        //this.myTree = new BinaryTree(this.expressions);
        return myTree;
    }

    public BinaryTree getMyTree() {
        return myTree;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {

    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {

    }




}
