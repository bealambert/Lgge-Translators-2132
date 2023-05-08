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
        String input = "var i int = 3 + 2; " +
                "var j real = 1.5 * 4.2;" +
                "const empty bool = true;" +
                "const s string = \"abc\" + \"123\";" +
                "var comp bool = \"abc\" == \"def\";" +
                "var notEqual bool = \"aaa\" <> \"aba\";" +
                "var equalIntegers bool = 1.0 <> 2.4;" +
                "var moduloIntegers int = 10 % 4;" +
                "var lt bool = 4 < 3;" +
                "var le bool = 7 <=2;" +
                "var gt bool = 5 >5;" +
                "var ge bool = 7 >=4;" +
                "var comp2 bool = true == true;" +
                "var comp3 bool = true <> true;";

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
                "proc square(v int) int {\n" +
                        "    return v*v;\n" +
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
}
