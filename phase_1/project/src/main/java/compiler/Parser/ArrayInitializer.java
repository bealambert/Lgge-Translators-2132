package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class ArrayInitializer extends ASTNode {

    ArrayType type;
    Expression arraySize;

    public ArrayInitializer(ArrayType type, Expression arraySize) {
        super();
        this.type = type;
        this.arraySize = arraySize;
    }

    @Override
    public String toString() {
        return "ArrayInitializer{" +
                "type=" + type +
                ", arraySize=" + arraySize +
                '}';
    }
}
