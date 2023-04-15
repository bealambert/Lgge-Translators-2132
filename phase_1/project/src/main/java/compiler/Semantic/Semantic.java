package compiler.Semantic;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Parser.Parser;
import compiler.Token;

import java.util.HashMap;

public class Semantic {

    Parser parser;
    SymbolTable globalSymbolTable = new SymbolTable(null);
    AssignSymbolTableVisitor assignSymbolTableVisitor = new AssignSymbolTableVisitor();
    MakeSemanticAnalysisVisitor makeSemanticAnalysisVisitor = new MakeSemanticAnalysisVisitor();


    public Semantic(Parser parser) {
        this.parser = parser;
    }

    public void setSymbolTableFieldToASTNodes() {
        // make first traversal of the AST
        ASTNode astNode = parser.getAST();
        while (astNode != null) {
            astNode.accept(assignSymbolTableVisitor, globalSymbolTable);
            astNode = astNode.getNext();
        }

    }

    public void makeSemanticAnalysis() {
        //
        ASTNode astNode = parser.getAST();
        while (astNode != null) {

            astNode.accept(makeSemanticAnalysisVisitor, globalSymbolTable);
            astNode = astNode.getNext();
        }
    }
}
