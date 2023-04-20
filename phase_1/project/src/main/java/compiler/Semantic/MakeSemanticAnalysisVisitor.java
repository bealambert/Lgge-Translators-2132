package compiler.Semantic;

import compiler.*;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;
import compiler.Parser.*;

import java.util.ArrayList;
import java.util.Stack;

public class MakeSemanticAnalysisVisitor implements SemanticVisitor {


    @Override
    public void visit(ExpressionParameter expressionParameter, SymbolTable symbolTable) {


    }

    @Override
    public void visit(ArrayInitializerParameter arrayInitializerParameter, SymbolTable symbolTable) {

    }

    @Override
    public void visit(FunctionCallParameter functionCallParameter) throws SemanticAnalysisException {

    }

    @Override
    public void visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {
        //String input = "var c int[] = int[](5);";
        // var p int = c[0];

        CreateArrayVariable createArrayVariable =
                (CreateArrayVariable) treatSemanticCases.getFirstDeclarationInsideSymbolTable
                        (accessToIndexArray.getIdentifier(), accessToIndexArray.getSymbolTable());
        if (!createArrayVariable.getType().getName().equals(ClassName.ArrayType.getName())) {
            throw new SemanticAnalysisException("");
        }
        Expression expression = accessToIndexArray.getExpression();
        Type type = treatSemanticCases.treatExpression(expression);
        if (!type.getAttribute().equals(Token.IntIdentifier.getName())) {
            throw new SemanticAnalysisException("");
        }

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
        Type observedType = treatSemanticCases.treatExpression(condition.getArrayOfExpression());
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
        Type observedType = treatSemanticCases.treatExpression(createExpressionVariable.getArrayOfExpression());
        treatSemanticCases.isEqual(expectedType, observedType);
    }

    @Override
    public void visit(CreateProcedure createProcedure) throws SemanticAnalysisException {
        // need to check the return value
        functionNameStack.push(createProcedure.getProcedureName());
        Block body = createProcedure.getBody();
        body.accept(this);
        functionNameStack.pop();


    }

    @Override
    public void visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException {

        // var d Person = Person(\"me\", Point(3,7), int[](a*2));  // new record";

        SymbolTable symbolTable = createRecordVariables.getSymbolTable();
        Identifier identifier = createRecordVariables.getType();
        // Person - Person
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, symbolTable);
        if (!createRecordVariables.getRecordCall().getRecords().getIdentifier().getAttribute().equals(identifier.getAttribute())) {
            throw new SemanticAnalysisException("");
        }

        createRecordVariables.getRecordCall().accept(this);

    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {
        Identifier referencedIdentifier = createReferencedVariable.getReferencedIdentifier();
        SymbolTable symbolTable = createReferencedVariable.getSymbolTable();
        CreateVariables createVariables = (CreateVariables) treatSemanticCases.getFirstDeclarationInsideSymbolTable(referencedIdentifier, symbolTable);
        Type expected = createVariables.getType();
        Type observed = createReferencedVariable.getType();
        treatSemanticCases.isEqual(expected, observed);
    }

    @Override
    public void visit(CreateVariables createVariables) throws SemanticAnalysisException {
        // only used as a disjoint parent class -> either A or B or C but the parent is not useful in this case
    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(Expression expression) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(ForLoop forLoop) throws SemanticAnalysisException {
        Block body = forLoop.getBody();
        body.accept(this);
    }

    @Override
    public void visit(FunctionCall functionCall) throws SemanticAnalysisException {

        /*SymbolTable symbolTable = functionCall.getSymbolTable();
        CreateProcedure createProcedure = treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionCall.get)
        ArrayList<Expression> expressionArrayList = functionCall.getExpression();
        for (int i = 0; i < expressionArrayList.size(); i++) {
            Type observed = treatSemanticCases.treatExpression(expressionArrayList.get(i));
            Type expected =
        }*/
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
        Condition condition = ifElse.getCondition();
        Block ifBlock = ifElse.getIfBlock();
        Block elseBlock = ifElse.getElseBlock();

        condition.accept(this);
        ifBlock.accept(this);
        elseBlock.accept(this);
    }

    @Override
    public void visit(InitializeRecords initializeRecords) throws SemanticAnalysisException {
        Records records = initializeRecords.getRecords();
        SymbolTable symbolTable = initializeRecords.getSymbolTable();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(records.getIdentifier(), symbolTable);

        ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
        for (int i = 0; i < recordParameters.size(); i++) {
            RecordParameter recordParam = (RecordParameter) treatSemanticCases.getFirstDeclarationInsideSymbolTable
                    (recordParameters.get(i).getIdentifier(), symbolTable);
            recordParam.accept(this);
        }

    }

    @Override
    public void visit(MethodCall methodCall) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException {
        // <Object>.<method>
        SymbolTable symbolTable = methodCallFromIdentifier.getSymbolTable();
        Identifier objectIdentifier = methodCallFromIdentifier.getObjectIdentifier();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(objectIdentifier, symbolTable);
        // Do I have to check if the function can be applied on that object?
        // no class or something -> maybe built-in functions that are not yet implemented

        Identifier identifier = methodCallFromIdentifier.getMethodIdentifier();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, symbolTable);
        createProcedure.accept(this);
    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException {
        SymbolTable symbolTable = methodCallFromIndexArray.getSymbolTable();

        AccessToIndexArray accessToIndexArray = methodCallFromIndexArray.getAccessToIndexArray();
        accessToIndexArray.accept(this);

        Identifier identifier = methodCallFromIndexArray.getMethodIdentifier();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, symbolTable);
        createProcedure.accept(this);
    }

    @Override
    public void visit(Param param) throws SemanticAnalysisException {
        SymbolTable symbolTable = param.getSymbolTable();

        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(param.getIdentifier(), symbolTable);
        astNode.accept(this);
    }

    @Override
    public void visit(Reassignment reassignment) throws SemanticAnalysisException {
        // var x int = 2;
        // var y int = 4;
        // x = y;
        SymbolTable symbolTable = reassignment.getSymbolTable();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(reassignment.getIdentifier(), symbolTable);
        // implement visitor pattern for Expression to check types
    }

    @Override
    public void visit(RecordCall recordCall) throws SemanticAnalysisException {

        SymbolTable symbolTable = recordCall.getSymbolTable();
        Identifier identifier = recordCall.getRecords().getIdentifier();

        InitializeRecords value = (InitializeRecords) treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, symbolTable);
        ArrayList<RecordParameter> recordParameters = value.getRecordVariable();
        ArrayList<FunctionCallParameter> functionCallParameters = recordCall.getFunctionCallParameters();
        if (recordParameters.size() != functionCallParameters.size()) {
            throw new SemanticAnalysisException("expected " + recordParameters.size() + " parameters, but got " + functionCallParameters.size() + " parameters");
        }
        for (int i = 0; i < recordParameters.size(); i++) {
            Type expected = recordParameters.get(i).getType(); // int
            FunctionCallParameter functionCallParameter = functionCallParameters.get(i);
            if (functionCallParameter instanceof ExpressionParameter) {
                ExpressionParameter expressionParameter = (ExpressionParameter) functionCallParameter;
                Type observed = expressionParameter.getExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);
                treatSemanticCases.isEqual(expected, observed);
            } else if (functionCallParameter instanceof RecordCall) {
                RecordCall cast = (RecordCall) functionCallParameter;
                cast.accept(this);
            } else {
                ArrayInitializerParameter arrayInitializerParameter = (ArrayInitializerParameter) functionCallParameter;
                arrayInitializerParameter.getArrayInitializer().accept(this);
                Type observed = arrayInitializerParameter.getArrayInitializer().getType();
                treatSemanticCases.isEqual(expected, observed);
            }
        }
    }

    @Override
    public void visit(RecordParameter recordParameter) throws SemanticAnalysisException {
        // check the type exists
        SymbolTable symbolTable = recordParameter.getSymbolTable();
        RecordParameter storedRecordParameter = (RecordParameter)
                treatSemanticCases.getFirstDeclarationInsideSymbolTable(recordParameter.getIdentifier(), symbolTable);

        Type expected = storedRecordParameter.getType();
        Type observed = recordParameter.getType();
        treatSemanticCases.isEqual(expected, observed);
        // check for i-th param is of type <> should be inside the CreateRecordVariables

    }

    @Override
    public void visit(Records records) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(ReturnStatement returnStatement) throws SemanticAnalysisException {
        SymbolTable symbolTable = returnStatement.getSymbolTable();
        ASTNode astNode = treatSemanticCases.treatExpression(returnStatement.getArrayOfExpression());
        Type observedType = astNode.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Identifier functionName = functionNameStack.peek();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionName, symbolTable);
        Type expectedType = createProcedure.getReturnType();
        treatSemanticCases.isEqual(expectedType, observedType);
    }

    @Override
    public void visit(Type type) throws SemanticAnalysisException {
        SymbolTable symbolTable = type.getSymbolTable();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(type, symbolTable);
    }

    @Override
    public void visit(Values values) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(WhileLoop whileLoop) throws SemanticAnalysisException {
        Condition condition = whileLoop.getCondition();
        Block body = whileLoop.getBody();

        condition.accept(this);
        body.accept(this);
    }

    @Override
    public void visit(Identifier identifier) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(BinaryTree binaryTree) throws SemanticAnalysisException {
        MyNode myNode = binaryTree.getRoot();

        MyNode left = myNode.getLeft();
        MyNode right = myNode.getRight();

    }

    @Override
    public void visit(MyNode myNode) throws SemanticAnalysisException {
        Expression expression = myNode.getValue();
        expression.accept(this);
        MyNode left = myNode.getLeft();
        MyNode right = myNode.getRight();
        if (left != null) {
            left.accept(this);
        }
        if (right != null) {
            right.accept(this);
        }
    }

    @Override
    public void visit(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException {

    }
}



