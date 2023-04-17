package compiler;

import compiler.Lexer.Symbol;
import compiler.Parser.Type;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;

public class MyNode {
    Object value;
    String type;
    MyNode left;
    MyNode right;

    public MyNode(Object v) {
        this.value = v;
        if (v instanceof Symbol) {
            this.type = ((Symbol) v).getName();
        }
        left = null;
        right = null;
    }

    public MyNode(Object v, MyNode left, MyNode right) {
        this.value = v;
        if (v instanceof Symbol) {
            this.type = ((Symbol) v).getName();
        }
        this.left = left;
        this.right = right;
    }

    public MyNode getLeft() {
        return left;
    }

    public MyNode getRight() {
        return right;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }


    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
