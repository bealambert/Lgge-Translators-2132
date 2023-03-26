package compiler;

import java.util.ArrayList;

public class ASTNode {

    private ASTNode next;

    public ASTNode() {

    }

    public ASTNode getNext() {
        return next;
    }

    public void setNext(ASTNode next) {
        this.next = next;
    }
}
