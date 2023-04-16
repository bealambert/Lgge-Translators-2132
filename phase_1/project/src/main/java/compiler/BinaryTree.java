package compiler;

import compiler.Lexer.Symbol;

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

    public BinaryTree(ArrayList<Object> symbolArrayList) {
        this.root = BinaryTreeHelperRun(symbolArrayList);
    }
    public MyNode BinaryTreeHelperRun(ArrayList<Object> symbolArrayList){
        ArrayList<Object> firstP = BinaryTreeHelper(symbolArrayList, firstPrecedence);
        ArrayList<Object> secondP = BinaryTreeHelper(firstP, secondPrecedence);
        ArrayList<Object> thirdP = BinaryTreeHelper(secondP, thirdPrecedence);
        ArrayList<Object> fourthP = BinaryTreeHelper(thirdP, fourthPrecedence);
        return (MyNode) fourthP.get(0);
    }

    public ArrayList<Object> BinaryTreeHelper(ArrayList<Object> symbolArrayList, Token[] validTokens){
        ArrayList<Object> resultingList = new ArrayList<>();
        int n = symbolArrayList.size();
        Object current_elem;
        MyNode node; MyNode left; MyNode right;

        for (int i = 0; i < n; i++) {
            current_elem = symbolArrayList.get(i);
            if (current_elem instanceof Symbol && whichSymbol( (Symbol) current_elem, validTokens)) {
                // if it is a valid operation
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
                    node = new MyNode(current_elem, left, right);
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

    public boolean isArgToken(Symbol elem, Token reference) {
        return elem != null && (elem.getName().equals(reference.getName()) || elem.getAttribute().equals(reference.getName()));
    }

    /**
     * @param elem
     * @param tokenArray
     * @return true if elem is an operator symbol
     */
    public boolean whichSymbol(Symbol elem, Token[] tokenArray) {
        for (Token token : tokenArray) {
            if (isArgToken(elem, token)) {
                return true;
            }
        }
        return false;
    }
}


class MyNode{
    Object value;
    String type;
    MyNode left;
    MyNode right;

    public MyNode(Object v){
        this.value = v;
        if (v instanceof Symbol){
            this.type = ((Symbol) v).getName();
        }
        left=null;
        right=null;
    }
    public MyNode(Object v, MyNode left, MyNode right){
        this.value = v;
        if (v instanceof Symbol){
            this.type = ((Symbol) v).getName();
        }
        this.left=left;
        this.right=right;
    }

}