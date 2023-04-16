package compiler.Semantic;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Parser.Expression;
import compiler.Parser.Type;
import compiler.SemanticAnalysisException;

public class TreatSemanticCases {

    public TreatSemanticCases() {

    }

    public Type treatExpression(Expression expression) {
        return null;
    }

    public void isEqual(Type expected, Type observed) throws SemanticAnalysisException {
        // might need to add the line + if inside function
        // expected <int>      observed <boolean>    in function : <function>  at line 302.
        if (!expected.getAttribute().equals(observed.getAttribute())) {
            throw new SemanticAnalysisException(
                    "Types do no match :  expected : " + expected.getName() + "\t observed : " + observed.getName());
        }
    }

    public ASTNode getFirstDeclarationInsideSymbolTable(Identifier identifier, SymbolTable symbolTable) throws SemanticAnalysisException {
        ASTNode astNode = symbolTable.symbolTable.get(identifier);

        if (astNode != null) {
            return astNode;
        }

        if (symbolTable.previous != null) {
            return getFirstDeclarationInsideSymbolTable(identifier, symbolTable.previous);
        }

        throw new SemanticAnalysisException("Could not find identifier : " + identifier + " in this scope");

    }
}
