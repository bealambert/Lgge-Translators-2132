package compiler.Semantic;

import compiler.ASTNode;
import compiler.ClassName;
import compiler.Lexer.Identifier;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;
import compiler.Token;

import java.util.ArrayList;

public class AssignSymbolTableVisitor implements Visitor {


    @Override
    public void visit(AssignVariable assignVariable, SymbolTable symbolTable) {
        assignVariable.setSymbolTable(symbolTable);
        assignVariable.getAssignmentExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(AssignToRecordAttribute assignToRecordAttribute, SymbolTable symbolTable) {
        assignToRecordAttribute.setSymbolTable(symbolTable);
        assignToRecordAttribute.getMethodCallFromIdentifier().accept(this, symbolTable);
        assignToRecordAttribute.getAssignmentExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(AssignToRecordAttributeAtIndex assignToRecordAttributeAtIndex, SymbolTable symbolTable) {
        assignToRecordAttributeAtIndex.setSymbolTable(symbolTable);
        assignToRecordAttributeAtIndex.getMethodCallFromIndexArray().accept(this, symbolTable);
        assignToRecordAttributeAtIndex.getAssignmentExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(AssignToIndexArray assignToIndexArray, SymbolTable symbolTable) {
        assignToIndexArray.setSymbolTable(symbolTable);
        assignToIndexArray.getAccessToIndexArray().accept(this, symbolTable);
        assignToIndexArray.getAssignmentExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(ForLoopAssignVariable forLoopAssignVariable, SymbolTable symbolTable) {
        forLoopAssignVariable.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);

        Block body = forLoopAssignVariable.getBody();
        body.accept(this, nestedScopeSymbolTable);

        forLoopAssignVariable.getIncrementBy().accept(this, nestedScopeSymbolTable);
        forLoopAssignVariable.getEnd().accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(ForLoopCreateVariable forLoopCreateVariable, SymbolTable symbolTable) {
        forLoopCreateVariable.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);

        nestedScopeSymbolTable.symbolTable.put(forLoopCreateVariable.getCreateVariables().getVariableIdentifier().getIdentifier().getAttribute(), forLoopCreateVariable.getCreateVariables());


        //nestedScopeSymbolTable.symbolTable.put(forLoopCreateVariable.getCreateVariables().getVariableIdentifier().getIdentifier().getAttribute(), forLoopCreateVariable);

        Block body = forLoopCreateVariable.getBody();
        body.accept(this, nestedScopeSymbolTable);

        forLoopCreateVariable.getIncrementBy().accept(this, nestedScopeSymbolTable);
        forLoopCreateVariable.getEnd().accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(ExpressionParameter expressionParameter, SymbolTable symbolTable) {
        expressionParameter.setSymbolTable(symbolTable);
        expressionParameter.getExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(ArrayInitializerParameter arrayInitializerParameter, SymbolTable symbolTable) {
        arrayInitializerParameter.setSymbolTable(symbolTable);
        arrayInitializerParameter.getArrayInitializer().accept(this, symbolTable);
    }

    @Override
    public void visit(FunctionCallParameter functionCallParameter, SymbolTable symbolTable) {
        functionCallParameter.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(ArrayOfExpression arrayOfExpression, SymbolTable symbolTable) {
        arrayOfExpression.setSymbolTable(symbolTable);
        for (int i = 0; i < arrayOfExpression.getExpressions().size(); i++) {
            arrayOfExpression.getExpressions().get(i).accept(this, symbolTable);
        }
    }

    @Override
    public void visit(AccessToIndexArray accessToIndexArray, SymbolTable symbolTable) {
        accessToIndexArray.setSymbolTable(symbolTable);
        accessToIndexArray.getIdentifier().accept(this, symbolTable);
        accessToIndexArray.getArrayOfExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(ArrayInitializer arrayInitializer, SymbolTable symbolTable) {
        //symbolTable.symbolTable.put(arrayInitializer.getType().getAttribute(), arrayInitializer);
        arrayInitializer.getArraySize().accept(this, symbolTable);
        arrayInitializer.getType().accept(this, symbolTable);
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
            astNode.setSymbolTable(symbolTable);
            astNode.accept(this, symbolTable);
        }

        block.setSymbolTable(symbolTable);

    }

    @Override
    public void visit(Condition condition, SymbolTable symbolTable) {
        condition.setSymbolTable(symbolTable);
        condition.getArrayOfExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(CreateArrayVariable createArrayVariable, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createArrayVariable.getVariableIdentifier().getIdentifier().getAttribute(), createArrayVariable);
        createArrayVariable.setSymbolTable(symbolTable);
        createArrayVariable.getArrayInitializer().accept(this, symbolTable);
        createArrayVariable.getVariableIdentifier().accept(this, symbolTable);
        createArrayVariable.getType().accept(this, symbolTable);
    }

    @Override
    public void visit(CreateExpressionVariable createExpressionVariable, SymbolTable symbolTable) {

        symbolTable.symbolTable.put(createExpressionVariable.getVariableIdentifier().getIdentifier().getAttribute(), createExpressionVariable);
        createExpressionVariable.setSymbolTable(symbolTable);
        createExpressionVariable.getType().accept(this, symbolTable);
        createExpressionVariable.getVariableIdentifier().accept(this, symbolTable);
        createExpressionVariable.getArrayOfExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(CreateProcedure createProcedure, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createProcedure.getProcedureName().getAttribute(), createProcedure);
        createProcedure.setSymbolTable(symbolTable);
        // TODO : Do we need to set all attributes `symbolTable` field to the current ST ? or is it enough?
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);
        ArrayList<Param> procedureParameters = createProcedure.getParams();
        for (int i = 0; i < createProcedure.getParams().size(); i++) {
            nestedScopeSymbolTable.symbolTable.put(procedureParameters.get(i).getIdentifier().getAttribute(), procedureParameters.get(i));
            procedureParameters.get(i).accept(this, nestedScopeSymbolTable);
        }
        Block body = createProcedure.getBody();
        body.accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(CreateRecordVariables createRecordVariables, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createRecordVariables.getVariableIdentifier().getIdentifier().getAttribute(), createRecordVariables);
        createRecordVariables.setSymbolTable(symbolTable);
        createRecordVariables.getRecordCall().accept(this, symbolTable);
        createRecordVariables.getType().accept(this, symbolTable);
        createRecordVariables.getVariableIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createReferencedVariable.getVariableIdentifier().getIdentifier().getAttribute(), createReferencedVariable);
        createReferencedVariable.setSymbolTable(symbolTable);
        createReferencedVariable.getType().accept(this, symbolTable);
        createReferencedVariable.getReferencedIdentifier().accept(this, symbolTable);
        createReferencedVariable.getVariableIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(CreateVariables createVariables, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createVariables.getVariableIdentifier().getIdentifier().getAttribute(), createVariables);
        createVariables.setSymbolTable(symbolTable);
        createVariables.getType().accept(this, symbolTable);
        createVariables.getVariableIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(createVoidVariable.getVariableIdentifier().getIdentifier().getAttribute(), createVoidVariable);
        createVoidVariable.setSymbolTable(symbolTable);
        createVoidVariable.getType().accept(this, symbolTable);
        createVoidVariable.getVariableIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(Expression expression, SymbolTable symbolTable) {
        expression.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(ForLoop forLoop, SymbolTable symbolTable) {
        // implemented in child classes as it is only a parent class not used itself in practice
        forLoop.setSymbolTable(symbolTable);
    }

    @Override
    public void visit(FunctionCall functionCall, SymbolTable symbolTable) {
        functionCall.setSymbolTable(symbolTable);
        ArrayList<ArrayOfExpression> arrayOfExpressions = functionCall.getParams();
        for (int i = 0; i < arrayOfExpressions.size(); i++) {
            arrayOfExpressions.get(i).accept(this, symbolTable);
        }
        functionCall.getIdentifier().accept(this, symbolTable);
    }


    @Override
    public void visit(IfCondition ifCondition, SymbolTable symbolTable) {
        ifCondition.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);

        Block body = ifCondition.getIfBlock();
        ifCondition.getCondition().accept(this, nestedScopeSymbolTable);
        body.accept(this, nestedScopeSymbolTable);
    }

    @Override
    public void visit(IfElse ifElse, SymbolTable symbolTable) {
        ifElse.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable1 = new SymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable2 = new SymbolTable(symbolTable);

        ifElse.getCondition().accept(this, symbolTable);

        Block ifBlock = ifElse.getIfBlock();
        ifBlock.accept(this, nestedScopeSymbolTable1);

        Block elseBlock = ifElse.getElseBlock();
        elseBlock.accept(this, nestedScopeSymbolTable2);

    }

    @Override
    public void visit(InitializeRecords initializeRecords, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(initializeRecords.getRecords().getIdentifier().getAttribute(), initializeRecords);
        SymbolTable nestedSymbolTable = new SymbolTable(symbolTable);
        initializeRecords.setSymbolTable(nestedSymbolTable);
        ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
        for (int i = 0; i < recordParameters.size(); i++) {
            recordParameters.get(i).accept(this, nestedSymbolTable);
        }
        initializeRecords.getRecords().accept(this, symbolTable);
    }


    @Override
    public void visit(MethodCall methodCall, SymbolTable symbolTable) {
        // Parent class of two children that implements their own visit
        methodCall.setSymbolTable(symbolTable);

    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier, SymbolTable symbolTable) {
        methodCallFromIdentifier.setSymbolTable(symbolTable);
        methodCallFromIdentifier.getObjectIdentifier().accept(this, symbolTable);
        methodCallFromIdentifier.getMethodIdentifier().accept(this, symbolTable);

    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray, SymbolTable symbolTable) {
        methodCallFromIndexArray.setSymbolTable(symbolTable);
        methodCallFromIndexArray.getAccessToIndexArray().accept(this, symbolTable);
        methodCallFromIndexArray.getMethodIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(Param param, SymbolTable symbolTable) {
        param.setSymbolTable(symbolTable);
        param.getIdentifier().accept(this, symbolTable);
        param.getType().accept(this, symbolTable);
    }

    @Override
    public void visit(Reassignment reassignment, SymbolTable symbolTable) {
        reassignment.setSymbolTable(symbolTable);
        reassignment.getIdentifier().accept(this, symbolTable);
        reassignment.getArrayOfExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(RecordCall recordCall, SymbolTable symbolTable) {
        recordCall.setSymbolTable(symbolTable);
        recordCall.getRecords().accept(this, symbolTable);
        ArrayList<FunctionCallParameter> functionCallParameters = recordCall.getFunctionCallParameters();
        for (int i = 0; i < functionCallParameters.size(); i++) {
            functionCallParameters.get(i).accept(this, symbolTable);
        }
    }

    @Override
    public void visit(RecordParameter recordParameter, SymbolTable symbolTable) {
        symbolTable.symbolTable.put(recordParameter.getIdentifier().getAttribute(), recordParameter);
        recordParameter.setSymbolTable(symbolTable);
        recordParameter.getType().accept(this, symbolTable);
        recordParameter.getIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(Records records, SymbolTable symbolTable) {
        records.setSymbolTable(symbolTable);
        records.getIdentifier().accept(this, symbolTable);
    }

    @Override
    public void visit(ReturnStatement returnStatement, SymbolTable symbolTable) {
        returnStatement.setSymbolTable(symbolTable);
        returnStatement.getArrayOfExpression().accept(this, symbolTable);
    }

    @Override
    public void visit(Type type, SymbolTable symbolTable) {
        type.setSymbolTable(symbolTable);

    }

    @Override
    public void visit(Values values, SymbolTable symbolTable) {
        values.setSymbolTable(symbolTable);
        values.getType().accept(this, symbolTable);
    }

    @Override
    public void visit(WhileLoop whileLoop, SymbolTable symbolTable) {
        whileLoop.setSymbolTable(symbolTable);
        SymbolTable nestedScopeSymbolTable = new SymbolTable(symbolTable);

        Block body = whileLoop.getBody();
        body.accept(this, nestedScopeSymbolTable);

        Condition condition = whileLoop.getCondition();
        condition.accept(this, nestedScopeSymbolTable);

    }

    @Override
    public void visit(Identifier identifier, SymbolTable symbolTable) {
        identifier.setSymbolTable(symbolTable);
    }

}
