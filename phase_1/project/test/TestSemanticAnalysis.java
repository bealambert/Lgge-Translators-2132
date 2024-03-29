import compiler.Lexer.Lexer;
import compiler.Parser.Parser;
import compiler.Semantic.Semantic;
import compiler.SemanticAnalysisException;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

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
                "    location int[];\n" +
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
                        + "var d Person = Person(\"me\", Point(3,7), int[](2));  // new record;\n";
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
    public void TestCreateRecordVariableShouldFailArrayInitializer() {
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
                        + "var d Person = Person(\"me\", Point(3,7), int[](2.5));  // new record;\n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }


    @Test
    public void TestCreateRecordVariableShouldFailRecordCall() {
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
                        + "var d Person = Person(\"me\", Point(3.5,7), int[](2.5));  // new record;\n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestCreateRecordVariableShouldFailParameter() {
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
                        + "var d Person = Person(10, Point(3,7), int[](2.5));  // new record;\n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }

    @Test
    public void TestReassignment() {
        String input = "var x int = 4; var y int = 5; x = y;";

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
    public void TestWrongReassignment() {
        String input = "var x int = 4; var y real = 5.0; x = y;";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);
    }

    @Test
    public void TestForLoopAssignVariable() {
        String input = "var value int = 5; var i int; " +
                "for i=1 to 100 by 2 {\n" +
                "        while value <> 3 {\n" +
                "            // ...\n" +
                "        }\n" +
                "    }";

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
    public void TestForLoopCreateVariables() {
        String input = "var value int = 5; " +
                "for var i int =1 to 100 by 2 {\n" +
                "        while value <> i {\n" +
                "            // ...\n" +
                "        }\n" +
                "    }";

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
    public void TestWhileLoop() {
        String input = "var value int = 5;" +
                "while value <> 3 {\n" +
                "            // ...\n" +
                "        }";

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
    public void TestFunctionCall() {
        String input = "record Point {\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}" +
                "proc square(v int, a int, p Point) int {\n" +
                "    return v*a + p.x + p.y;\n" +
                "}"

                + "var i int = 0; var j int = 1; var d Point = Point(i, 2);"
                + "var result int = square(1, j, Point(1,1));";
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
    public void TestFunctionCallWrongCallToRecord() {
        String input = "record Point {\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}" +
                "proc square(v int, a int, p Point) int {\n" +
                "    return v*a + p.x + p.y;\n" +
                "}"

                + "var i int = 0; var j int = 1; var d Point = Point(i, 2);"
                + "var result int = square(1, j, Point(1, 1.0));"; // 1.0 -> expected an int
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestFunctionCallWrongParameterProcedure() {
        String input = "record Point {\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}" +
                "proc square(v int, a int, p Point) int {\n" +
                "    return v*a + p.x + p.y;\n" +
                "}"

                + "var i int = 0; var j int = 1; var d Point = Point(i, 2);"
                + "var result int = square(1.5, j, Point(1,1));"; // first argument expected int
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestFunctionCallAndMethodWithAccessArray() {
        String input = "record Point {\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}" +
                "proc copyPoints(p Point[]) Point {\n" +
                "     return Point(p[0].x+p[1].x, p[0].y+p[1].y);" +
                "} \n"
                + "var points Point[] = Point[](2);"
                + "var first Point = Point(1, 5);"
                + "var second Point = Point(2, 4);"
                + "points[0] = first;"
                + "points[1] = second;"
                + "var copy Point = copyPoints(points);";
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
    public void TestVoidFunction() {
        String input = "record Point {\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}" +
                "proc square(v int, a int, p Point) void {\n" +
                "    return ;\n" +
                "}"

                + "var i int = 0; var j int = 1; var d Point = Point(i, 2);"
                + "square(1, j, Point(2, 5));";
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
    public void TestNotAllowedOperation() {
        String input = "var x bool = true + false;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            fail();
        } catch (SemanticAnalysisException e) {
            //
        }
    }

    @Test
    public void TestBuiltInNot() {
        String input = "var c bool = not(true and false);";
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
    public void TestIO() {
        String input = "var c int = 3 + readInt();";
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
    public void TestPrecedenceConstInsideFunction() {
        String input = "const a int = 3;" +
                "const b int = 32; " +
                "record Person {\n" +
                "    name string;\n" +
                "    location int[];\n" +
                "    history int[];\n" +
                "}" +
                "proc square(v int) int {\n" +
                "const c bool = true;" +
                "    return v*v;\n" +
                "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestPrecedenceConstAfterRecord() {
        String input = "const a int = 3;" +
                "const b int = 32; " +
                "record Person {\n" +
                "    name string;\n" +
                "    location int[];\n" +
                "    history int[];\n" +
                "}" +
                "const c bool = true;" +

                "proc square(v int) int {\n" +
                "    return v*v;\n" +
                "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }


    @Test
    public void TestPrecedenceRecordBeforeConst() {
        String input = "record Person {\n" +
                "    name string;\n" +
                "    location int[];\n" +
                "    history int[];\n" +
                "}" +
                "const a int = 3;" +
                "const b int = 32; " +
                "const c bool = true;" +

                "proc square(v int) int {\n" +
                "    return v*v;\n" +
                "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestPrecedenceRecordInsideFunction() {
        String input = "const a int = 3;" +
                "const b int = 32; " +
                "const c bool = true;" +

                "proc square(v int) int {\n" +
                "record Person {\n" +
                "    name string;\n" +
                "    location int[];\n" +
                "    history int[];\n" +
                "}" +
                "    return v*v;\n" +
                "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }

    @Test
    public void TestPrecedenceRespected() {
        String input = "const a int = 3;" +
                "const b int = 32; " +
                "const c bool = true;" +
                "record Person {\n" +
                "    name string;\n" +
                "    location int[];\n" +
                "    history int[];\n" +
                "}" +

                "proc square(v int) int {\n" +

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
    public void TestTypeConversionFromIntToReal() {
        String input = "var x real = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void TestOperationWithRealAndInt() {
        String input = "var x real = 2 + 3.5;" +
                "var i real;" +
                "i = 3;" +
                "var j int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void TestModuloOnIntegerType() {
        String input = "var value int = 5 % 3;";

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
    public void TestNoConversionForModuloOperation() {
        String input = "var a int = 5.0 % 3;";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        Assert.assertThrows(SemanticAnalysisException.class, semantic::makeSemanticAnalysis);

    }


}
