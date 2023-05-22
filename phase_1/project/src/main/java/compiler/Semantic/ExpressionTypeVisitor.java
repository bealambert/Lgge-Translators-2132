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

    public Type visit(ReadInt readInt) throws SemanticAnalysisException;

    public Type visit(ReadReal readReal) throws SemanticAnalysisException;

    public Type visit(ReadString readString) throws SemanticAnalysisException;

    public Type visit(Write write) throws SemanticAnalysisException;

    public Type visit(Writeln writeln) throws SemanticAnalysisException;

    public Type visit(WriteInt writeInt) throws SemanticAnalysisException;

    public Type visit(WriteReal writeReal) throws SemanticAnalysisException;


    public Type visit(Not not) throws SemanticAnalysisException;

    public Type visit(Len len) throws SemanticAnalysisException;

    public Type visit(Chr chr) throws SemanticAnalysisException;

    public Type visit(Floor floor) throws SemanticAnalysisException;

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
