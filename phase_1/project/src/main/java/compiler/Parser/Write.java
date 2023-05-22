package compiler.Parser;

import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class Write extends ProcedureIO {

    public Write(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }
}
