package compiler.Parser;

import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class WriteInt extends ProcedureIO {

    public WriteInt(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }
}
