import compiler.ASMGenerator.Generator;
import compiler.ASTNode;
import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

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
        assertTrue(root instanceof CreateVariables);

    }

    @Test
    public void TestRecords() {
        String input = "record Person {\n" +
                "    name string;\n" +
                "    location Point;\n" +
                "    history int[];\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        System.out.println(root);
        assertTrue(root instanceof InitializeRecords);
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
        assertTrue(root instanceof InitializeRecords);

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
    public void TestSimpleExpression() {
        String input = "a+1*3.2/true%func();";
        int sizeExpected = 9;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    @Test
    public void TestExpressionWithParenthesis() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "a+(1*3.2)/true%func();";
        int sizeExpected = 9;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    @Test
    public void TestExpressionWithMoreParenthesis() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "func() and (4-(3+42));";
        int sizeExpected = 7;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    @Test
    public void TestExpressionBeginWithParenthesis() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "(4-(3+42)) and true;";
        int sizeExpected = 7;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    @Test
    public void TestExpressionBeginWithMoreParenthesis() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "((3+42)-4) and true;";
        int sizeExpected = 7;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    @Test
    public void TestExpressionBeginWithNegValue() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "-4 and true;";
        int sizeExpected = 3;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    @Test
    public void TestExpressionBeginWithNegValueInParenthesis() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "3%(-4 and (-true));";
        int sizeExpected = 5;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }

    /*
    @Test
    public void TestExpressionWithNegValue() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "a*-4 and true;";
        int sizeExpected = 3;
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayOfExpression result = parser.parseArrayOfExpression();
        assertEquals(result.getMyTree().size(), sizeExpected);
    }
    */

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
        assertTrue(root instanceof CreateArrayVariable);
    }

    @Test
    public void TestInitializationWithFunctionCallExpression() {
        String input = "var a int = fun(a,3)*2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        assertTrue(root instanceof CreateVariables);
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
        //System.out.println(root);
        assertTrue(root instanceof FunctionCall);
    }

    @Test
    public void TestAccessToArrayByIndex() {
        String input = "proc copyPoints(p Point[]) Point { return Point(p[0].x+p[1].x, p[0].y+p[1].y);}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        //System.out.println(root);
        assertTrue(root instanceof CreateProcedure);
    }

    @Test
    public void TestAssignToIndexArray() {
        String input = "a[3] = 1234";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        //System.out.println(root);
        assertTrue(root instanceof AssignToIndexArray);
    }

    @Test
    public void TestAssignToRecordAttribute() {
        String input = "a.x = 1234";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        //System.out.println(root);
        assertTrue(root instanceof AssignToRecordAttribute);
    }

    @Test
    public void TestAssignToRecordAttributeAtIndex() {
        String input = "a[3].x = 1234";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        //System.out.println(root);
        assertTrue(root instanceof AssignToRecordAttributeAtIndex);
    }


    @Test
    public void TestProcedureExample() {
        String input = "proc main() void {\n" +
                "    var value int = readInt();                             \n" +
                "    writeln(square(value));\n" +
                "    var i int;\n" +
                "    for i=1 to 100 by 2 {\n" +
                "        while value <> 3 {\n" +
                "            // ....\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    i = i+2*2;\n" +
                "}";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        //System.out.println(root);
        assertTrue(root instanceof CreateProcedure);
    }

    @Test
    public void TestExample() {
        String input =
                "var d Person = Person(\"me\", Point(3,7), int[](a*2));  // new record";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        //System.out.println(root);
        assertTrue(root instanceof CreateVariables);
    }

    @Test
    public void TestCodeExample() {
        String input = "// Source is a single file. No imports, etc.\n" +
                "\n" +
                "// Comments look like this. No block comments.\n" +
                "\n" +
                "// The language is strongly and statically typed.\n" +
                "// Base types are int (signed 32-bit), real (32-bit float), bool (true/false), and string.\n" +
                "// int values are automatically promoted to real values in mixed expressions.\n" +
                "// There is no dedicated char type. Characters are represented by integers.\n" +
                "// Strings are immutable.\n" +
                "// For int and real, available operations are\n" +
                "//      +, -, * , /, - (unary).\n" +
                "//      ==, <>, <, >, <=, >=\n" +
                "// For int:\n" +
                "//      %\n" +
                "// For boolean:\n" +
                "//      and, or, ==, <>\n" +
                "// For strings\n" +
                "//      + (concatenation), ==, <>\n" +
                "// The i-th characters/element in a string/array can be read by the index operator [i].\n" +
                "// Built-in functions:\n" +
                "//      not(bool) bool             negates a boolean value\n" +
                "//      chr(int) string            turns the character (an int value) into a string\n" +
                "//      len(string or array) int   gives the length of a string or array\n" +
                "//      floor(real) int            returns the largest integer less than or equal the real value\n" +
                "\n" +
                "// Exceptions:\n" +
                "// Run-time errors terminate the running program.\n" +
                "// Can happen when:\n" +
                "//    Out of memory\n" +
                "//    Division by zero\n" +
                "//    Out-of-bounds array and string access\n" +
                "//    real->int overflow error\n" +
                "\n" +
                "// Operator precedence:\n" +
                "//     function and constructor calls\n" +
                "//     parenthesis\n" +
                "//     index operator\n" +
                "//     record field access operator .\n" +
                "//     *,/,%\n" +
                "//     +,-, unary -\n" +
                "//     ==, <>, <, >, <=, >=\n" +
                "//     and, or\n" +
                "// Operators with same precedence are left-associative.\n" +
                "      \n" +
                "// Constants must be declared at the top of the source file.\n" +
                "// Constant declarations can use expressions and other constants that\n" +
                "// have been declared earlier. Only base types can be used for constants.\n" +
                "\n" +
                "const i int = 3;\n" +
                "const j real = 3.2*5.0;\n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        System.out.println(root);
        //assertTrue(root instanceof FunctionCall);

    }

    @Test
    public void TestBuiltIn() {
        String input = "var i bool = not(true);";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        /*boolean flag = true;
        while (flag) {
            Symbol symbol = lexer.getNextSymbol();
            if (symbol == null){
                flag = false;
            }
            System.out.println(symbol);
        }*/

        Parser parser = new Parser(lexer);
        ASTNode root = parser.getAST();
        ASTNode copy = root;
        boolean flag = true;
        while (flag) {
            System.out.println(copy);

            copy = copy.getNext();
            if (copy == null) {
                flag = false;
            }

        }
        assertTrue(root instanceof CreateVariables);

    }

}
