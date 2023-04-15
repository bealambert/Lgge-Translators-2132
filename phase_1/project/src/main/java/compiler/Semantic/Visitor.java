package compiler.Semantic;

import compiler.Lexer.Identifier;
import compiler.Parser.*;

public interface Visitor {

    void visit(AccessToIndexArray accessToIndexArray, SymbolTable symbolTable);

    void visit(ArrayInitializer arrayInitializer, SymbolTable symbolTable);

    void visit(ArrayType arrayType, SymbolTable symbolTable);

    void visit(AssignmentOperator assignmentOperator, SymbolTable symbolTable);

    void visit(Block block, SymbolTable symbolTable);

    void visit(Condition condition, SymbolTable symbolTable);

    void visit(CreateArrayVariable createArrayVariable, SymbolTable symbolTable);

    void visit(CreateExpressionVariable createExpressionVariable, SymbolTable symbolTable);

    void visit(CreateProcedure createProcedure, SymbolTable symbolTable);

    void visit(CreateRecordVariables createRecordVariables, SymbolTable symbolTable);

    void visit(CreateReferencedVariable createReferencedVariable, SymbolTable symbolTable);

    void visit(CreateVariables createVariables, SymbolTable symbolTable);

    void visit(CreateVoidVariable createVoidVariable, SymbolTable symbolTable);

    void visit(Expression expression, SymbolTable symbolTable);

    void visit(ForLoop forLoop, SymbolTable symbolTable);

    void visit(FunctionCall functionCall, SymbolTable symbolTable);


    void visit(IfCondition ifCondition, SymbolTable symbolTable);

    void visit(IfElse ifElse, SymbolTable symbolTable);

    void visit(InitializeRecords initializeRecords, SymbolTable symbolTable);

    void visit(MethodCall methodCall, SymbolTable symbolTable);

    void visit(MethodCallFromIdentifier methodCallFromIdentifier, SymbolTable symbolTable);

    void visit(MethodCallFromIndexArray methodCallFromIndexArray, SymbolTable symbolTable);

    void visit(Operator operator, SymbolTable symbolTable);

    void visit(Param param, SymbolTable symbolTable);

    void visit(Reassignment reassignment, SymbolTable symbolTable);

    void visit(RecordCall recordCall, SymbolTable symbolTable);

    void visit(RecordParameter recordParameter, SymbolTable symbolTable);

    void visit(Records records, SymbolTable symbolTable);

    void visit(ReturnStatement returnStatement, SymbolTable symbolTable);

    void visit(Type type, SymbolTable symbolTable);

    void visit(Values values, SymbolTable symbolTable);

    void visit(WhileLoop whileLoop, SymbolTable symbolTable);

    void visit(Identifier identifier, SymbolTable symbolTable);

}
