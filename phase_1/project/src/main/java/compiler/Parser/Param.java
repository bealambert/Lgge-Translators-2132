package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class Param extends ASTNode implements Visitable {

    Type type;
    Identifier identifier;

    public Param(Type type, Identifier identifier) {
        super();
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "<" + type +
                ", " + identifier + "";
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
