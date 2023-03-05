import compiler.Lexer.Boolean;
import compiler.Lexer.Constant;
import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestConstant {

    Random random = new Random();

    @Test
    public void test_recognize_constant() {
        String input = "val";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertEquals(lexer.getNextSymbol().getName(), Constant.class.getName());
    }

    @Test
    public void test_random_constant() {
        String acceptedValuesForStringGeneration = "val "; // whitespaces are important for the lexer
        String acceptedValuesForCorrectness = "val"; // No whitespace to compare attributes of the Symbol

        int length_of_input = random.nextInt(1000);

        StringBuilder generated_input = new StringBuilder();
        String[] correct_output = new String[length_of_input];

        for (int i = 0; i < length_of_input; i++) {

            generated_input.append(acceptedValuesForStringGeneration);
            correct_output[i] = acceptedValuesForCorrectness;
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (String s : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertEquals(nextSymbol.getName(), Constant.class.getName());
        }
    }
}
