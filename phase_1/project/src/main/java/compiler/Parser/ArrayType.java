package compiler.Parser;

import compiler.Lexer.Identifier;

public class ArrayType extends Type {

    public ArrayType(Identifier attribute) {
        super(attribute);
    }

    public String getName() {
        return "ArrayType";
    }

}
