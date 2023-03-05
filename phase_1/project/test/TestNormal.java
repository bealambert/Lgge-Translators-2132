import compiler.Lexer.*;
import compiler.Lexer.Boolean;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class TestNormal {

    @Test
    public void test_not_null() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test_basic() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Symbol[] symbolClasses = new Symbol[]{
                new Keyword("var"), new Identifier("x"),
                new Identifier("int"), new Keyword("="),
                new NaturalNumber("2")
        };
        Symbol nextSymbol = lexer.getNextSymbol();
        for (int i = 0; i < symbolClasses.length; i++) {
            assertEquals(nextSymbol.getName(), symbolClasses[i].getName());
            assertEquals(nextSymbol.getAttribute(), symbolClasses[i].getAttribute());
        }
    }

    @Test
    public void test_void() {
        String input = "";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertNull(lexer.getNextSymbol());
    }

    @Test
    public void test_basic_modified() {
        String input = "val y double = 24.741;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Symbol[] symbolClasses = new Symbol[]{
                new Keyword("val"), new Identifier("y"),
                new Identifier("double"), new Keyword("="),
                new RealNumber("24.741")
        };
        Symbol nextSymbol = lexer.getNextSymbol();
        for (int i = 0; i < symbolClasses.length; i++) {
            assertEquals(nextSymbol.getName(), symbolClasses[i].getName());
            assertEquals(nextSymbol.getAttribute(), symbolClasses[i].getAttribute());
        }
    }

    @Test
    public void test_random() {
        Random random = new Random();

        Boolean my_boolean = new Boolean("true");
        Keyword my_keyword = new Keyword("else");
        SpecialCharacter my_special_character = new SpecialCharacter(";");

        String[] acceptedValues = new String[63]; // No whitespace to compare attributes of the Symbol
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            acceptedValues[i] = value;
        }
        for (int i = 10; i < 36; i++) {
            int j = i + 55; // 65 - 10
            String value = String.valueOf((char) j);
            acceptedValues[i] = value;
        }
        for (int i = 36; i < 62; i++) {
            int j = i + 61; // 97 - 36
            String value = String.valueOf((char) j);
            acceptedValues[i] = value;
        }
        acceptedValues[62] = "_";

        ArrayList<String> acceptedSymbolStrings = new ArrayList<>(Arrays.asList(my_boolean.getAcceptedAttributes()));
        acceptedSymbolStrings.addAll(Arrays.asList(my_keyword.getAcceptedAttributes()));
        acceptedSymbolStrings.addAll(Arrays.asList(my_special_character.getAcceptedAttributes()));
        acceptedSymbolStrings.add("GENERATE_STRING");
        acceptedSymbolStrings.add("GENERATE_NATURAL_NUMBER");
        acceptedSymbolStrings.add("GENERATE_REAL_NUMBER");

        int booleanEndIndex = 1;
        int keywordEndIndex = (my_keyword.getAcceptedAttributes().length) + 1;
        int specialEndIndex = keywordEndIndex + my_special_character.getAcceptedAttributes().length;

        int length_of_input = random.nextInt(1000);
        int length_of_accepted_values = acceptedSymbolStrings.size();

        StringBuilder generated_input = new StringBuilder();
        Symbol[] correct_output = new Symbol[length_of_input];

        for (int i = 0; i < length_of_input; i++) {
            int index = random.nextInt(length_of_accepted_values);
            String randomValue = acceptedSymbolStrings.get(index);
            switch (randomValue) {
                case "GENERATE_STRING":
                    int random_length_strings = random.nextInt(20);
                    StringBuilder strings_generated = new StringBuilder();
                    strings_generated.append("\"");

                    for (int j = 0; j < random_length_strings; j++) {
                        int index_of_character = random.nextInt(length_of_accepted_values);
                        strings_generated.append(acceptedValues[index_of_character]);
                    }
                    strings_generated.append("\"");
                    String generated_string = String.valueOf(strings_generated);
                    correct_output[i] = new Strings(generated_string);
                    generated_input.append(correct_output[i]).append(" ");
                    break;
                case "GENERATE_NATURAL_NUMBER":
                    int random_length_natural_number = random.nextInt(5);
                    StringBuilder natural_number_generated = new StringBuilder();

                    for (int j = 0; j < random_length_natural_number; j++) {
                        int index_of_numbers = random.nextInt(10);
                        natural_number_generated.append(acceptedValues[index_of_numbers]);
                    }
                    String generated_natural_number = String.valueOf(natural_number_generated);
                    correct_output[i] = new NaturalNumber(generated_natural_number);
                    generated_input.append(generated_natural_number).append(" ");
                    break;

                case "GENERATE_REAL_NUMBER":
                    int random_length_real_number_int_part = random.nextInt(5);
                    int random_length_real_number_decimal_part = random.nextInt(5);
                    StringBuilder real_number_generated = new StringBuilder();
                    for (int j = 0; j < random_length_real_number_int_part; j++) {
                        int index_of_numbers = random.nextInt(length_of_accepted_values);
                        real_number_generated.append(acceptedValues[index_of_numbers]);
                    }

                    real_number_generated.append(".");
                    for (int j = 0; j < random_length_real_number_decimal_part; j++) {
                        int index_of_numbers = random.nextInt(length_of_accepted_values);
                        real_number_generated.append(acceptedValues[index_of_numbers]);
                    }

                    String generated_real_number = String.valueOf(real_number_generated);
                    correct_output[i] = new RealNumber(generated_real_number);
                    generated_input.append(generated_real_number).append(" ");
                    break;
                default:
                    Symbol to_add;
                    if (index <= booleanEndIndex) {
                        to_add = new Boolean(randomValue);
                    } else if (index <= keywordEndIndex) {
                        to_add = new Keyword(randomValue);
                    } else {
                        to_add = new SpecialCharacter(randomValue);
                    }
                    correct_output[i] = to_add;
                    generated_input.append(randomValue).append(" ");
            }
        }
        String input = generated_input.toString();
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        for (Symbol s : correct_output) {
            Symbol nextSymbol = lexer.getNextSymbol();
            assertEquals(nextSymbol.getName(), s.getName()); // can replace it with instance of
            assertEquals(nextSymbol.getAttribute(), s.getAttribute());
        }
    }

}
