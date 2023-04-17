package compiler.Semantic;

import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;

public interface ExpressionTypeVisitor {

    TreatSemanticCases treatSemanticCases = new TreatSemanticCases();
    TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();

    Type visit(Variable variable) throws SemanticAnalysisException;

    Type visit(Values values) throws SemanticAnalysisException;

    Type visit(FunctionCall functionCall) throws SemanticAnalysisException;

    Type visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException;

    Type visit(MethodCall methodCall) throws SemanticAnalysisException;

    Type visit(BinaryComparator binaryComparator) throws SemanticAnalysisException;

    Type visit(BinaryModulo binaryModulo) throws SemanticAnalysisException;


    Type visit(BinaryOperator binaryOperator) throws SemanticAnalysisException;

    Type visit(MyNode myNode) throws SemanticAnalysisException;

    Type visit(CreateProcedure createProcedure) throws SemanticAnalysisException;

    Type visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException;

    Type visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException;

    Type visit(CreateFunctionCallParameterVariable createFunctionCallParameterVariable) throws SemanticAnalysisException;

    Type visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException;

    Type visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException;

    Type visit(CreateVariables createVariables) throws SemanticAnalysisException;

    Type visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException;
}
