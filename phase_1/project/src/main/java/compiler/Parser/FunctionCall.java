package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class FunctionCall extends Expression implements Visitable {

    Symbol identifier;


    public FunctionCall(Symbol identifier, ArrayList<Expression> params) {
        super(params);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                identifier +
                '}' + ", (" + this.getExpression() + ')';
    }

    public Symbol getIdentifier() {
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
}
