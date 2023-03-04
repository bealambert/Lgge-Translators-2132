import compiler.Lexer.Boolean;
import compiler.Lexer.Symbol;
import org.junit.Test;

import java.io.StringReader;
import compiler.Lexer.Lexer;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestBoolean.class, TestNormal.class})
public class TestLexer {


}
