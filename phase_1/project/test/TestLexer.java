import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestBoolean.class,
        TestIdentifier.class, TestKeyword.class, TestNaturalNumber.class,
        TestRealNumber.class, TestSpecialCharacter.class, TestStrings.class,
        TestNormal.class, TestThrowError.class
})
public class TestLexer {




}
