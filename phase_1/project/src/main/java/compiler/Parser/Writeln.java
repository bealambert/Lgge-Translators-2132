package compiler.Parser;

import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class Writeln extends ProcedureIO {

    public Writeln(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }
}
