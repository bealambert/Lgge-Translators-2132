package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

public class RecordParameter extends ASTNode {

    Identifier identifier;
    Type type;

    public RecordParameter(Identifier identifier, Type type) {
        super();
        this.identifier = identifier;
        this.type = type;
    }
}
