package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class BuiltIn extends FunctionCall implements Visitable {

    public BuiltIn(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }

}
