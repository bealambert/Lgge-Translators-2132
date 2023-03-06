import compiler.Lexer.*;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestIdentifier {

    Random random = new Random();

    @Test
    public void test_recognize_identifier() {
        String input = "my_identifier ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Identifier.class.getName());
        assertEquals(nextSymbol.getAttribute(), "my_identifier");
    }

    @Test
    public void test_random_identifier() {
        String[] acceptedValuesForCorrectness = new String[63]; // No whitespace to compare attributes of the Symbol
        String[] acceptedAlphabetAtFirstCharacter = new String[52];
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            acceptedValuesForCorrectness[i] = value;
        }
        for (int i = 10; i < 36; i++) {
            int j = i + 55; // 65 - 10
            String value = String.valueOf((char) j);
            acceptedValuesForCorrectness[i] = value;
            acceptedAlphabetAtFirstCharacter[i - 10] = value;
        }
        for (int i = 36; i < 62; i++) {
            int j = i + 61; // 97 - 36
            String value = String.valueOf((char) j);
            acceptedValuesForCorrectness[i] = value;
            acceptedAlphabetAtFirstCharacter[i - 36] = value;
        }
        acceptedValuesForCorrectness[62] = "_";


        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedValuesForCorrectness.length;

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        for (int i = 0; i < length_of_input; i++) {

            int random_first_character = random.nextInt(52);
            int random_length_identifier = random.nextInt(10);
            StringBuilder identifier_generated = new StringBuilder();
            identifier_generated.append(acceptedAlphabetAtFirstCharacter[random_first_character]);


            for (int j = 0; j < random_length_identifier; j++) {
                int index = random.nextInt(length_of_accepted_values);
                identifier_generated.append(acceptedValuesForCorrectness[index]);
            }
            correct_output[i] = (String.valueOf(identifier_generated));
            generated_input.append(correct_output[i]).append(" ");
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String expected_output : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertEquals(nextSymbol.getName(), Identifier.class.getName());
            assertEquals(nextSymbol.getAttribute(), expected_output);
        }
    }
}
