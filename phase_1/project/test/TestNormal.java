import compiler.Lexer.Lexer;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestNormal {

    @Test
    public void test() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test_void() {
        String input = "";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertNull(lexer.getNextSymbol());
    }
}
