package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

import java.util.ArrayList;

public class Method extends ASTNode implements Visitable {

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

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }


}
