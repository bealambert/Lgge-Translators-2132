package compiler.Lexer;

public class Comment extends Token{

    private final String comments;
    public Comment(String attribute) {
        super("Comment");
        this.comments = attribute;
    }

    public String getComments() {
        return comments;
    }
}
