package compiler;


import compiler.Lexer.Symbol;
import compiler.Parser.Expression;
import compiler.Parser.Operator;
import compiler.Parser.SubExpression;

public class MyNode extends Expression {
    Expression value;
    String type;
    MyNode left;
    MyNode right;

    public MyNode(Expression v){
        this.value = v;
        if (v instanceof Symbol){
            this.type = ((Symbol) v).getName();
        }
        left=null;
        right=null;
    }
    public MyNode(Operator v, MyNode left, MyNode right){
        this.value = v;
        if (v instanceof Symbol){
            this.type = ((Symbol) v).getName();
        }
        if (left.value instanceof SubExpression){
            this.left=((SubExpression) left.value).getRoot();
        }else {
            this.left=left;
        }
        if (right.value instanceof SubExpression){
            this.right=((SubExpression) right.value).getRoot();
        }else {
            this.right=right;
        }
    }

}