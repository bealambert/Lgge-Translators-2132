package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class Method extends ASTNode {

    Identifier identifier;
    Type returnType;
    ArrayList<Param> parameters;
    Block body;

    public Method(Identifier identifier, Type returnType, ArrayList<Param> parameters, Block body) {
        super();
        this.identifier = identifier;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }


}
