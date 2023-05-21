package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class CreateFunctionCallParameterVariable extends CreateVariables {

    ArrayList<FunctionCallParameter> functionCallParameters;

    public CreateFunctionCallParameterVariable(Keyword stateKeyword, Identifier variableIdentifier, Type type,
                                               ArrayList<FunctionCallParameter> functionCallParameters) {
        super(stateKeyword, variableIdentifier, type);
        this.functionCallParameters = functionCallParameters;
    }

    public ArrayList<FunctionCallParameter> getFunctionCallParameters() {
        return functionCallParameters;
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

}
