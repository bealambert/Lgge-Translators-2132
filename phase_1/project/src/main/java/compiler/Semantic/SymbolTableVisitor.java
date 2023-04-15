package compiler.Semantic;

import compiler.Lexer.Identifier;
import compiler.Parser.*;

public interface SymbolTableVisitor {

    void visit(AccessToIndexArray accessToIndexArray);

    void visit(ArrayInitializer arrayInitializer);

    void visit(ArrayType arrayType);

    void visit(AssignmentOperator assignmentOperator);

    void visit(Block block);

    void visit(Condition condition);

    void visit(CreateArrayVariable createArrayVariable);

    void visit(CreateExpressionVariable createExpressionVariable);

    void visit(CreateProcedure createProcedure);

    void visit(CreateRecordVariables createRecordVariables);

    void visit(CreateReferencedVariable createReferencedVariable);

    void visit(CreateVariables createVariables);

    void visit(CreateVoidVariable createVoidVariable);

    void visit(Expression expression);

    void visit(ForLoop forLoop);

    void visit(FunctionCall functionCall);

    void visit(IdentifierVariable identifierVariable);

    void visit(IfCondition ifCondition);

    void visit(IfElse ifElse);

    void visit(InitializeRecords initializeRecords);

    void visit(Method method);

    void visit(MethodCall methodCall);

    void visit(MethodCallFromIdentifier methodCallFromIdentifier);

    void visit(MethodCallFromIndexArray methodCallFromIndexArray);

    void visit(Operator operator);

    void visit(Param param);

    void visit(Reassignment reassignment);

    void visit(RecordCall recordCall);

    void visit(RecordParameter recordParameter);

    void visit(Records records);

    void visit(ReturnStatement returnStatement);

    void visit(Type type);

    void visit(Values values);

    void visit(WhileLoop whileLoop);

    void visit(Identifier identifier);

}
