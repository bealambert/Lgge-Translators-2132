import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertNotNull;

public class TestCornerCases {

    @Test
    public void test_int() {
        String input = "10192";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());

    }

    @Test
    public void test_float() {
        String input = "    1.12";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test_special_char_comment() {
        String input = "  //   =1";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test_special_keyword_identifier() {
        String input = "  const ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test_special_str() {
        String input = "  \"onst .32/*à\"";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test_special_multSymbols() {
        //String input = "  const 92+1.19>=\"onst .32/*à\"abx1//bizouille\n ";
        //String input = "t|rue+-9.0>=//hey\na int= \"Hel@lo\"/2; ";
        String input = "\n\t>=\"onst .32/*à\"abx1//bizouille\n 42";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Symbol result = lexer.getNextSymbol();
        while (result != null) {
            System.out.println(result.toString());
            result = lexer.getNextSymbol();

        }
    }
}