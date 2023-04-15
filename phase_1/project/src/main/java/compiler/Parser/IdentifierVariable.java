package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class IdentifierVariable extends ASTNode implements Visitable {

    Identifier identifier;

    public IdentifierVariable(Identifier identifier) {
        if (identifier.getName().equals(Identifier.class.getName())) {
            this.identifier = identifier;
        } else {
            this.identifier = (AccessToIndexArray) identifier;
        }
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }


}
