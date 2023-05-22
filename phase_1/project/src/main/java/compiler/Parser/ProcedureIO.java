package compiler.Parser;

import compiler.Lexer.Identifier;
import compiler.Semantic.Visitable;

import java.util.ArrayList;

public class ProcedureIO extends FunctionCall implements Visitable {

    public ProcedureIO(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }
}
