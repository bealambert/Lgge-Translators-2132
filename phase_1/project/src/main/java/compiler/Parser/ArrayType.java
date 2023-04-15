package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class ArrayType extends Type implements Visitable {

    public ArrayType(Identifier attribute) {
        super(attribute);
    }

    public String getName() {
        return "ArrayType";
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

}
