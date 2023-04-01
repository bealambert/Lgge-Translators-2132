import compiler.ASTNode;
import compiler.Lexer.Lexer;
import compiler.Parser.*;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertTrue;

public class TestParserBasics {

    @Test
    public void TestAll() {

        //String input = "var a int = 2;";
        // String input = "azea_8z = 3.54;";
        //String input = "f();";
        /*String input = "record Point{\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}";*/
        /*String input = "proc square(v int, a8aze_z real) int {\n" +
                "    return v*v;\n" +
                "}";*/
        //String input = "return v * v;";
        /*String input = "for var i int =2 to 100 by 1 {" +
                "return v*v;}";*/
        //String input = "if value <> 3 {return x*x;} else{return 2*3;}";
        //String input = "var c int[] = int[](5);";

        String allInput = new String(
                "var a int = 2;azrvzoe = 3.54;f();" +
                        "record Point{\n" +
                        "    x int;\n" +
                        "    y int;\n" +
                        "} proc square(v int, a8aze_z real) int {\n" +
                        "    return v*v;\n" +
                        "} return v * v;" +
                        "for var i int =2 to 100 by 1 {return v*v;}");
        StringReader reader = new StringReader(allInput);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        boolean flag = false;
        while (!flag) {
            //System.out.println(root);
            root = root.getNext();
            flag = root == null;
        }
    }

    @Test
    public void TestCreateVariable() {
        String input = "var a int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof CreateVariable);

    }

    @Test
    public void TestReassignment() {
        String input = "azea_8z = 3.54;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof Reassignment);

    }

    @Test
    public void TestFunctionCall() {
        String input = "f();";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof FunctionCall);
    }

    @Test
    public void TestRecord() {
        String input = "record Point{\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof Records);

    }

    @Test
    public void TestCreateProcedure() {
        String input = "proc square(v int, a8aze_z real) int {\n" +
                "    return v*v;\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof CreateProcedure);

    }

    @Test
    public void TestReturnStatement() {
        String input = "return v * v;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof ReturnStatement);

    }

    @Test
    public void TestIfElse() {
        String input = "if value <> 3 {return x*x;} else{return 2*3;}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof IfElse);
    }

    @Test
    public void TestArrayInitialization() {
        String input = "var c int[] = int[](5);";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof CreateVariable);
    }

    @Test
    public void TestInitializationWithFunctionCallExpression() {
        String input = "var a int = fun(a,3)*2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof CreateVariable);
    }

    @Test
    public void TestNestedFunctionCall() {
        String input = "writeln(square(value));";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof FunctionCall);
    }

    @Test
    public void TestExtremeFunctionCall() {
        String input = "writeln(square(value) + circle(3, a) * 3 + rectangle(a,b,c,d), 2 * square(value));";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        System.out.println(root);
        assertTrue(root instanceof FunctionCall);
    }
}
