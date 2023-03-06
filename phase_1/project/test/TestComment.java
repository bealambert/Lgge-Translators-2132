import compiler.Lexer.*;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestComment {

    Random random = new Random();

    @Test
    public void test_recognize_comment() {
        String input = "// abc123 \n";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        Symbol nextSymbol = lexer.getNextSymbol();
        assertEquals(nextSymbol.getName(), Comment.class.getName());
        assertEquals(nextSymbol.getAttribute(), input.substring(0, input.length()));
    }

    @Test
    public void test_random_comment() {
        String[] acceptedValuesForCorrectness = new String[63]; // No whitespace to compare attributes of the Symbol
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            acceptedValuesForCorrectness[i] = value;
        }
        for (int i = 10; i < 36; i++) {
            int j = i + 55; // 65 - 10
            String value = String.valueOf((char) j);
            acceptedValuesForCorrectness[i] = value;
        }
        for (int i = 36; i < 62; i++) {
            int j = i + 61; // 97 - 36
            String value = String.valueOf((char) j);
            acceptedValuesForCorrectness[i] = value;
        }
        acceptedValuesForCorrectness[62] = "_";


        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedValuesForCorrectness.length;

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        generated_input.append("//");

        for (int i = 0; i < length_of_input; i++) {

            int random_length_comment = random.nextInt(20);
            StringBuilder comment_generated = new StringBuilder();
            comment_generated.append("//");


            for (int j = 0; j < random_length_comment; j++) {
                int index = random.nextInt(length_of_accepted_values);
                comment_generated.append(acceptedValuesForCorrectness[index]);
            }
            comment_generated.append("\n");
            correct_output[i] = (String.valueOf(comment_generated));
            generated_input.append(correct_output[i]);
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String expected_output : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertEquals(nextSymbol.getName(), Comment.class.getName());
            assertEquals(nextSymbol.getAttribute(), expected_output);
        }
    }

}
