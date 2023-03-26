package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class FunctionCall extends ASTNode {

    Identifier identifier;
    ArrayList<FunctionCallParameters> params;


    public FunctionCall(Identifier identifier, ArrayList<FunctionCallParameters> params) {
        super();
        this.identifier = identifier;
        this.params = params;
    }
}
