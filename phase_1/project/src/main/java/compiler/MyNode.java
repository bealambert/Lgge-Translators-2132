package compiler;


import compiler.ASMGenerator.OperatorVisitor;
import compiler.Lexer.Symbol;
import compiler.Parser.Expression;
import compiler.Parser.Operator;
import compiler.Parser.SubExpression;
import compiler.Parser.Type;
import compiler.Semantic.ExpressionTypeVisitor;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.TypeCheckingVisitor;
import org.objectweb.asm.MethodVisitor;

public class MyNode extends Expression {
    Expression value;
    String type;
    int n_nodes;
    MyNode left;
    MyNode right;

    public MyNode(Expression v){
        this.value = v;
        if (v instanceof Symbol){
            this.type = ((Symbol) v).getName();
        }
        this.left=null;
        this.right=null;
        this.n_nodes = 1;
    }
    public MyNode(Operator v, MyNode left, MyNode right){
        this.value = v;
        if (v instanceof Symbol){
            this.type = ((Symbol) v).getName();
        }
        if (left.value instanceof SubExpression){
            this.left=((SubExpression) left.value).getRoot();
        } else {
            this.left = left;
        }
        if (right.value instanceof SubExpression) {
            this.right = ((SubExpression) right.value).getRoot();
        } else {
            this.right = right;
        }

        this.n_nodes = this.left.n_nodes + this.right.n_nodes + 1;
    }

    public MyNode getRight() {
        return right;
    }

    public MyNode getLeft() {
        return left;
    }

    public String getType() {
        return type;
    }

    public int getN_nodes(){
        return n_nodes;
    }
    public Expression getValue() {
        return value;
    }
    public boolean isLeaf(){
        return getLeft()==null && getRight()==null;
    }

    public void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException {
        semanticVisitor.visit(this);
    }

    public Type accept(ExpressionTypeVisitor expressionTypeVisitor) throws SemanticAnalysisException {
        return expressionTypeVisitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    public void accept(OperatorVisitor operatorVisitor, Type type, MethodVisitor methodVisitor) {
        operatorVisitor.visit(this.getValue(), type, methodVisitor);
    }
}