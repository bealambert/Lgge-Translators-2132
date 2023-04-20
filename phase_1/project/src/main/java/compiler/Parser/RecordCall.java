package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class RecordCall extends FunctionCallParameter implements Visitable {

    Records records;
    ArrayList<FunctionCallParameter> functionCallParameters;

    public RecordCall(Identifier identifier, ArrayList<FunctionCallParameter> functionCallParameters) {
        this.records = new Records(identifier);
        this.functionCallParameters = functionCallParameters;
    }

    public Records getRecords() {
        return records;
    }

    public ArrayList<FunctionCallParameter> getFunctionCallParameters() {
        return functionCallParameters;
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
