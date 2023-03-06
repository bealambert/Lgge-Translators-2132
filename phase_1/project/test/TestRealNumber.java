import compiler.Lexer.Lexer;
import compiler.Lexer.NaturalNumber;
import compiler.Lexer.RealNumber;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRealNumber {

    Random random = new Random();

    @Test
    public void test_recognize_real_number() {
        String input = "1.642 "; // whitespace otherwise it would be ambiguous (NaturalNumber vs RealNumber)
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertTrue(lexer.getNextSymbol() instanceof RealNumber);
    }

    @Test
    public void test_recognize_end_of_line() {
        String input = "\n812.23";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof RealNumber);
        assertEquals(nextSymbol.getAttribute(), Double.valueOf("812.23"));
    }

    @Test
    public void test_recognize_tabulation() {
        String input = "\t741.88";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertTrue(nextSymbol instanceof RealNumber);
        assertEquals(nextSymbol.getAttribute(), Double.valueOf("741.88"));
    }


    @Test
    public void test_random_real_number() {
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

            int random_length_real_number_int_part = random.nextInt(4) + 1;
            int random_length_real_number_decimal_part = random.nextInt(4) + 1;
            StringBuilder real_number_generated = new StringBuilder();
            for (int j = 0; j < random_length_real_number_int_part; j++) {
                int index = random.nextInt(length_of_accepted_values);
                real_number_generated.append(acceptedValuesForCorrectness[index]);

            }

            real_number_generated.append(".");
            for (int j = 0; j < random_length_real_number_decimal_part; j++) {
                int index = random.nextInt(length_of_accepted_values);
                real_number_generated.append(acceptedValuesForCorrectness[index]);
            }

            correct_output[i] = (String.valueOf(real_number_generated));
            generated_input.append(correct_output[i]).append(" ");
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String expected_output : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();

            assertTrue(nextSymbol instanceof RealNumber);
            assertEquals(nextSymbol.getAttribute(), Double.valueOf(expected_output));
        }
    }
}
