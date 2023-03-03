package compiler.Lexer;

import java.util.Arrays;
import java.util.HashSet;

public class Symbol {

    private Token token;
    private String attribute;
    private String name = "Symbol";

    private final String [] acceptedTokenAsString = new String[] {"Boolean", "Comment", "Constant", "Identifier", "Keyword",
    "NaturalNumber", "RealNumber", "SpecialCharacter", "Strings", "Symbol" , "Variable"};
    private final HashSet<String> acceptedTokens = new HashSet<>(Arrays.asList(acceptedTokenAsString));

    public Symbol (Token token, String attribute){
        this.token = token;
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public Token getToken() {
        return this.token;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "<" + this.getToken() + ", " + this.getAttribute() + ">" ;
    }

}
