package compiler.Semantic;

import compiler.*;
import compiler.Lexer.Identifier;
import compiler.Parser.*;

public class TypeCheckingVisitor implements ExpressionTypeVisitor {


    @Override
    public Type visit(Variable variable) throws SemanticAnalysisException {
        SymbolTable symbolTable = variable.getSymbolTable();
        Type type = treatSemanticCases.getFirstDeclarationInsideSymbolTable(variable.getIdentifier(), symbolTable).accept(this);
        return type;
    }

    @Override
    public Type visit(Values values) throws SemanticAnalysisException {
        return values.getType();
    }

    @Override
    public Type visit(FunctionCall functionCall) throws SemanticAnalysisException {
        SymbolTable symbolTable = functionCall.getSymbolTable();
        Type type = treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionCall.getIdentifier(), symbolTable).accept(this);
        return type;
    }

    @Override
    public Type visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {
        SymbolTable symbolTable = accessToIndexArray.getSymbolTable();
        Type type = treatSemanticCases.getFirstDeclarationInsideSymbolTable(accessToIndexArray.getIdentifier(), symbolTable).accept(this);
        ArrayOfExpression arrayOfExpression = accessToIndexArray.getArrayOfExpression();
        BinaryTree myTree = arrayOfExpression.getMyTree();
        Type indexType = myTree.accept(this);
        if (type.getAttribute().equals(ClassName.ArrayType.getName()) && indexType.getAttribute().equals(Token.IntIdentifier.getName())) {
            Identifier identifier = new Identifier(indexType.getAttribute());
            return new Type(identifier);
        }
        throw new SemanticAnalysisException("");
    }

    @Override
    public Type visit(MethodCall methodCall) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(BinaryComparator binaryComparator) throws SemanticAnalysisException {
        Expression left = binaryComparator.getLeft();
        Expression right = binaryComparator.getRight();

        Type leftType = left.accept(this);
        Type rightType = right.accept(this);
        if (leftType.getAttribute().equals(Token.Boolean.getName()) && rightType.getAttribute().equals(Token.Boolean.getName())) {
            return leftType;
        }
        treatSemanticCases.isEqual(leftType, rightType);
        return leftType;
    }


    @Override
    public Type visit(BinaryModulo binaryModulo) throws SemanticAnalysisException {
        Expression left = binaryModulo.getLeft();
        Expression right = binaryModulo.getRight();

        Type leftType = left.accept(this);
        Type rightType = right.accept(this);

        if (leftType.getAttribute().equals(Token.IntIdentifier.getName()) && rightType.getAttribute().equals(Token.IntIdentifier.getName())) {
            return leftType;
        }

        treatSemanticCases.isEqual(leftType, rightType);
        return leftType;
    }


    @Override
    public Type visit(BinaryOperator binaryOperator) throws SemanticAnalysisException {
        Expression left = binaryOperator.getLeft();
        Expression right = binaryOperator.getRight();

        Type leftType = left.accept(this);
        Type rightType = right.accept(this);

        treatSemanticCases.isEqual(leftType, rightType);
        return leftType;
    }

    @Override
    public Type visit(MyNode myNode) throws SemanticAnalysisException {
        MyNode left = myNode.getLeft();
        MyNode right = myNode.getRight();
        if (left == null && right == null) {
            Expression expression = (Expression) myNode.getValue();
            return expression.accept(this);
        }
        assert left != null;
        assert right != null;
        Type leftType = left.accept(this);
        Type rightType = right.accept(this);

        treatSemanticCases.isEqual(leftType, rightType);
        throw new SemanticAnalysisException("");

    }

    @Override
    public Type visit(CreateProcedure createProcedure) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateFunctionCallParameterVariable createFunctionCallParameterVariable) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateVariables createVariables) throws SemanticAnalysisException {
        return null;
    }

    @Override
    public Type visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {
        return null;
    }
}
