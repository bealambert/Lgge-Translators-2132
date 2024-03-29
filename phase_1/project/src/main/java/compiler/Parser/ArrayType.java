package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;
import compiler.Token;

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

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

}
