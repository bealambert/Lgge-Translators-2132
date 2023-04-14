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
        assertTrue(root instanceof CreateVariables);

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
        assertTrue(root instanceof CreateVariables);
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
                "const j real = 3.2*5.0;\n" +
                "const k int = i*3;\n" +
                "const message string = \"Hello\";\n" +
                "const isEmpty bool = true;\n" +
                "\n" +
                "// Constant declarations are followed by record definitions.\n" +
                "\n" +
                "record Point {\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}\n" +
                "\n" +
                "record Person {\n" +
                "    name string;\n" +
                "    location Point;\n" +
                "    history int[];\n" +
                "}\n" +
                "\n" +
                "// Global variables and values are initialized in the order they appear.\n" +
                "// Variables and Values always have an initializer, which can be an\n" +
                "// expression. Values are equivalent to a single-assignment variable.\n" +
                "// Accessing a variable or value that is not initalized (for example, by calling\n" +
                "// a procedure in the initializer expression that accesses a global variable\n" +
                "// before it has been initialized) results in undefined behavior.\n" +
                "\n" +
                "var a int = 3;\n" +
                "val e int = a*2;    // value\n" +
                "\n" +
                "// For arrays, only one-dimensional arrays of basetypes are allowed.\n" +
                "// Record and array variables are references to a record or array. To initialize\n" +
                "// the variable, it must be assigned an existing record or array, or a new\n" +
                "// array or record must be created.\n" +
                "\n" +
                "var c int[] = int[](5);  // new array of length 5\n" +
                "var d Person = Person(\"me\", Point(3,7), int[](a*2));  // new record\n" +
                "\n" +
                "// Procedures:\n" +
                "// Procedures have parameters and a return type. The return type\n" +
                "// can be a type or void.\n" +
                "// Base type arguments are always passed by value.\n" +
                "// Records and arrays are always passed by reference.\n" +
                "//\n" +
                "// There are built-in procedures for I/O:\n" +
                "//    readInt, readReal, readString, writeInt, writeReal, write, writeln\n" +
                "\n" +
                "// Procedure calls can forward-reference procedures, even in initializers of global\n" +
                "// variables and values, but not in constants.\n" +
                "\n" +
                "// Local variables and values:\n" +
                "// Procedures and while/if/else/for blocks can declare local variables and values\n" +
                "// mixed with statements.\n" +
                "// Their initialization follows the same rules as for global variables.\n" +
                "\n" +
                "// Scope:\n" +
                "// Lexical scoping.\n" +
                "// Local variables can shadow variables with the same name in surrounding scopes.\n" +
                "// Keywords, types, procedures, constants, and variables share one name space.\n" +
                "// All names are case sensitive.\n" +
                "\n" +
                "// Control structures:\n" +
                "// for, while, if/else\n" +
                "// Control structure bodies are always block statements.\n" +
                "\n" +
                "// The left side of an assignment must be either:\n" +
                "//    a variable\n" +
                "//    an element of an array\n" +
                "//         a[3] = 1234;   // a is an array of int\n" +
                "//    a field access to a record\n" +
                "//         a.x = 123;\n" +
                "//         a[3].x = 12;\n" +
                "//    To simplify the compiler, the left side cannot be an expression, this is not allowed:\n" +
                "//         someFunctionThatReturnsAnArray()[2] = 2;\n" +
                "//    Assigning an array or record, copies the reference.\n" +
                "\n" +
                "// Deallocating arrays and records:\n" +
                "// We assume that there is no garbage collector and that the created\n" +
                "// arrays and records have to be manually deallocated when not needed anymore.\n" +
                "//       var x Point = Point(3,5);\n" +
                "//       delete x;\n" +
                "// Accessing a deallocated array or record results in undefined behavior.\n" +
                "// Deallocation is not deep. Before a record is deallocated, it is the duty\n" +
                "// of the programmer to deallocate arrays or records referenced by that record.\n" +
                "\n" +
                "                                                                                              \n" +
                "proc square(v int) int {\n" +
                "    return v*v;\n" +
                "}\n" +
                "\n" +
                "proc copyPoints(p Point[]) Point {\n" +
                "     return Point(p[0].x+p[1].x, p[0].y+p[1].y);\n" +
                "}\n" +
                "                            \n" +
                "proc main() void {\n" +
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
                "} \n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        System.out.println(root);
        //assertTrue(root instanceof FunctionCall);

    }
}
