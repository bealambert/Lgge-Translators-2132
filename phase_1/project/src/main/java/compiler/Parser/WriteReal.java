package compiler.Parser;

import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class WriteReal extends ProcedureIO {
    public WriteReal(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }
}
