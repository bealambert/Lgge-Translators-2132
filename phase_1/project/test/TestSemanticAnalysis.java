import compiler.ASTNode;
import compiler.Lexer.Lexer;
import compiler.Parser.*;
import compiler.Semantic.Semantic;
import compiler.SemanticAnalysisException;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

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
        String input = "if 2 <> 3 { var x int = 2*3;}";
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
    public void TestWrongTypeIntBoolean() {
        String input = "var x int = 2<>3;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }

    @Test
    public void TestWrongTypeIntReal() {
        String input = "var x int = 2.14;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }


    @Test
    public void TestWrongTypeRealInt() {
        String input = "var x real = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }

    @Test
    public void TestCreateExpressionVariables() {
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

    @Test
    public void TestCreateProcedure() {
        String input = "proc square(v int) int {\n" +
                "    return v*v;\n" +
                "}";
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
    public void TestCreateProcedureWrongReturnType() {
        String input = "proc square(v int) real {\n" +
                "    return v*v;\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }


    @Test
    public void TestCreateProcedureWrongReturnExpression() {
        String input = "proc square(v real) int {\n" +
                "    return v*v;\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestNotDefinedVariable() {
        String input = "if value <> 3 {return x*x;} else{return 2*3;}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }


    @Test
    public void TestConditionWithVariableCreatedBefore() {
        String input = "var x int = 3;" + "var value int = 2;" + "if value <> 3 { var y int = x*2;} else{ var y int = 2*3;}";
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
    public void TestCreateRecordDefinedNewTypes() {
        String input = "record Person {\n" +
                "    name string;\n" +
                "    location int[];\n" +   // changed Point object to int
                "    history int[];\n" +
                "}";
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
    public void TestCreateNewTypesAndUseAfterInsideRecord() {
        String input =
                "record Person {\n" +
                        "    name string;\n" +
                        "    location Point;\n" +
                        "    history int[];\n" +
                        "}";

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
    public void TestCreateRecordVariable() {
        String input =
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "}"
                        +
                        "record Person {\n" +
                        "    name string;\n" +
                        "    location Point;\n" +
                        "    history int[];\n" +
                        "}\n"
                        + "var d Person = Person(\"me\", Point(3,7), int[](a*2));  // new record;\n";
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
