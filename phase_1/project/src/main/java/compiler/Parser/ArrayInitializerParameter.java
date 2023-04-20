package compiler.Parser;

import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;

public class ArrayInitializerParameter extends FunctionCallParameter {

    ArrayInitializer arrayInitializer;

    public ArrayInitializerParameter(ArrayInitializer arrayInitializer) {
        this.arrayInitializer = arrayInitializer;
    }

    public ArrayInitializer getArrayInitializer() {
        return arrayInitializer;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException {
        semanticVisitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
