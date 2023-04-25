package compiler.Parser;

import compiler.Lexer.Identifier;

public class ForLoopAssignVariable extends ForLoop {

    Identifier identifier;
    Expression start;

    public ForLoopAssignVariable(Identifier identifier, Expression start, Expression end, Expression incrementBy, Block body) {
        super(end, incrementBy, body);

    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getStart() {
        return start;
    }
}
