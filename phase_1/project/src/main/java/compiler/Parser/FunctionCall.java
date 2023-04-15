package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;
import compiler.Semantic.AssignSymbolTableVisitor;
import compiler.Semantic.MakeSemanticAnalysisVisitor;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;

public class FunctionCall extends Expression {

    Symbol identifier;


    public FunctionCall(Symbol identifier, ArrayList<Object> params) {
        super(params);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                identifier +
                '}' + ", (" + this.getExpression() + ')';
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
