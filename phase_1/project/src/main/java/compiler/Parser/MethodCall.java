package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class MethodCall extends Expression implements Visitable {

    Identifier methodIdentifier;

    public MethodCall(Identifier methodIdentifier) {
        super();
        this.methodIdentifier = methodIdentifier;
    }

    @Override
    public String toString() {
        return "MethodCall{" +
                "methodIdentifier=" + methodIdentifier +
                '}';
    }

    public Identifier getMethodIdentifier() {
        return methodIdentifier;
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
