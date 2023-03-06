import compiler.Lexer.Boolean;
import compiler.Lexer.Symbol;
import static org.junit.Assert.assertNotNull;

import compiler.Lexer.Token;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestBoolean.class,
        TestIdentifier.class, TestKeyword.class, TestNaturalNumber.class,
        TestRealNumber.class, TestSpecialCharacter.class, TestStrings.class,
        TestNormal.class, TestThrowError.class
})
public class TestLexer {




}
