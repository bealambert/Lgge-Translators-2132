import compiler.Lexer.Boolean;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;

import static org.junit.Assert.*;

public class TestBoolean {

    @Test
    public void test_recognize_boolean_one_value_true(){
        String input = "true";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(lexer.getNextSymbol().getName(), Boolean.class.getName());
    }

    @Test
    public void test_recognize_boolean_one_value_false(){
        String input = "false";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Boolean.class.getName());
        assertEquals(nextSymbol.getAttribute(), "false");
    }


    @Test
    public void test_recognize_boolean_multiple_values(){
        String [] correct_output = new String[] {"false","true", "true","false", "false","false", "true"};
        String input = "false true true false false false true";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (int i = 0; i < 7; i++) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertEquals(nextSymbol.getName(), Boolean.class.getName());
            assertEquals(nextSymbol.getAttribute(), correct_output[i]);
        }
    }
}
