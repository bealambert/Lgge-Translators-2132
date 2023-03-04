import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;

public class TestLexer {
    /*
    @Test
    public void test() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }
    */

    @Test
    public void test_int() {
        String input = "10192";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());

    }
    @Test
    public void test_float() {
        String input = "1.12";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

}
