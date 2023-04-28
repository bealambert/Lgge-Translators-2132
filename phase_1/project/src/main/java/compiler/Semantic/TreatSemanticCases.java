package compiler.Semantic;

import compiler.ASTNode;
import compiler.BinaryTree;
import compiler.Lexer.Identifier;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;

import java.util.HashMap;

import static compiler.Semantic.ExpressionTypeVisitor.treatSemanticCases;
import static compiler.Semantic.ExpressionTypeVisitor.typeCheckingVisitor;

public class TreatSemanticCases {

    HashMap<String, String> mapping = new HashMap<>();
    TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();


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

    public void TypeExists(Type type) throws SemanticAnalysisException {
        if (mapping.get(type.getAttribute()) != null) {
            return;
        }
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(type, type.getSymbolTable());
        if (astNode instanceof InitializeRecords) {
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
                    "Types do no match :  expected : " + expected.getName() + "\t observed : " + observed.getName());
        }
    }

    public InitializeRecords getAccessToRecordDeclaration(Identifier identifier, SymbolTable symbolTable) throws SemanticAnalysisException {
        ASTNode astNode = symbolTable.symbolTable.get(identifier.getAttribute());

        if (astNode instanceof Param) {
            Param param = (Param) astNode;
            Type type = param.getType();
            return (InitializeRecords) getFirstDeclarationInsideSymbolTable(type, param.getSymbolTable());
        }

        if (astNode instanceof InitializeRecords) {
            return (InitializeRecords) astNode;
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
