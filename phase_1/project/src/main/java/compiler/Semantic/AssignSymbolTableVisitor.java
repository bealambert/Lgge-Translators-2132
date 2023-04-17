package compiler.Semantic;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Parser.*;

import java.util.ArrayList;

public class AssignSymbolTableVisitor implements Visitor {


    @Override
    public void visit(ArrayOfExpression arrayOfExpression, SymbolTable symbolTable) {
        arrayOfExpression.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(AccessToIndexArray accessToIndexArray, SymbolTable symbolTable) {
        accessToIndexArray.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(ArrayInitializer arrayInitializer, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(arrayInitializer.getType(), arrayInitializer);
    }

    @Override
    public void visit(ArrayType arrayType, SymbolTable symbolTable) {
        arrayType.setSymbolTable(symbolTable);
    }


    @Override
    public void visit(Block block, SymbolTable symbolTable) {
        ArrayList<ASTNode> astNodes = block.getAttribute();
        for (int i = 0; i < astNodes.size(); i++) {
            ASTNode astNode = astNodes.get(i);
            astNode.accept(this, symbolTable);
        }

        block.setSymbolTable(symbolTable);

    }

    @Override
    public void visit(Condition condition, SymbolTable symbolTable) {
        condition.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(CreateArrayVariable createArrayVariable, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createArrayVariable.getVariableIdentifier(), createArrayVariable);
        createArrayVariable.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(CreateExpressionVariable createExpressionVariable, SymbolTable symbolTable) {

        symbolTable.symbolTable.put(createExpressionVariable.getVariableIdentifier(), createExpressionVariable);
        createExpressionVariable.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(CreateProcedure createProcedure, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createProcedure.getProcedureName(), createProcedure);
        createProcedure.setSymbolTable(symbolTable);
        // TODO : Do we need to set all attributes `symbolTable` field to the current ST ? or is it enough?
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);
        ArrayList<Param> procedureParameters = createProcedure.getParams();
        for (int i = 0; i < createProcedure.getParams().size(); i++) {
            nestedScopeSymbolTable.symbolTable.put(procedureParameters.get(i).getIdentifier(), procedureParameters.get(i));
        }
        Block body = createProcedure.getBody();
        body.setSymbolTable(nestedScopeSymbolTable);
        body.accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(CreateRecordVariables createRecordVariables, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createRecordVariables.getVariableIdentifier(), createRecordVariables);
        createRecordVariables.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createReferencedVariable.getVariableIdentifier(), createReferencedVariable);
        createReferencedVariable.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(CreateVariables createVariables, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createVariables.getVariableIdentifier(), createVariables);
        createVariables.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createVoidVariable.getVariableIdentifier(), createVoidVariable);
        createVoidVariable.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(Expression expression, SymbolTable symbolTable) {
        expression.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(ForLoop forLoop, SymbolTable symbolTable) {
        forLoop.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);
        nestedScopeSymbolTable.symbolTable.put(forLoop.getIdentifier(), forLoop);

        Block body = forLoop.getBody();
        body.accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(FunctionCall functionCall, SymbolTable symbolTable) {
        functionCall.setSymbolTable(symbolTable);
    }


    @Override
    public void visit(IfCondition ifCondition, SymbolTable symbolTable) {
        ifCondition.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);
        Block body = ifCondition.getIfBlock();
        body.accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(IfElse ifElse, SymbolTable symbolTable) {
        ifElse.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable1 = new SymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable2 = new SymbolTable(symbolTable);

        Block ifBlock = ifElse.getIfBlock();
        ifBlock.accept(this, nestedScopeSymbolTable1);

        Block elseBlock = ifElse.getElseBlock();
        elseBlock.accept(this, nestedScopeSymbolTable2);

    }

    @Override
    public void visit(InitializeRecords initializeRecords, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(initializeRecords.getRecords().getIdentifier(), initializeRecords);
        initializeRecords.setSymbolTable(symbolTable);
    }


    @Override
    public void visit(MethodCall methodCall, SymbolTable symbolTable) {
        methodCall.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier, SymbolTable symbolTable) {
        methodCallFromIdentifier.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray, SymbolTable symbolTable) {
        methodCallFromIndexArray.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(Param param, SymbolTable symbolTable) {
        param.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(Reassignment reassignment, SymbolTable symbolTable) {

    }

    @Override
    public void visit(RecordCall recordCall, SymbolTable symbolTable) {
        recordCall.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(RecordParameter recordParameter, SymbolTable symbolTable) {
        recordParameter.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(Records records, SymbolTable symbolTable) {
        records.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(ReturnStatement returnStatement, SymbolTable symbolTable) {
        returnStatement.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(Type type, SymbolTable symbolTable) {
        type.setSymbolTable(symbolTable);

    }

    @Override
    public void visit(Values values, SymbolTable symbolTable) {
        values.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(WhileLoop whileLoop, SymbolTable symbolTable) {
        whileLoop.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);

        Block body = whileLoop.getBody();
        body.accept(this, nestedScopeSymbolTable);

    }

    @Override
    public void visit(Identifier identifier, SymbolTable symbolTable) {
        identifier.setSymbolTable(symbolTable);
    }

}
