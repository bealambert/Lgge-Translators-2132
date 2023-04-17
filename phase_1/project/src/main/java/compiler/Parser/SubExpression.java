package compiler.Parser;

import compiler.MyNode;

public class SubExpression extends Expression{
    ArrayOfExpression subExpression;
    public SubExpression(ArrayOfExpression subExpression){
        this.subExpression = subExpression;
    }
    public MyNode getRoot(){
        return this.subExpression.getMyTree().getRoot();
    }
}
