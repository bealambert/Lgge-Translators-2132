package compiler.Lexer;
import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.io.Reader;


public class Lexer {
    Reader inputData;
    int WHITESPACE = 32;
    int END_OF_INPUT = 65535;
    int DEFAULT_VALUE = -1;
    int INTERGER_VALUE = 5;
    int FLOAT_VALUE = 6;

    public Lexer(Reader input) {
        this.inputData=input;
    }
    
    public Token getNextSymbol()  {
        String s = "";
        char c;
        int state = 0; // all is possible :)
        s="";
        c = getNextChar();
        if (c>='1' && c<='9'){
            state = INTERGER_VALUE; // is a candidate for an integer symbol
            while (state == INTERGER_VALUE){
                s+=c;
                c=getNextChar();
                if (!(c>='0' && c<='9')) {state=DEFAULT_VALUE;}
            }
            if (c=='.'){
                state = FLOAT_VALUE;
                while (state == FLOAT_VALUE){
                    s+=c;
                    c=getNextChar();
                    if (!(c>='0' && c<='9')) {state = DEFAULT_VALUE;}
                }
                if (c == WHITESPACE || c==END_OF_INPUT) {
                    // todo here you discovered a float
                    System.out.println(s);
                    return new RealNumber(s);
                }
            } else if (c == WHITESPACE || c==END_OF_INPUT) {
                // todo here you discovered an int
                System.out.println(s);
                return new NaturalNumber(s);
            }

        }


        return null;
    }
    public char getNextChar(){
        try {
            char curr_char = (char) inputData.read();
            return curr_char;
        } catch (IOException e){
            return 0;
        }
    }
}
