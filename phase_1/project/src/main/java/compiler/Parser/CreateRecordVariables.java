package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;

import java.util.ArrayList;

public class CreateRecordVariables extends CreateVariables implements Visitable {

    RecordCall recordCall;

    public CreateRecordVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type, RecordCall recordCall) {
        super(stateKeyword, variableIdentifier, type);
        this.recordCall = recordCall;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
