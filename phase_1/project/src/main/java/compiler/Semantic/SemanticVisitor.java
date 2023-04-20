package compiler.Semantic;

import compiler.ASTNode;
import compiler.BinaryTree;
import compiler.Lexer.Identifier;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;

import java.util.Stack;

public interface SemanticVisitor {

    TreatSemanticCases treatSemanticCases = new TreatSemanticCases();
    AssignSymbolTableVisitor assignSymbolTableVisitor = new AssignSymbolTableVisitor();
    Stack<Identifier> functionNameStack = new Stack<>();

    void visit(ExpressionParameter expressionParameter, SymbolTable symbolTable) throws SemanticAnalysisException;

    void visit(ArrayInitializerParameter arrayInitializerParameter, SymbolTable symbolTable) throws SemanticAnalysisException;

    void visit(FunctionCallParameter functionCallParameter) throws SemanticAnalysisException;

    void visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException;

    void visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException;

    void visit(ArrayType arrayType) throws SemanticAnalysisException;

    void visit(Block block) throws SemanticAnalysisException;

    void visit(Condition condition) throws SemanticAnalysisException;

    void visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException;

    void visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException;

    void visit(CreateProcedure createProcedure) throws SemanticAnalysisException;

    void visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException;

    void visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException;

    void visit(CreateVariables createVariables) throws SemanticAnalysisException;

    void visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException;

    void visit(Expression expression) throws SemanticAnalysisException;

    void visit(ForLoop forLoop) throws SemanticAnalysisException;

    void visit(FunctionCall functionCall) throws SemanticAnalysisException;


    void visit(IfCondition ifCondition) throws SemanticAnalysisException;

    void visit(IfElse ifElse) throws SemanticAnalysisException;

    void visit(InitializeRecords initializeRecords) throws SemanticAnalysisException;

    void visit(MethodCall methodCall) throws SemanticAnalysisException;

    void visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException;

    void visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException;

    void visit(Param param) throws SemanticAnalysisException;

    void visit(Reassignment reassignment) throws SemanticAnalysisException;

    void visit(RecordCall recordCall) throws SemanticAnalysisException;

    void visit(RecordParameter recordParameter) throws SemanticAnalysisException;

    void visit(Records records) throws SemanticAnalysisException;

    void visit(ReturnStatement returnStatement) throws SemanticAnalysisException;

    void visit(Type type) throws SemanticAnalysisException;

    void visit(Values values) throws SemanticAnalysisException;

    void visit(WhileLoop whileLoop) throws SemanticAnalysisException;

    void visit(Identifier identifier) throws SemanticAnalysisException;

    void visit(BinaryTree binaryTree) throws SemanticAnalysisException;

    void visit(MyNode myNode) throws SemanticAnalysisException;

    void visit(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException;

}
