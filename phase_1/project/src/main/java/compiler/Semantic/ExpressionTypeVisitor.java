package compiler.Semantic;

import compiler.ASMGenerator.ASMClassWriterVisitor;
import compiler.ASTNode;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;

public interface ExpressionTypeVisitor {

    TreatSemanticCases treatSemanticCases = new TreatSemanticCases();
    TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
    MakeSemanticAnalysisVisitor makeSemanticAnalysisVisitor = new MakeSemanticAnalysisVisitor();

    public Type visit(ReturnVoid returnVoid) throws SemanticAnalysisException;

    public Type visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException;

    public Type visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException;

    public Type visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException;

    public Type visit(Reassignment reassignment) throws SemanticAnalysisException;

    public Type visit(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException;

    public Type visit(Expression expression) throws SemanticAnalysisException;

    Type visit(RecordParameter recordParameter) throws SemanticAnalysisException;

    Type visit(InitializeRecords initializeRecords) throws SemanticAnalysisException;

    Type visit(ExpressionParameter expressionParameter) throws SemanticAnalysisException;

    Type visit(ArrayInitializerParameter arrayInitializerParameter) throws SemanticAnalysisException;

    Type visit(FunctionCallParameter functionCallParameter) throws SemanticAnalysisException;

    Type visit(Param param) throws SemanticAnalysisException;

    Type visit(Variable variable) throws SemanticAnalysisException;

    Type visit(Values values) throws SemanticAnalysisException;

    Type visit(FunctionCall functionCall) throws SemanticAnalysisException;

    Type visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException;

    Type visit(MethodCall methodCall) throws SemanticAnalysisException;

    Type visit(MyNode myNode) throws SemanticAnalysisException;

    Type visit(CreateProcedure createProcedure) throws SemanticAnalysisException;

    Type visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException;

    Type visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException;

    Type visit(CreateFunctionCallParameterVariable createFunctionCallParameterVariable) throws SemanticAnalysisException;

    Type visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException;

    Type visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException;

    Type visit(CreateVariables createVariables) throws SemanticAnalysisException;

    Type visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException;

    Type visit(ASTNode astNode) throws SemanticAnalysisException;

    Type visit(Type type) throws SemanticAnalysisException;
}
