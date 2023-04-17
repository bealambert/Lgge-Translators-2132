package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class FunctionCall extends Expression implements Visitable {

    Identifier identifier;
    ArrayList<ArrayOfExpression> params;

    public FunctionCall(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        this.params = params;
        this.identifier = identifier;
    }


    public Identifier getIdentifier() {
        return identifier;
    }


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }
}
