package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

public class RecordParameter extends ASTNode implements Visitable {

    Identifier identifier;
    Type type;

    public RecordParameter(Identifier identifier, Type type) {
        super();
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }
}
