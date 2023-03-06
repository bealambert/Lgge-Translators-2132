import compiler.Lexer.*;
import compiler.Lexer.Boolean;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestKeyword {

    Random random = new Random();

    String[] acceptedKeywords = new String[]{
            "const", "record", "var", "val", "proc",
            "for", "to", "by", "while", "if", "else",
            "return", "and", "or"
    };

    String[] acceptedKeywordsSeparatedWithWhitespace = new String[]{
            "const ", "record ", "var ", "val ", "proc ",
            "for ", "to ", "by ", "while ", "if ", "else ",
            "return ", "and ", "or "
    };


    @Test
    public void test_recognize_const() {
        String input = "const ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_record() {
        String input = "record ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_var() {
        String input = "var ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_val() {
        String input = "val ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }


    @Test
    public void test_recognize_proc() {
        String input = "proc ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_for() {
        String input = "for ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_to() {
        String input = "to ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_by() {
        String input = "by ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_while() {
        String input = "while ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_if() {
        String input = "if ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_else() {
        String input = "else ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }


    @Test
    public void test_recognize_return() {
        String input = "return ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_and() {
        String input = "and ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_or() {
        String input = "or ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Keyword);
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_random() {

        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedKeywords.length;

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        for (int i = 0; i < length_of_input; i++) {
            int index = random.nextInt(length_of_accepted_values);
            String randomValue = acceptedKeywordsSeparatedWithWhitespace[index];
            generated_input.append(randomValue);

            correct_output[i] = acceptedKeywords[index];
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String s : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertTrue(nextSymbol instanceof Keyword);
            assertEquals(nextSymbol.getAttribute(), s);
        }
    }
}
