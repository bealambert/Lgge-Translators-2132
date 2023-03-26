package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.SpecialCharacter;

public class Operator extends ASTNode {

    private SpecialCharacter specialCharacter;

    public Operator(SpecialCharacter specialCharacter) {
        this.specialCharacter = specialCharacter;
    }

}
