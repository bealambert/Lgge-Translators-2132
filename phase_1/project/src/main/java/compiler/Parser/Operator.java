package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.SpecialCharacter;
import compiler.Semantic.*;

public class Operator extends ASTNode implements Visitable {

    private SpecialCharacter specialCharacter;

    public Operator(SpecialCharacter specialCharacter) {
        this.specialCharacter = specialCharacter;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "specialCharacter=" + specialCharacter +
                '}';
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
