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
                "const s string = \"abc\" + \"123\";";
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
