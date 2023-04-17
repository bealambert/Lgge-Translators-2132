import compiler.ASTNode;
import compiler.Lexer.Lexer;
import compiler.Parser.*;
import compiler.Semantic.Semantic;
import compiler.SemanticAnalysisException;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestSemanticAnalysis {

    @Test
    public void TestSimpleDeclarationVariable() {
        String input = "var c int[] = int[](5);";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void TestSimpleDeclarationVariableFail() {
        String input = "var c int[] = real[](5);";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }

    @Test
    public void TestIfCondition() {
        // String input = "if value <> 3 {return x*x;} else{return 2*3;}";
        String input = "var value int = 0;" + "if value <> 3 {return x*x;}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void TestCreateExpressionVariables() {
        // String input = "if value <> 3 {return x*x;} else{return 2*3;}";
        String input = "var value int = 0;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }
}
