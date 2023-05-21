package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

import java.util.ArrayList;

public class CreateProcedure extends ASTNode implements Visitable {

    Identifier procedureName;
    ArrayList<Param> params;
    Type returnType;
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

    public Type getReturnType() {
        return returnType;
    }

    public Block getBody() {
        return body;
    }

    public Identifier getProcedureName() {
        return procedureName;
    }

    public ArrayList<Param> getParams() {
        return params;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

}
