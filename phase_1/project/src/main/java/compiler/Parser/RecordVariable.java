package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.SpecialCharacter;
import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class RecordVariable extends ASTNode {

    Identifier identifier;
    Type type;

    public RecordVariable(Identifier identifier, Type type) {
        super();
        this.identifier = identifier;
        this.type = type;
    }
}
