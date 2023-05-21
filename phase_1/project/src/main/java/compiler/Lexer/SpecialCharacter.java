package compiler.Lexer;

public class SpecialCharacter implements Symbol {

    private final String attribute;
    private final String name = "SpecialCharacter";
    private final String[] acceptedAttributes = new String[]{
            "=", "+", "-", "*", "/", "%",
            "==", "<>", "<", ">", "<=", ">="
            , "(", ")", "{", "}", "[", "]",
            ".", ";", ","
    };

    public SpecialCharacter(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getAttribute() {
        return this.attribute;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String[] getAcceptedAttributes() {
        return acceptedAttributes;
    }

    @Override
    public String toString() {
        return "<" + this.name + ", " + this.getAttribute() + ">";
    }
}