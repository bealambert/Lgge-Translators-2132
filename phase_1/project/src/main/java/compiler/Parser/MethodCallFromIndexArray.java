package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class MethodCallFromIndexArray extends MethodCall {

    AccessToIndexArray accessToIndexArray;

    public MethodCallFromIndexArray(AccessToIndexArray accessToIndexArray, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.accessToIndexArray = accessToIndexArray;
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
