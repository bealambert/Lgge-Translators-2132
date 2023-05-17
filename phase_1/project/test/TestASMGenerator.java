import compiler.ASMGenerator.Generator;
import compiler.ASMGenerator.Properinit;
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
        String input = "var i int = 3 + 2; " +
                "var j real = 1.5 * 4.2;" +
                "const empty bool = true;" +
                "const s string = \"abc\" + \"123\";" +
                "var comp bool = \"abc\" == \"def\";" +
                "var notEqual bool = \"aaa\" <> \"aba\";" +
                "var equalIntegers bool = 1.0 <> 2.4;" +
                "var moduloIntegers int = 10 % 4;" +
                "var lt bool = 4 < i;" +
                "var le bool = 7 <=2;" +
                "var gt bool = 5 >5;" +
                "var ge bool = 7 >=4;" +
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


}
