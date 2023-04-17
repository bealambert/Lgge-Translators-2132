package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Values extends Expression {


    Symbol symbol;
    Type type;

    public Values(Symbol symbol) {
        super();
        this.symbol = symbol;
        this.type = new Type(new Identifier(symbol.getName()));

    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public Type getType() {
        return this.type;
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
