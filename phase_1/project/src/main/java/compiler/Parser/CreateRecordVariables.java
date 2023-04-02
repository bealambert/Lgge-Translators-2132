package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

import java.util.ArrayList;

public class CreateRecordVariables extends CreateVariables {

    RecordCall recordCall;

    public CreateRecordVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type, RecordCall recordCall) {
        super(stateKeyword, variableIdentifier, type);
        this.recordCall = recordCall;
    }
}
