package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class CreateRecordVariables extends CreateVariables implements Visitable {

    RecordCall recordCall;

    public CreateRecordVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type, RecordCall recordCall) {
        super(stateKeyword, variableIdentifier, type);
        this.recordCall = recordCall;
    }

    public RecordCall getRecordCall() {
        return recordCall;
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
