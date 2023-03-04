package compiler.Lexer;

import java.util.Arrays;
import java.util.HashSet;

public interface Symbol {

    /*private Token token;
    private String attribute;
    private String name = "Symbol";

    private final String [] acceptedTokenAsString = new String[] {"Boolean", "Comment", "Constant", "Identifier", "Keyword",
    "NaturalNumber", "RealNumber", "SpecialCharacter", "Strings", "Symbol" , "Variable"};
    private final HashSet<String> acceptedTokens = new HashSet<>(Arrays.asList(acceptedTokenAsString));
*/
/*    public Symbol (Token token, String attribute){
        this. = token;
        this.attribute = attribute;
    }*/

    public String getAttribute();

    public Token getToken();

    public String getName();

    @Override
    public String toString();

}
