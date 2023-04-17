package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class ArrayInitializer extends ASTNode implements Visitable {

    ArrayType type;
    ArrayOfExpression arraySize;

    public ArrayInitializer(ArrayType type, ArrayOfExpression arrayOfExpression) {
        super();
        this.type = type;
        this.arraySize = arrayOfExpression;
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

    public ArrayOfExpression getArraySize() {
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

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
