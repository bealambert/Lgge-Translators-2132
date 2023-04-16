package compiler.Lexer;

import compiler.ASTNode;
import compiler.Parser.AccessToIndexArray;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Identifier extends ASTNode implements Symbol, Visitable {

    private final String attribute;
    private final String name = "Identifier";

    public Identifier(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getAttribute() {
        return this.attribute;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "<" + this.name + ", " + this.getAttribute() + ">";
    }


    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor semanticVisitor) throws SemanticAnalysisException {
        semanticVisitor.visit(this);
    }
}