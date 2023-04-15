package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class ArrayInitializer extends ASTNode implements Visitable {

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

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
