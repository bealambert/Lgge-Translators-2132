package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

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

    public ArrayType getType() {
        return type;
    }

    public Expression getArraySize() {
        return arraySize;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }
}
