package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Type extends Identifier {

    public Type(Identifier attribute) {
        super(attribute.getAttribute());
    }


    public String getName() {
        return "Type";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getAttribute() {
        return super.getAttribute();
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
