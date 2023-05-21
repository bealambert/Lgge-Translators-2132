package compiler;

public class AST {

    private ASTNode root = null;
    private ASTNode first = null;
    private ASTNode last = null;
    private int size = 0;

    public AST() {
    }


    public AST(ASTNode root) {
        this.root = root;
        this.first = root;
        this.last = root;
        this.size = 1;
    }

    public void add(ASTNode to_add) {
        if (getSize() == 0) {
            setFirst(to_add);
            setLast(to_add);
            setRoot(to_add);
            setSize(1);
            return;
        }
        this.last.setNext(to_add);
        setLast(to_add);
        setSize(getSize() + 1);

    }

    public int remove() {
        if (getSize() == 0) {
            return -1;
        }
        if (getSize() == 1) {
            setFirst(null);
            setLast(null);
            setRoot(null);
            setSize(0);

        } else {
            setFirst(getFirst().getNext());
            setSize(getSize() - 1);
        }
        return 1;
    }

    public ASTNode getFirst() {
        return first;
    }

    public void setFirst(ASTNode first) {
        this.first = first;
    }

    public ASTNode getLast() {
        return last;
    }

    public void setLast(ASTNode last) {
        this.last = last;
    }

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
