package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;

public class CreateRecordVariables extends CreateVariables {

    RecordCall recordCall;

    public CreateRecordVariables(Keyword stateKeyword, Identifier variableIdentifier, Type type, RecordCall recordCall) {
        super(stateKeyword, variableIdentifier, type);
        this.recordCall = recordCall;
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
