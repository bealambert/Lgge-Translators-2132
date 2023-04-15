package compiler.Semantic;

public interface Visitable {

    public void accept(AssignSymbolTableVisitor visitor);

    public void accept(MakeSemanticAnalysisVisitor makeSemanticAnalysisVisitor, SymbolTable symbolTable);
}
