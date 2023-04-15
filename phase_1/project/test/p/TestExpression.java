package p;

import compiler.ASTNode;
import compiler.Lexer.Lexer;
import compiler.Parser.Expression;
import compiler.Parser.Parser;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestExpression {
    @Test
    public void test_oneLevel1() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "a+1*3.2/true%func();";
        String outputExpected = "[<Identifier, a>, <SpecialCharacter, +>, <NaturalNumber, 1>, <SpecialCharacter, *>, <RealNumber, 3.2>, <SpecialCharacter, />, <Boolean, true>, <SpecialCharacter, %>, <Identifier, func>]";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        Expression result = parser.parseExpression();
        //System.out.println(result);

        assertTrue((result.toString()).equals(outputExpected));

    }

    @Test
    public void test_oneLevel2() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "readint();";
        String outputExpected = "[<FunctionCall: readint([]) >]";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        Expression result = parser.parseExpression();
        //System.out.println(result);

        assertTrue((result.toString()).equals(outputExpected));
    }

    @Test
    public void test_oneLevel3() {
        //String input = "var a int = 2;";
        //"fun(a,3)*2;"
        String input = "fun(a,3, true, 4.5)*2;";
        String outputExpected = "[<FunctionCall: fun([<Identifier, a>, <NaturalNumber, 3>, <Boolean, true>, <RealNumber, 4.5>]) >, <SpecialCharacter, *>, <NaturalNumber, 2>]";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        Expression result = parser.parseExpression();
        //System.out.println(result);

        assertTrue((result.toString()).equals(outputExpected));
    }
}
