import compiler.Lexer.*;
import compiler.Lexer.Boolean;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestNaturalNumber {

    Random random = new Random();

    @Test
    public void test_recognize_natural_number() {
        String input = "1 "; // whitespace otherwise it would be ambiguous (NaturalNumber vs RealNumber)
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertTrue(lexer.getNextSymbol() instanceof NaturalNumber);
    }

    @Test
    public void test_recognize_end_of_line() {
        String input = "\n812";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof NaturalNumber);
        assertEquals(nextSymbol.getAttribute(), Integer.valueOf("812"));
    }

    @Test
    public void test_recognize_tabulation() {
        String input = "\t741";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof NaturalNumber);
        assertEquals(nextSymbol.getAttribute(), Integer.valueOf("741"));
    }

    @Test
    public void test_random_natural_number() {
        String[] acceptedValuesForCorrectness = new String[10]; // No whitespace to compare attributes of the Symbol
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            acceptedValuesForCorrectness[i] = value;
        }

        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedValuesForCorrectness.length;

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        for (int i = 0; i < length_of_input; i++) {

            int random_length_natural_number = random.nextInt(4) + 1;
            StringBuilder natural_number_generated = new StringBuilder();
            for (int j = 0; j < random_length_natural_number; j++) {
                int index = random.nextInt(length_of_accepted_values);

                natural_number_generated.append(acceptedValuesForCorrectness[index]);

            }
            correct_output[i] = (String.valueOf(natural_number_generated));

            generated_input.append(correct_output[i]).append(" ");
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String expected_output : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertTrue(nextSymbol instanceof NaturalNumber);
            assertEquals(nextSymbol.getAttribute(), Integer.valueOf(expected_output));
        }
    }
}
