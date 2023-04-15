package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

public class CreateArrayVariable extends CreateVariables {

    ArrayInitializer arrayInitializer;

    public CreateArrayVariable(Keyword stateKeyword, Identifier variableIdentifier, ArrayType type, ArrayInitializer arrayInitializer) {
        super(stateKeyword, variableIdentifier, type);
        this.arrayInitializer = arrayInitializer;
    }

    @Override
    public String toString() {
        return "CreateArrayVariable{" +
                "arrayInitializer=" + arrayInitializer +
                '}';
    }

    @Override
    public void accept(AssignSymbolTableVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(MakeSemanticAnalysisVisitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
