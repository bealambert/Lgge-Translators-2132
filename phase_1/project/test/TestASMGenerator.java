import compiler.ASMGenerator.Generator;
import compiler.Lexer.Lexer;
import compiler.Parser.Parser;
import compiler.Semantic.Semantic;
import compiler.SemanticAnalysisException;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.fail;

public class TestASMGenerator {

    @Test
    public void TestSimpleDeclarationVariable() {
        String input = "const i int = 3 + 2; " +
                "const j real = 1.5 * 4.2;" +
                "const k real = (1.0 + 3.2) * (4.1 - 6.2);" +
                "const empty bool = true;" +
                "const s string = \"abc\" + \"123\" + \"zzz\";" +
                "var comp bool = \"abc\" == \"def\";" +
                "var notEqual bool = \"aaa\" <> \"aba\";" +
                "var equalIntegers bool = 1.0 <> 2.4;" +
                "var moduloIntegers int = 10 % 4;" +
                "var lt1 bool = 4 < i;" +
                "var lt2 bool = 4.2 < 7.3;" +
                "var le1 bool = 7 <=2;" +
                "var le2 bool = 7.4 <= 2.2;" +
                "var gt1 bool = 5 >5;" +
                "var gt2 bool = 5.4 > 7.2;" +
                "var ge1 bool = 7 >=4;" +
                "var ge2 bool = 7.1 >= 4.0;" +
                "var comp2 bool = true == true;" +
                "var comp3 bool = true <> true;" +
                "var aaaa bool = (1 + 3 *2) > 5;";

        // add array

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestSimpleDeclarationLocalVariable() {
        String input = "proc square() void { " +
                "var azer int = 6266;" +
                "var i int = 3 + 2; " +
                "var j real = 1.5 * 4.2;" +
                "var k real = (1.0 + 3.2) * (4.1 - 6.2);" +
                "var empty bool = true;" +
                "var s string = \"abc\" + \"123\" + \"zzz\";" +
                "var comp bool = \"abc\" == \"def\";" +
                "var notEqual bool = \"aaa\" <> \"aba\";" +
                "var equalIntegers bool = 1.0 <> 2.4;" +
                "var moduloIntegers int = 10 % 4;" +
                "var lt1 bool = 4 < i;" +
                "var lt2 bool = 4.2 < 7.3;" +
                "var le1 bool = 7 <=2;" +
                "var le2 bool = 7.4 <= 2.2;" +
                "var gt1 bool = 5 >5;" +
                "var gt2 bool = 5.4 > 7.2;" +
                "var ge1 bool = 7 >=4;" +
                "var ge2 bool = 7.1 >= 4.0;" +
                "var comp2 bool = true == true;" +
                "var comp3 bool = true <> true;" +
                "var aaaa bool = (1 + 3 *2) > 5;" +
                "}";

        // add array

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestExpression() {
        String input =
                "proc square(v int, j int, r int) int {\n" +
                        "    return (v + j * 2) / r;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }


    @Test
    public void TestCreateVariable() {
        String input =
                "proc square(v int, j int, r int) int {\n" +
                        "var xcv int = 3 + 2;" +
                        "    return xcv;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestCreateVariable2() {
        String input =
                "proc square(v int, j int, r int) int {\n" +
                        "var xcv int = v + j;" +
                        "var bn int = r *j;" +
                        "var wx int = xcv + bn;" +
                        "    return xcv;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestLocalAndGlobalVariables() {
        String input =
                "var i int = 3 + 2; " +
                        "var j int = i * 4;" +
                        "proc square(a int, b int) int {\n" +
                        "var xcv int = a + i;" +
                        "var bn int = b * j;" +
                        "var wx int = xcv + bn;" +
                        "    return wx;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestNewArray() {
        String input =
                "var x int = 7;" +
                        "var i int[] = int()[(5 *3) + x];" +
                        "proc square(a int, b int) int {\n" +
                        "var j int[] = int()[x + b];" +
                        "    return a;\n" +
                        "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestCreateVoidVariable() {
        String input =
                "var x int[] = int() [5];" +
                        "var y int;" +
                        "proc square(a int, b int) int {\n" +
                        "    return a;\n" +
                        "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestUniqueVariable() {
        String input =
                "var x int[] = int() [5];" +
                        "x[2] = 3;" +
                        "x[0] = 1;" + "x[1] = 6;" +
                        "proc square(a int, b int) int {\n" +
                        "var y int[] = int() [4];" +
                        "y[0] = 2 * b; " +
                        "    return y[0] + a * b;\n" +
                        "}";
        ;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void testVoidReturn() {
        String input =
                "var x int[] = int() [5];" +
                        "x[2] = 3;" +
                        "x[0] = 1;" + "x[1] = 6;" +
                        "proc square(a int, b int) void {\n" +
                        "var y int[] = int() [4];" +
                        "y[0] = 2 * b; " +
                        "    return;\n" +
                        "}";
        ;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestWhileLoop() {
        String input =
                // GLOBAL
                "var x int[] = int() [5];" +
                        "x[2] = 3;" +
                        // FUNCTION
                        "proc square(a int, b int) void {\n" +
                        "var i int = 0;" +
                        "while i < 10 {" +
                        "i = i + 1;" +
                        "}" +
                        "return;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestForLoopAssignVariable() {
        String input =
                // FUNCTION
                "proc square(a int, b int) void {\n" +
                        "var i int;" +
                        "for i = 0  to 100 by 10 {" +
                        "}" +
                        "return;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestForLoopCreateVariable() {
        String input =
                // FUNCTION
                "proc square(a int, b int) void {\n" +
                        "for var i int = 0  to 100 by 10 {" +
                        "var j int = 3;" +
                        "var result int = a * j +b;" +
                        "}" +
                        "return;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }


    @Test
    public void TestIfCondition() {
        String input =
                // FUNCTION
                "proc square(a int, b int) int {\n" +
                        "if a > 3 {" +
                        "return a;" +
                        "}" +
                        "return b;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestIfElseCondition() {
        String input =
                // FUNCTION
                "proc square(a int, b int) int {\n" +
                        "if a > 3 {" +
                        "var i bool = true;" +
                        "}" +
                        "else {" +
                        "var i int = 4;\n" +
                        "}" +
                        "return a;" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestVoid() {
        String input = "proc square(a int, b int) int {\n" +
                "if a > 3 {" +
                "return a;" +
                "}" +
                "return b;\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void TestFunctionCallParameters() {
        String input = "proc square(a real, b real[]) real {\n" +
                "if a > 3.0{" +
                "return a;" +
                "}" +
                "return b[0];\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void TestReassignmentLocally() {
        String input = "proc square(a real, b real[]) real {\n" +
                "var i int = 17;" +
                "i = 6266;" +
                "return a + b[0];" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void TestReassignmentGlobally() {
        String input = " var i int = 17;" +
                "proc square(a real, b real[]) real {\n" +
                "i = 6266;" +
                "return a + b[0];" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void TestFunctionCall() {
        String input = "proc square(a real, b real[]) real {\n" +
                "return a + b[0];" +
                "}" +
                "proc azer(c real) real {" +
                "var j real[] = real()[5];" +
                "j[0] = 2.0;" +
                "return square(c, j);" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }
    }

    @Test
    public void Test2121() {
        String input =
                // FUNCTION
                "proc square(a int, b int) void {\n" +
                        "var i int;" +
                        "i = 3;" +
                        "return;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            fail();
        }

    }

    @Test
    public void TestWriteln() {
        String input =
                // FUNCTION
                "proc square(a int, b int) void {\n" +
                        "writeln(\"abc\")" +
                        "var i bool = not(true);" +
                        "return;\n" +
                        "}";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestConversion() {
        String input =
                // FUNCTION
                "const i real = 2 + 3.5;";

        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestAssignFieldRecord() {
        String input =
                // FUNCTION
                "const i int = 2;" +
                        "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) void {\n" +
                        "writeln(\"abc\")" +
                        "var i bool = not(true);" +
                        "var d Point = Point(10, 20, 30);" +
                        "var r Point = Point(30, 20, 10);" +
                        "d.x = 5;" +
                        "d.z = 100;" +
                        "r.y = 22;" +
                        "return;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestAssignFieldArrayRecord() {
        String input =
                // FUNCTION
                "const i int = 2;" +
                        "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) void {\n" +
                        "writeln(\"abc\")" +
                        "var i bool = not(true);" +
                        "var myArray Point[] = Point() [2];" +
                        "var d Point = Point(10, 20, 30);" +
                        "var r Point = Point(30, 20, 10);" +
                        "myArray[0] = d;" +
                        "myArray[1] = r;" +
                        "return;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestRetrieveValueFromMethodCallRecord() {
        String input =
                // FUNCTION
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) void {\n" +
                        "var d Point = Point(10, 20, 30);" +
                        "var z int[] = int()[5];" +
                        "var value int = z[0] + 3;" +
                        "return;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestRetrieveValueFromMethodCallRecordArray() {
        String input =
                // FUNCTION
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) void {\n" +
                        "var d Point = Point(10, 20, 30);" +
                        "var z int[] = int()[5];" +
                        "var j int = d.x + 3;" +
                        "return;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void Test() {
        String input =
                // FUNCTION
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) int {\n" +
                        "var d Point = Point(10, 20, 30);" +
                        "var z int[] = int()[5];" +
                        "var j int = d.x + 3;" +
                        "return d.x + d.y + d.z;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestRecordIndex() {
        String input =
                // FUNCTION
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) int {\n" +
                        "var d Point = Point(10, 20, 30);" +
                        "var z Point[] = Point()[5];" +
                        "z[0] = d;" +
                        "return z[0].x + a;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void TestRecordAssignmentWithIndexArray() {
        String input =
                // FUNCTION
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "proc square(a int, b int) int {\n" +
                        "var myArray int[] = int()[5];" +
                        "myArray[0] = 2;" +
                        "myArray[1] = 3;" +
                        "myArray[2] = 4;" +
                        "myArray[3] = 5" +
                        "myArray[4] = 6;" +

                        "var d Point = Point(myArray[0], 20, 30);" +
                        "var z Point[] = Point()[5];" +
                        "z[0] = d;" +
                        "return z[0].x + a;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }


    @Test
    public void TestRecordWithCallToConstructorWithRecordField() {
        String input =
                // FUNCTION
                "record Point {\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "    z int;\n" +
                        "}" +
                        "record Person {\n" +
                        "    name string;\n" +
                        "    location Point;\n" +
                        "    history int[];\n" +
                        "}" +

                        "proc square(a int, b int) int {\n" +
                        "var myArray int[] = int()[5];" +
                        "myArray[0] = 2;" +
                        "myArray[1] = 3;" +
                        "myArray[2] = 4;" +
                        "myArray[3] = 5" +
                        "myArray[4] = 6;" +

                        "var d Point = Point(myArray[0], 20, 30);" +
                        "var loic Person = Person(\"loic\", d, myArray);" +
                        "var myLocation Point = loic.location;" +
                        "return myLocation.x;\n" +
                        "}";


        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);
        Semantic semantic = new Semantic(parser);
        try {
            semantic.makeSemanticAnalysis();
            Generator generator = new Generator(semantic.getRoot());
            generator.generateBytecode();
        } catch (SemanticAnalysisException e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

}
