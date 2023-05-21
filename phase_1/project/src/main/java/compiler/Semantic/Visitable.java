package compiler.Semantic;

import compiler.SemanticAnalysisException;

public interface Visitable {

    public void accept(Visitor visitor, SymbolTable symbolTable);

    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException;
}
