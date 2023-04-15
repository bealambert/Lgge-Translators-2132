package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;

import java.util.ArrayList;

public class CreateProcedure extends ASTNode implements Visitable {

    Identifier procedureName;
    ArrayList<Param> params;
    Type returnType;
    ArrayInitializer returnTypeArray;
    Block body;

    public CreateProcedure(Identifier procedureName, ArrayList<Param> params, Type returnType, Block body) {
        this.procedureName = procedureName;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    public String toString() {
        return "CreateProcedure{" +
                "procedureName=" + procedureName +
                ", params=" + params +
                ", returnType=" + returnType +
                ", body=" + body +
                '}';
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

}
