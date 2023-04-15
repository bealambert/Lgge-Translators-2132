package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;

public class MethodCall extends Expression {

    Identifier methodIdentifier;

    public MethodCall(Identifier methodIdentifier) {
        super(new ArrayList<>());

        this.methodIdentifier = methodIdentifier;
    }

    @Override
    public String toString() {
        return "MethodCall{" +
                "methodIdentifier=" + methodIdentifier +
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
