package compiler.Parser;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class Records extends ASTNode implements Visitable {

    Identifier identifier;

    public Records(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
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
