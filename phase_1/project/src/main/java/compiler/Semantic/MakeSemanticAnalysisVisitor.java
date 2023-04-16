package compiler.Semantic;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;
import compiler.Token;

import java.util.ArrayList;
import java.util.Stack;

public class MakeSemanticAnalysisVisitor implements SemanticVisitor {

    TreatSemanticCases treatSemanticCases = new TreatSemanticCases();
    Stack<Identifier> functionNameStack = new Stack<>();

    @Override
    public void visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {
        //String input = "var c int[] = int[](5);";
        // var p int = c[0];
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(accessToIndexArray, accessToIndexArray.getSymbolTable());


    }

    @Override
    public void visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException {

    }


    @Override
    public void visit(ArrayType arrayType) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Block block) throws SemanticAnalysisException {
        ArrayList<ASTNode> astNodeArrayList = block.getAttribute();
        for (int i = 0; i < astNodeArrayList.size(); i++) {
            astNodeArrayList.get(i).accept(this);
        }
    }

    @Override
    public void visit(Condition condition) throws SemanticAnalysisException {
        // expected boolean expression at the end
        Type observedType = treatSemanticCases.treatExpression(condition.getExpression());
        if (!observedType.getAttribute().equals("bool")) {
            throw new SemanticAnalysisException("");
        }
    }

    @Override
    public void visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException {
        ArrayInitializer arrayInitializer = createArrayVariable.getArrayInitializer();

        ArrayType left = (ArrayType) createArrayVariable.getType();
        ArrayType right = arrayInitializer.getType();
        treatSemanticCases.isEqual(left, right);
    }

    @Override
    public void visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException {
        Type expectedType = createExpressionVariable.getType();
        Type observedType = treatSemanticCases.treatExpression(createExpressionVariable.getExpression());
        treatSemanticCases.isEqual(expectedType, observedType);
    }

    @Override
    public void visit(CreateProcedure createProcedure) throws SemanticAnalysisException {
        // need to check the return value
        Block body = createProcedure.getBody();
        body.accept(this);


    }

    @Override
    public void visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException {

    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(CreateVariables createVariables) throws SemanticAnalysisException {

    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Expression expression) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ForLoop forLoop) throws SemanticAnalysisException {

    }

    @Override
    public void visit(FunctionCall functionCall) throws SemanticAnalysisException {

    }

    @Override
    public void visit(IfCondition ifCondition) throws SemanticAnalysisException {
        Condition condition = ifCondition.getCondition();
        Block body = ifCondition.getIfBlock();

        condition.accept(this);
        body.accept(this);
    }

    @Override
    public void visit(IfElse ifElse) throws SemanticAnalysisException {

    }

    @Override
    public void visit(InitializeRecords initializeRecords) throws SemanticAnalysisException {

    }

    @Override
    public void visit(MethodCall methodCall) throws SemanticAnalysisException {

    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException {

    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Param param) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Reassignment reassignment) throws SemanticAnalysisException {

    }

    @Override
    public void visit(RecordCall recordCall) throws SemanticAnalysisException {

    }

    @Override
    public void visit(RecordParameter recordParameter) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Records records) throws SemanticAnalysisException {

    }

    @Override
    public void visit(ReturnStatement returnStatement) throws SemanticAnalysisException {
        SymbolTable symbolTable = returnStatement.getSymbolTable();
        Type observedType = treatSemanticCases.treatExpression(returnStatement.getExpression());
        Identifier functionName = functionNameStack.peek();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionName, symbolTable);
        Type expectedType = createProcedure.getReturnType();
        treatSemanticCases.isEqual(expectedType, observedType);
    }

    @Override
    public void visit(Type type) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Values values) throws SemanticAnalysisException {

    }

    @Override
    public void visit(WhileLoop whileLoop) throws SemanticAnalysisException {

    }

    @Override
    public void visit(Identifier identifier) throws SemanticAnalysisException {

    }
}



