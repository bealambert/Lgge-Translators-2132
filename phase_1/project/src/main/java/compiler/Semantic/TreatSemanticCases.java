package compiler.Semantic;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;
import compiler.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static compiler.Semantic.ExpressionTypeVisitor.makeSemanticAnalysisVisitor;
import static compiler.Semantic.ExpressionTypeVisitor.treatSemanticCases;

public class TreatSemanticCases {

    HashMap<String, String> mapping = new HashMap<>();
    TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
    String[] allowedForIntegers = new String[]{"+", "-", "*", "/", "==", "<>", "<", "<=", ">", ">=", "%"};
    String[] allowedForFloats = new String[]{"+", "-", "*", "/", "==", "<>", "<", "<=", ">", ">="};
    String[] allowedForBoolean = new String[]{"and", "or", "==", "<>"};
    String[] allowedForString = new String[]{"+", "==", "<>"};
    public int CONST = 0;
    public int RECORD = 1;
    public int OTHER = 2;

    public TreatSemanticCases() {
        mapping.put("NaturalNumber", "int");
        mapping.put("RealNumber", "real");
        mapping.put("Boolean", "bool");
        mapping.put("Strings", "string");

        mapping.put("int", "int");
        mapping.put("real", "real");
        mapping.put("bool", "bool");
        mapping.put("string", "string");
    }

    public int ensuresStateRespected(CreateVariables createVariables, int state) throws SemanticAnalysisException {
        if (createVariables.getStateKeyword().getAttribute().equals(Token.ConstKeyword.getName()) && state != CONST) {
            throw new SemanticAnalysisException("Constant values should be initialized at the start of the program");
        }
        if (!(createVariables.getStateKeyword().getAttribute().equals(Token.ConstKeyword.getName()))) {
            return OTHER;
        }
        return state;
    }

    public void ensuresStateRespected(InitializeRecords initializeRecords, int state) throws SemanticAnalysisException {
        if (state > RECORD) {
            throw new SemanticAnalysisException("Record initialization should be initialized after the constant and " +
                    "before any functions");
        }
    }

    public Type treatExpression(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException {
        MyNode root = arrayOfExpression.getMyTree().getRoot();
        return root.accept(typeCheckingVisitor);
    }

    public Type treatExpression(Expression expression) {
        return null;
    }

    public Type treatFunctionCallParameters(FunctionCallParameter functionCallParameter) {
        return null;
    }

    public void treatFunctionCallParameterHelper(Type expected, FunctionCallParameter functionCallParameter) throws SemanticAnalysisException {
        if (functionCallParameter instanceof ExpressionParameter) {
            ExpressionParameter expressionParameter = (ExpressionParameter) functionCallParameter;
            Type observed = expressionParameter.getExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);
            isEqual(expected, observed);
        } else if (functionCallParameter instanceof RecordCall) {
            RecordCall cast = (RecordCall) functionCallParameter;
            cast.accept(makeSemanticAnalysisVisitor);
        } else {
            ArrayInitializerParameter arrayInitializerParameter = (ArrayInitializerParameter) functionCallParameter;
            arrayInitializerParameter.getArrayInitializer().accept(makeSemanticAnalysisVisitor);
            Type observed = arrayInitializerParameter.getArrayInitializer().getType();
            treatSemanticCases.isEqual(expected, observed);
        }
    }

    public void helperRecordCall(InitializeRecords value, ArrayList<FunctionCallParameter> functionCallParameters) throws SemanticAnalysisException {
        ArrayList<RecordParameter> recordParameters = value.getRecordVariable();

        if (recordParameters.size() != functionCallParameters.size()) {
            throw new SemanticAnalysisException("expected " + recordParameters.size() + " parameters, but got " + functionCallParameters.size() + " parameters");
        }
        for (int i = 0; i < recordParameters.size(); i++) {
            Type expected = recordParameters.get(i).getType(); // int
            FunctionCallParameter functionCallParameter = functionCallParameters.get(i);
            treatFunctionCallParameterHelper(expected, functionCallParameter);
        }

    }

    public void helperRecordCall(CreateProcedure createProcedure, ArrayList<FunctionCallParameter> functionCallParameters) throws SemanticAnalysisException {
        ArrayList<Param> params = createProcedure.getParams();

        if (params.size() != functionCallParameters.size()) {
            throw new SemanticAnalysisException("expected " + params.size() + " parameters, but got " + functionCallParameters.size() + " parameters");
        }
        for (int i = 0; i < params.size(); i++) {
            Type expected = params.get(i).getType(); // int
            FunctionCallParameter functionCallParameter = functionCallParameters.get(i);
            treatFunctionCallParameterHelper(expected, functionCallParameter);
        }

    }

    public void TypeExists(Type type) throws SemanticAnalysisException {
        if (mapping.get(type.getAttribute()) != null) {
            return;
        }
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(type, type.getSymbolTable());
        if (astNode != null) {
            return;
        }
        throw new SemanticAnalysisException("Type : " + type.getAttribute() + " does not exist");
    }

    public void isEqual(Type expected, Type observed) throws SemanticAnalysisException {
        // might need to add the line + if inside function
        // expected <int>      observed <boolean>    in function : <function>  at line 302.
        String expectations = mapping.getOrDefault(expected.getAttribute(), expected.getAttribute());
        String reality = mapping.getOrDefault(observed.getAttribute(), observed.getAttribute());

        if (!expectations.equals(reality)) {
            throw new SemanticAnalysisException(
                    "Types do no match :  expected : " + expected.getAttribute() + "\t observed : " + observed.getAttribute());
        }
    }


    public Type isAllowed(Type expected, Type observed, Operator operator) throws SemanticAnalysisException {
        String expectations = mapping.getOrDefault(expected.getAttribute(), expected.getAttribute());
        String reality = mapping.getOrDefault(observed.getAttribute(), observed.getAttribute());

        String[] allowed_types = operator.getAllowed_types();
        List<String> allowedOperationArray = Arrays.asList(allowed_types);
        boolean allowedOperation = allowedOperationArray.contains(expectations) && allowedOperationArray.contains(reality);
        if (!allowedOperation) {
            throw new SemanticAnalysisException("operation not allowed : \"" + operator.getOperator() + "\" between " + expectations + " and " + reality);
        }
        if (expectations.equals(Token.IntIdentifier.getName()) && reality.equals(Token.RealIdentifier.getName())) {
            return observed;
        } else if (reality.equals(Token.IntIdentifier.getName()) && expectations.equals(Token.RealIdentifier.getName())) {
            return expected;
        }
        isEqual(expected, observed);
        return expected;

    }

    public InitializeRecords getAccessToRecordDeclaration(Identifier identifier, SymbolTable symbolTable) throws SemanticAnalysisException {
        ASTNode astNode = symbolTable.symbolTable.get(identifier.getAttribute());

        if (astNode instanceof Param) {
            Param param = (Param) astNode;
            Type type = param.getType();
            return (InitializeRecords) getFirstDeclarationInsideSymbolTable(type, param.getSymbolTable());
        }
        // ArrayInitializer -> call the identifier corresponding to the record
        if (astNode instanceof ArrayInitializer) {
            ArrayInitializer arrayInitializer = (ArrayInitializer) astNode;
            return (InitializeRecords) getFirstDeclarationInsideSymbolTable(arrayInitializer.getType(), arrayInitializer.getSymbolTable());
        }

        if (astNode instanceof InitializeRecords) {
            return (InitializeRecords) astNode;
        }
        if (astNode instanceof CreateRecordVariables) {
            CreateRecordVariables createRecordVariables = (CreateRecordVariables) astNode;
            return (InitializeRecords) getFirstDeclarationInsideSymbolTable(createRecordVariables.getType(), symbolTable);
        }
        if (astNode instanceof CreateArrayVariable) {
            CreateArrayVariable createArrayVariable = (CreateArrayVariable) astNode;
            return (InitializeRecords) getFirstDeclarationInsideSymbolTable(createArrayVariable.getType(), symbolTable);
        }
        if (astNode instanceof CreateExpressionVariable) {
            CreateExpressionVariable createExpressionVariable = (CreateExpressionVariable) astNode;
            Type type = createExpressionVariable.getType();
            return (InitializeRecords) getFirstDeclarationInsideSymbolTable(type, symbolTable);
        }

        if (symbolTable.previous != null) {
            return getAccessToRecordDeclaration(identifier, symbolTable.previous);
        }

        throw new SemanticAnalysisException("Could not find identifier : " + identifier + " in this scope");
    }

    public ASTNode getFirstDeclarationInsideSymbolTable(Identifier identifier, SymbolTable symbolTable) throws SemanticAnalysisException {
        ASTNode astNode = symbolTable.symbolTable.get(identifier.getAttribute());

        if (astNode != null) {
            return astNode;
        }

        if (symbolTable.previous != null) {
            return getFirstDeclarationInsideSymbolTable(identifier, symbolTable.previous);
        }

        throw new SemanticAnalysisException("Could not find identifier : " + identifier + " in this scope");

    }
}
