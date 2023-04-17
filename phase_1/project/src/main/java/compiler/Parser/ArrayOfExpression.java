package compiler.Parser;

import compiler.ASTNode;
import compiler.BinaryTree;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class ArrayOfExpression extends ASTNode implements Visitable {

    ArrayList<Expression> expressions;
    BinaryTree myTree;

    public ArrayOfExpression(ArrayList<Expression> expressionArrayList) {
        this.expressions = expressionArrayList;
        this.myTree = new BinaryTree(expressionArrayList);
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }
    public BinaryTree getMyTree(){
        return this.myTree;
    }


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

}
