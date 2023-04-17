package compiler;

import compiler.Lexer.Symbol;
import compiler.Parser.Expression;
import compiler.Parser.Operator;
import compiler.MyNode;

import java.util.ArrayList;
import java.util.RandomAccess;

public class BinaryTree {
    private final Token[] operatorValues = new Token[]{
            Token.PlusOperator, Token.MinusOperator, Token.DivideOperator,
            Token.MultiplyOperator, Token.ModuloOperator, Token.IsDifferentOperator,
            Token.IsLessOperator, Token.IsEqualOperator, Token.IsGreaterOperator,
            Token.IsLessOrEqualOperator, Token.IsGreaterOrEqualOperator, Token.AndKeyword, Token.OrKeyword
    };
    private final Token[] firstPrecedence = new Token[]{
            Token.DivideOperator, Token.MultiplyOperator, Token.ModuloOperator
    };

    private final Token[] secondPrecedence = new Token[]{
            Token.PlusOperator, Token.MinusOperator
    };

    private final Token[] thirdPrecedence = new Token[]{
            Token.IsDifferentOperator, Token.IsLessOperator, Token.IsEqualOperator, Token.IsGreaterOperator,
            Token.IsLessOrEqualOperator, Token.IsGreaterOrEqualOperator
    };

    private final Token[] fourthPrecedence = new Token[]{
            Token.AndKeyword, Token.OrKeyword
    };
    MyNode root;

    public BinaryTree(MyNode node) {
        this.root = node;
    }

    public BinaryTree(ArrayList<Expression> symbolArrayList) {
        this.root = BinaryTreeHelperRun(symbolArrayList);
    }
    public MyNode BinaryTreeHelperRun(ArrayList<Expression> symbolArrayList){
        if (symbolArrayList.size()<2){
            return new MyNode(symbolArrayList.get(0));
        }
        else{
            ArrayList<Expression> firstP = BinaryTreeHelper(symbolArrayList, 1);
            ArrayList<Expression> secondP = BinaryTreeHelper(firstP, 2);
            ArrayList<Expression> thirdP = BinaryTreeHelper(secondP, 3);
            ArrayList<Expression> fourthP = BinaryTreeHelper(thirdP, 4);
            return (MyNode) fourthP.get(0);
        }

    }

    public ArrayList<Expression> BinaryTreeHelper(ArrayList<Expression> symbolArrayList, int target_precedence_level){
        ArrayList<Expression> resultingList = new ArrayList<>();
        int n = symbolArrayList.size();
        Expression current_elem;
        MyNode node; MyNode left; MyNode right;

        for (int i = 0; i < n; i++) {
            current_elem = symbolArrayList.get(i);
            if (current_elem instanceof Operator  &&  ((Operator) current_elem).getPrecedenceLevel() == target_precedence_level) {
                // it is a valid operation
                if (i>0){
                    // 2+3
                    System.out.println(current_elem);
                    if (resultingList.get(resultingList.size()-1) instanceof MyNode){
                        left = (MyNode) resultingList.get(resultingList.size()-1);
                    }else{
                        left = new MyNode(resultingList.get(resultingList.size()-1));
                    }
                    if (symbolArrayList.get(i+1) instanceof MyNode){
                        right = (MyNode) symbolArrayList.get(i+1);
                    } else{
                        right = new MyNode(symbolArrayList.get(i+1));
                    }
                    node = new MyNode((Operator) current_elem, left, right);
                    resultingList.set(resultingList.size()-1, node);
                }
                i++;
            }
            // todo unary minus !!!
            else {
                // if it is another operation or a value
                resultingList.add(symbolArrayList.get(i));
            }
        }
        return resultingList;
    }


    public MyNode getRoot(){
        return this.root;
    }

}
