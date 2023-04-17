package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class InitializeRecords extends ASTNode implements Visitable {


    Keyword keyword;
    Records records;
    ArrayList<RecordParameter> recordVariable;

    public InitializeRecords(Keyword keyword, Records records,
                             ArrayList<RecordParameter> recordVariable) {
        this.keyword = keyword;
        this.records = records;
        this.recordVariable = recordVariable;
    }

    public Records getRecords() {
        return records;
    }

    public ArrayList<RecordParameter> getRecordVariable() {
        return recordVariable;
    }

    public Keyword getKeyword() {
        return keyword;
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
