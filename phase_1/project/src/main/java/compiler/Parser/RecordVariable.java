package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.SpecialCharacter;
import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class RecordVariable extends ASTNode {

    Identifier identifier;
    Type type;
    SpecialCharacter specialCharacter;

    public RecordVariable(Identifier identifier, Type type, SpecialCharacter specialCharacter) {
        super();
        this.identifier = identifier;
        this.type = type;
        this.specialCharacter = specialCharacter;
    }
}
