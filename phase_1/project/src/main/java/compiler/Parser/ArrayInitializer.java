package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

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

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
