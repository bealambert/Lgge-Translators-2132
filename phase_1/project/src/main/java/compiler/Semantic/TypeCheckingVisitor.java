package compiler.Semantic;

import compiler.*;
import compiler.Lexer.Identifier;
import compiler.Parser.*;


import java.util.ArrayList;

public class TypeCheckingVisitor implements ExpressionTypeVisitor {


    @Override
    public Type visit(Variable variable) throws SemanticAnalysisException {
        SymbolTable symbolTable = variable.getSymbolTable();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(variable.getIdentifier(), symbolTable);
        return astNode.accept(this);
    }

    @Override
    public Type visit(Values values) throws SemanticAnalysisException {
        return values.getType();
    }

    @Override
    public Type visit(FunctionCall functionCall) throws SemanticAnalysisException {
        SymbolTable symbolTable = functionCall.getSymbolTable();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionCall.getIdentifier(), symbolTable);
        Type returnType = astNode.accept(typeCheckingVisitor);
        //functionCall.accept(makeSemanticAnalysisVisitor);

        return returnType;
    }

    @Override
    public Type visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {
        SymbolTable symbolTable = accessToIndexArray.getSymbolTable();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(accessToIndexArray.getIdentifier(), symbolTable);
        Type type = astNode.accept(typeCheckingVisitor);
        ArrayOfExpression arrayOfExpression = accessToIndexArray.getArrayOfExpression();
        BinaryTree myTree = arrayOfExpression.getMyTree();
        Type indexType = myTree.accept(typeCheckingVisitor);
        if (type instanceof ArrayType && indexType.getAttribute().equals(Token.NaturalNumber.getName())) {
            return type;
        }
        throw new SemanticAnalysisException("");
    }

    @Override
    public Type visit(MethodCall methodCall) throws SemanticAnalysisException {
        // parent class
        return null;
    }

    @Override
    public Type visit(MyNode myNode) throws SemanticAnalysisException {

        myNode.getValue().accept(makeSemanticAnalysisVisitor);

        MyNode left = myNode.getLeft();
        MyNode right = myNode.getRight();
        // left == null && right == null
        if (myNode.isLeaf()) {
            Expression expression = (Expression) myNode.getValue();
            return expression.accept(this);
        }
        // if myNode is not a leaf then it must have 2 children !
        assert left != null;
        assert right != null;
        Type leftType = left.accept(this);
        Type rightType = right.accept(this);

        treatSemanticCases.isEqual(leftType, rightType);
        if (myNode.getValue() instanceof OperatorComparator) {
            return new Type(new Identifier("bool"));
        }
        return leftType;
    }

    @Override
    public Type visit(CreateProcedure createProcedure) throws SemanticAnalysisException {
        return createProcedure.getReturnType();
    }

    @Override
    public Type visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException {
        return createArrayVariable.getType();
    }

    @Override
    public Type visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException {
        //createExpressionVariable.getVariableIdentifier().accept(makeSemanticAnalysisVisitor);

        Type expectedType = createExpressionVariable.getType();
        ArrayOfExpression arrayOfExpression = createExpressionVariable.getArrayOfExpression();
        Type observed = arrayOfExpression.accept(this);
        treatSemanticCases.isEqual(expectedType, observed);

        return expectedType;
    }

    @Override
    public Type visit(CreateFunctionCallParameterVariable createFunctionCallParameterVariable) throws SemanticAnalysisException {
        return createFunctionCallParameterVariable.getType();
    }

    @Override
    public Type visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException {
        return createRecordVariables.getType();
    }

    @Override
    public Type visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {
        return createReferencedVariable.getType();
    }

    @Override
    public Type visit(CreateVariables createVariables) throws SemanticAnalysisException {
        return createVariables.getType();
    }

    @Override
    public Type visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {
        return createVoidVariable.getType();
    }

    @Override
    public Type visit(ASTNode astNode) throws SemanticAnalysisException {
        // parent root
        return null;
    }

    @Override
    public Type visit(Type type) throws SemanticAnalysisException {
        return type;
    }

    @Override
    public Type visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException {
        treatSemanticCases.TypeExists(arrayInitializer.getType());
        Type indexType = arrayInitializer.getArraySize().accept(this);

        if (!indexType.getAttribute().equals(Token.NaturalNumber.getName())) {
            throw new SemanticAnalysisException("expected int as index, got : " + indexType.getAttribute());
        }
        return arrayInitializer.getType();

    }

    @Override
    public Type visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException {
        Identifier objectIdentifier = methodCallFromIdentifier.getObjectIdentifier();
        // MethodCall are applied only by records at least for now (no built-in functions yet)
        InitializeRecords initializeRecords = treatSemanticCases.getAccessToRecordDeclaration(objectIdentifier, objectIdentifier.getSymbolTable());
        ArrayList<RecordParameter> arrayList = initializeRecords.getRecordVariable();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getIdentifier().getAttribute().equals(methodCallFromIdentifier.getMethodIdentifier().getAttribute())) {
                return arrayList.get(i).getType();
            }
        }
        throw new SemanticAnalysisException(
                "could not find attribute \"" + methodCallFromIdentifier.getMethodIdentifier().getAttribute() + "\"" +
                        "for the record " + initializeRecords.getRecords().getIdentifier().getAttribute());
    }

    @Override
    public Type visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException {

        AccessToIndexArray accessToIndexArray = methodCallFromIndexArray.getAccessToIndexArray();
        Type type = accessToIndexArray.accept(typeCheckingVisitor);
        // MethodCall are applied only by records at least for now (no built-in functions yet)

        InitializeRecords initializeRecords = treatSemanticCases.getAccessToRecordDeclaration(type, type.getSymbolTable());
        ArrayList<RecordParameter> arrayList = initializeRecords.getRecordVariable();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getIdentifier().getAttribute().equals(methodCallFromIndexArray.getMethodIdentifier().getAttribute())) {
                return arrayList.get(i).getType();
            }
        }
        throw new SemanticAnalysisException(
                "could not find attribute \"" + methodCallFromIndexArray.getMethodIdentifier().getAttribute() + "\"" +
                        "for the record " + initializeRecords.getRecords().getIdentifier().getAttribute());
    }

    @Override
    public Type visit(Reassignment reassignment) throws SemanticAnalysisException {
        SymbolTable symbolTable = reassignment.getSymbolTable();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(reassignment.getIdentifier(), symbolTable);
        Type expected = astNode.accept(typeCheckingVisitor);
        Type observed = reassignment.getArrayOfExpression().accept(typeCheckingVisitor);

        treatSemanticCases.isEqual(expected, observed);
        return observed;
    }

    @Override
    public Type visit(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException {
        return treatSemanticCases.treatExpression(arrayOfExpression);
    }

    @Override
    public Type visit(Expression expression) throws SemanticAnalysisException {
        return treatSemanticCases.treatExpression(expression);
    }

    @Override
    public Type visit(RecordParameter recordParameter) throws SemanticAnalysisException {
        return recordParameter.getType();
    }

    @Override
    public Type visit(InitializeRecords initializeRecords) throws SemanticAnalysisException {
        return new Type(initializeRecords.getRecords().getIdentifier());
    }

    @Override
    public Type visit(ExpressionParameter expressionParameter) throws SemanticAnalysisException {
        return expressionParameter.getExpression().accept(this);
    }

    @Override
    public Type visit(ArrayInitializerParameter arrayInitializerParameter) throws SemanticAnalysisException {
        return arrayInitializerParameter.getArrayInitializer().accept(this);
    }

    @Override
    public Type visit(FunctionCallParameter functionCallParameter) {
        // parent class
        return null;
    }


    @Override
    public Type visit(Param param) throws SemanticAnalysisException {
        return param.getType();
    }

    public Type applyOperation(MyNode myNode, Operator operator, Type leftType) {
        return leftType;
    }

    public Type applyOperation(MyNode myNode, OperatorComparator operatorComparator) {
        return new Type(new Identifier("bool"));
    }
}
