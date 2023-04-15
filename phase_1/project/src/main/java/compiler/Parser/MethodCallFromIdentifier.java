package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class MethodCallFromIdentifier extends MethodCall {

    Identifier objectIdentifier;

    public MethodCallFromIdentifier(Identifier objectIdentifier, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.objectIdentifier = objectIdentifier;
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
