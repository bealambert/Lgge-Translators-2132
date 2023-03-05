import compiler.Lexer.*;
import compiler.Lexer.Boolean;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestSpecialCharacter {

    Random random = new Random();

    String[] acceptedSpecialCharacters = new String[]{
            "=", "+", "-", "*", "/", "%",
            "==", "<>", "<", ">", "<=", ">="
            , "(", ")", "{", "}", "[", "]",
            ".", ";", ","
    };

    String[] acceptedSpecialCharactersWithWhitespace = new String[]{
            "= ", "+ ", "- ", "* ", "/ ", "% ",
            "== ", "<> ", "< ", "> ", "<= ", ">= "
            , "( ", ") ", "{ ", "} ", "[ ", "] ",
            ". ", "; ", ", "
    };


    @Test
    public void test_recognize_assignment_operator() {
        String input = "= ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_plus_operator() {
        String input = "+ ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_minus_operator() {
        String input = "- ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }


    @Test
    public void test_recognize_multiply_operator() {
        String input = "* ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_divide_operator() {
        String input = "/ ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }


    @Test
    public void test_recognize_modulo_operator() {
        String input = "% ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_equality_operator() {
        String input = "== ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_less_than_greater_than() {
        String input = "<> ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_less_than() {
        String input = "< ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_greater_than() {
        String input = "> ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_less_than_or_equal() {
        String input = "<= ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_greater_than_or_equal() {
        String input = ">= ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_left_parenthesis() {
        String input = "( ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_right_parenthesis() {
        String input = ") ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_left_curly_bracket() {
        String input = "{ ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_right_curly_bracket() {
        String input = "} ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_left_bracket() {
        String input = "[ ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_right_bracket() {
        String input = "] ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_dot() {
        String input = ". ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_semicolon() {
        String input = "; ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), SpecialCharacter.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_recognize_comma() {
        String input = ", ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Keyword.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length() - 1));
    }

    @Test
    public void test_random() {

        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedSpecialCharacters.length;

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        for (int i = 0; i < length_of_input; i++) {
            int index = random.nextInt(length_of_accepted_values);
            String randomValue = acceptedSpecialCharactersWithWhitespace[index];
            generated_input.append(randomValue);

            correct_output[i] = acceptedSpecialCharacters[index];
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String s : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertEquals(nextSymbol.getName(), SpecialCharacter.class.getName()); // can replace it with instance of
            assertEquals(nextSymbol.getAttribute(), s);
        }
    }
}
