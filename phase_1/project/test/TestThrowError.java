import compiler.Lexer.Identifier;
import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class TestThrowError {

    Random random = new Random();

    ArrayList<String> refusedValues = new ArrayList<>();
    public TestThrowError(){

        refusedValues.add(String.valueOf((char) 33)); // !
        refusedValues.add(String.valueOf((char) 35)); // #
        refusedValues.add(String.valueOf((char) 36)); // $


        refusedValues.add(String.valueOf((char) 38)); // &
        refusedValues.add(String.valueOf((char) 39)); // '
        refusedValues.add(String.valueOf((char) 58)); // :
        refusedValues.add(String.valueOf((char) 63)); // ?
        refusedValues.add(String.valueOf((char) 64)); // 0
        refusedValues.add(String.valueOf((char) 94)); // :
        refusedValues.add(String.valueOf((char) 96)); // `
        refusedValues.add(String.valueOf((char) 124)); // |
        refusedValues.add(String.valueOf((char) 126)); // ~
    }

    @Test
    public void test_error_is_thrown_vertical_bar() {
        String input = " | my_identifier ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertThrows(Error.class, () -> {
            Symbol nextSymbol = lexer.getNextSymbol();
        ;});
    }

    @Test
    public void test_error_is_thrown_esperluette() {
        String input = " & my_identifier ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertThrows(Error.class, () -> {
            Symbol nextSymbol = lexer.getNextSymbol();
            ;});
    }

    @Test
    public void test_error_is_thrown_exclamation_mark() {
        String input = " ! my_identifier ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertThrows(Error.class, () -> {
            Symbol nextSymbol = lexer.getNextSymbol();
            ;});
    }

    @Test
    public void test_error_is_thrown_question_mark() {
        String input = " ? my_identifier ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertThrows(Error.class, () -> {
            Symbol nextSymbol = lexer.getNextSymbol();
            ;});
    }

    @Test
    public void test_error_is_thrown_power_of_two() {
        String input = " ² my_identifier ";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);

        assertThrows(Error.class, () -> {
            Symbol nextSymbol = lexer.getNextSymbol();
            ;});
    }

    @Test
    public void test_refused_ascii(){

        for (int i = 0; i < refusedValues.size(); i++) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(refusedValues.get(i));

            String input = String.valueOf(stringBuilder);
            StringReader reader = new StringReader(input);
            Lexer lexer = new Lexer(reader);

            assertThrows(Error.class, () -> {
                lexer.getNextSymbol();
                ;
            });
        }

    }

}
