package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class Type extends ASTNode implements Visitable {

    Identifier attribute;

    public Type(Identifier attribute) {
        super();
        this.attribute = attribute;
    }

    public Identifier getAttribute() {
        return attribute;
    }

    public String getName() {
        return "Type";
    }


    @Override
    public String toString() {
        return "Type{" +
                "attribute=" + attribute +
                '}';
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
