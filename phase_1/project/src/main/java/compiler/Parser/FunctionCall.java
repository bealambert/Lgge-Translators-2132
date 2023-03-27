package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;

import java.util.ArrayList;

public class FunctionCall extends ASTNode {

    Symbol identifier;
    ArrayList<Object> params;


    public FunctionCall(Symbol identifier, ArrayList<Object> params) {
        super();
        this.identifier = identifier;
        this.params = params;
    }
}
