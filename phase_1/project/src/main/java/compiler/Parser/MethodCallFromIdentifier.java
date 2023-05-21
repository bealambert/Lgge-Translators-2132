package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class MethodCallFromIdentifier extends MethodCall implements Visitable {

    Identifier objectIdentifier;

    public MethodCallFromIdentifier(Identifier objectIdentifier, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.objectIdentifier = objectIdentifier;
    }

    public Identifier getObjectIdentifier() {
        return objectIdentifier;
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
