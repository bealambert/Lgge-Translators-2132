package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Lexer.SpecialCharacter;

import java.util.ArrayList;

public class Records extends ASTNode {


    Keyword keyword;
    Identifier identifier;
    SpecialCharacter openBracket;
    ArrayList<RecordVariable> recordVariable;
    SpecialCharacter closeBracket;

    public Records(Keyword keyword, Identifier identifier, SpecialCharacter openBracket,
                   ArrayList<RecordVariable> recordVariable, SpecialCharacter closeBracket) {
        super();
        this.keyword = keyword;
        this.identifier = identifier;
        this.openBracket = openBracket;
        this.recordVariable = recordVariable;
        this.closeBracket = closeBracket;
    }


}
