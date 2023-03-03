package compiler.Lexer;

public class SpecialCharacter extends Token{

    private final String specialCharacter;
    public SpecialCharacter(String attribute) {
        super("SpecialCharacter");
        this.specialCharacter = attribute;
    }

    public String getSpecialCharacter() {
        return specialCharacter;
    }
}
