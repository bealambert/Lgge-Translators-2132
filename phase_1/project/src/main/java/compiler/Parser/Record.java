package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Lexer.SpecialCharacter;
import compiler.Token;

import java.util.ArrayList;

public class Record extends ASTNode {


    Keyword keyword;
    Identifier identifier;
    SpecialCharacter openBracket;
    ArrayList<RecordVariable> recordVariable;
    SpecialCharacter closeBracket;

    public Record(Keyword keyword, Identifier identifier, SpecialCharacter openBracket,
                  ArrayList<RecordVariable> recordVariable, SpecialCharacter closeBracket) {
        super();
        this.keyword = keyword;
        this.identifier = identifier;
        this.openBracket = openBracket;
        this.recordVariable = recordVariable;
        this.closeBracket = closeBracket;
    }


}
