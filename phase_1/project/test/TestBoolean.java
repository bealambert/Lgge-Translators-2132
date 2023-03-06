import compiler.Lexer.Boolean;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import compiler.Lexer.Lexer;

import static org.junit.Assert.*;

public class TestBoolean {

    Random random = new Random();

    @Test
    public void test_recognize_boolean_one_value_true() {
        String input = "true";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Boolean);
        assertEquals(nextSymbol.getAttribute(), "true");
    }

    @Test
    public void test_recognize_boolean_one_value_false(){
        String input = "false";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof Boolean);
        assertEquals(nextSymbol.getAttribute(), "false");
    }


    @Test
    public void test_recognize_boolean_multiple_values(){
        String [] correct_output = new String[] {"false","true", "true","false", "false","false", "true"};
        String input = "false true true false false false true";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String s : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertTrue(nextSymbol instanceof Boolean);
            assertEquals(nextSymbol.getAttribute(), s);
        }
    }

    @Test
    public void test_random_boolean_values_and_length() {
        String[] acceptedValuesForStringGeneration = new String[]{"true ", "false "}; // whitespaces are important for the lexer
        String[] acceptedValuesForCorrectness = new String[]{"true", "false"}; // No whitespace to compare attributes of the Symbol

        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedValuesForCorrectness.length;

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        for (int i = 0; i < length_of_input; i++) {
            int index = random.nextInt(length_of_accepted_values);
            String randomValue = acceptedValuesForStringGeneration[index];
            generated_input.append(randomValue);

            correct_output[i] = acceptedValuesForCorrectness[index];
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String s : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertTrue(nextSymbol instanceof Boolean);
            assertEquals(nextSymbol.getAttribute(), s);
        }
    }
}
