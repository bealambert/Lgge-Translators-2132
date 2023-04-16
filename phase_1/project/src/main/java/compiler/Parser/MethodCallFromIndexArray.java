package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class MethodCallFromIndexArray extends MethodCall implements Visitable {

    AccessToIndexArray accessToIndexArray;

    public MethodCallFromIndexArray(AccessToIndexArray accessToIndexArray, Identifier methodIdentifier) {
        super(methodIdentifier);
        this.accessToIndexArray = accessToIndexArray;
    }

    public AccessToIndexArray getAccessToIndexArray() {
        return accessToIndexArray;
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
