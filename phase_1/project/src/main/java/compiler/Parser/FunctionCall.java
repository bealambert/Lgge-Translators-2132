package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Symbol;

import java.util.ArrayList;
import java.util.Objects;

public class FunctionCall extends ASTNode {
    Symbol identifier;
    ArrayList<Object> parameters;

    public FunctionCall(Symbol identifier, ArrayList<Object> parameters) {
        super();
        this.identifier = identifier;
        this.parameters = parameters;
    }
    @Override
    public String toString() {
        return "<FunctionCall: " + this.identifier.getAttribute() + "(" + this.parameters + ") >";
    }
}








