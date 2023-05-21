package compiler.Semantic;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Parser.Parser;
import compiler.SemanticAnalysisException;
import compiler.Token;

import java.util.HashMap;

public class Semantic {

    Parser parser;
    ASTNode root;
    SymbolTable globalSymbolTable = new SymbolTable(null);
    AssignSymbolTableVisitor assignSymbolTableVisitor = new AssignSymbolTableVisitor();
    MakeSemanticAnalysisVisitor makeSemanticAnalysisVisitor = new MakeSemanticAnalysisVisitor();


    public Semantic(Parser parser) {
        this.parser = parser;
        this.root = parser.getAST();
    }

    public void setSymbolTableFieldToASTNodes() {
        // make first traversal of the AST
        ASTNode astNode = this.root;
        while (astNode != null) {
            astNode.accept(assignSymbolTableVisitor, globalSymbolTable);
            astNode = astNode.getNext();
        }

    }

    public void performSemanticAnalysis() throws SemanticAnalysisException {
        // after the filling of all symbol tables let's now make an analysis of it
        ASTNode astNode = this.root;
        while (astNode != null) {

            astNode.accept(makeSemanticAnalysisVisitor);
            astNode = astNode.getNext();
        }
    }

    public void makeSemanticAnalysis() throws SemanticAnalysisException {
        setSymbolTableFieldToASTNodes();
        performSemanticAnalysis();
    }

    public ASTNode getRoot() {
        return root;
    }
}
