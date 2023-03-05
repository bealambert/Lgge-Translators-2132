package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;


public class Lexer {
    Reader inputData;
    int END_OF_INPUT = 65535;
    char[] special_char_values = new char[]{'=', '+','-', '*', '/', '%','<','>','(', ')', '{','}','[', ']','.',';',','};
    String[] special_str_values = new String[]{"==","<>","<=",">="};
    String[] keyword_values = new String[]{"const", "record", "var", "val", "proc", "for", "to", "by", "while", "if", "else", "return", "and", "or"};
    String[] boolean_values = new String[]{"true", "false"};


    public Lexer(Reader input) {
        this.inputData=input;
    }
    
    public Token getNextSymbol()  {
        // init phase
        StringBuilder s = new StringBuilder();
        StringBuilder s_buffer;
        char c;
        // all is possible :)
        c = getNextChar();

        // ignore firsts withespaces:
        while (c==' '){
            c=getNextChar();
        }

        // is it a numerical value ?
        if (c>='1' && c<='9'){ // 0 not included for the first digit
            while (c>='0' && c<='9'){
                // take the char and get the next one
                s.append(c);
                c=getNextChar();
            }
            if (c=='.'){
                s.append(c);
                c=getNextChar();
                while (c>='0' && c<='9'){
                    // take the char and get the next one
                    s.append(c);
                    c=getNextChar();
                }
                comeBack();
                // todo here you discovered a float
                System.out.println(s);
                return new RealNumber(s.toString());

            } else {
                comeBack();
                // todo here you discovered an int
                System.out.println(s);
                return new NaturalNumber(s.toString());
            }
        }

        // is it a special char or a comment ?
        if (isInList(c, special_char_values)){
            // take the char and get the next one
            s.append(c);
            c = peek();
            // is it a special str ?
            s_buffer = new StringBuilder(s.toString());
            s_buffer.append(c);
            if ( s_buffer.toString().equals ( "//")){
                while (c != '\n' && c != END_OF_INPUT){
                    c = getNextChar();
                    s.append(c);
                }
                // todo here you discovered a comment
                comeBack();
                System.out.println(s);
                return new Comment(s.toString());
            } else if (isInList(String.valueOf(s_buffer), special_str_values)) {
                getNextChar();
                s.append(c);
                // todo here you discovered a special str
                System.out.println(s);
                return new SpecialCharacter(s.toString());
            } else{
                // todo here you discovered a special char
                //comeBack();
                System.out.println(s);
                return new SpecialCharacter(s.toString());
            }
        }

        // is it a keyword or an identifier ?
        // start with a letter or an underscore
        if ((c>='a' && c<='z') || (c>='A' &&c<='Z') || c=='_'){
            //  candidate for an indentifier or a keyword or a boolean
            while((c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9') || c=='_'){
                s.append(c);
                c = getNextChar();
            }
            if (isInList(s.toString(), keyword_values)){ // must check if it is a keyword
                // todo here you discovered a keyword
                comeBack();
                System.out.println(s);
                return new Keyword(s.toString());
            } else if (isInList(s.toString(), boolean_values)){ // must check if it is a boolean
                // todo here you discovered a boolean
                comeBack();
                System.out.println(s);
                return new Boolean(s.toString());
            }else {
                // todo here you discovered a identifier
                comeBack();
                System.out.println(s);
                return new Identifier(s.toString());
            }


        }

        // is it a String value ?
        if (c=='"'){
            c = getNextChar(); // ignore opening: "
            while (c != '"'){
                // take the char and get the next one
                s.append(c);
                c=getNextChar();
            }
            c=getNextChar(); // ignore closing: "
            // todo here you discovered a string
            comeBack();
            System.out.println(s);
            return new Strings(s.toString());
        }

        return null;
    }
    public char getNextChar(){
        try {
            inputData.mark(1);
            char curr_char = (char) inputData.read();
            return curr_char;
        } catch (IOException e){
            return 0;
        }
    }
    public char peek(){
        char c = getNextChar();
        comeBack();
        return c;
    }
    public void comeBack(){
        try {
            inputData.reset();
            return ;
        } catch (IOException e){
            return ;
        }
    }
    public boolean isInList(char c, char[] reference){
        for (char reference_char : reference) {
            if (reference_char == c) {
                return true;
            }
        }
        return false;
    }
    public boolean isInList(String str, String[] reference){
        for (String reference_str : reference) {
            if (reference_str.equals(str) ){
                return true;
            }
        }
        return false;
    }
}
