package compiler.Lexer;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;


public class Lexer {
    PushbackReader inputData;
    int END_OF_INPUT = 65535;
    char[] special_char_values = new char[]{'=', '+','-', '*', '/', '%','<','>','(', ')', '{','}','[', ']','.',';',','};
    String[] special_str_values = new String[]{"==","<>","<=",">="};
    String[] keyword_values = new String[]{"const", "record", "var", "val", "proc", "for", "to", "by", "while", "if", "else", "return", "and", "or"};
    String[] boolean_values = new String[]{"true", "false"};


    public Lexer(Reader input) {
        this.inputData=new PushbackReader(input,2);
    }
    
    public Symbol getNextSymbol()  {
        // init phase
        StringBuilder s = new StringBuilder();
        StringBuilder s_buffer;
        char c;
        char peek_c;
        // all is possible :)
        c = getNextChar();

        // ignore firsts whitespaces:
        while (c==' ' || c== '\n' || c == '\t'){
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
                peek_c = getNextChar();
                if (!(peek_c>='0' && peek_c<='9')){
                    // natural number
                    // give back the unconsumed char
                    unread_handled(peek_c);
                    unread_handled('.');
                    // todo here you discovered an int
                    System.out.println(s);
                    return new NaturalNumber(s.toString());
                }
                s.append(c);
                s.append(peek_c);
                c=getNextChar();
                while (c>='0' && c<='9'){
                    // take the char and get the next one
                    s.append(c);
                    c=getNextChar();
                }
                unread_handled(c);
                // todo here you discovered a float
                System.out.println(s);
                return new RealNumber(s.toString());

            } else {
                unread_handled(c);
                // todo here you discovered an int
                System.out.println(s);
                return new NaturalNumber(s.toString());
            }
        }

        // is it a special char or a comment ?
        if (isInList(c, special_char_values)){
            // take the char and get the next one
            s.append(c);
            c = getNextChar();
            // is it a special str ?
            s_buffer = new StringBuilder(s.toString());
            s_buffer.append(c);
            if ( s_buffer.toString().equals ( "//")){
                s.append(c);
                while (c != '\n' && c != END_OF_INPUT){
                    c = getNextChar();
                    s.append(c);
                }
                // todo here you discovered a comment
                System.out.println(s);
                // ignore comments
                return this.getNextSymbol();
            } else if (isInList(String.valueOf(s_buffer), special_str_values)) {
                s.append(c);
                // todo here you discovered a special str
                System.out.println(s);
                return new SpecialCharacter(s.toString());
            } else{
                // todo here you discovered a special char
                unread_handled(c);
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
            unread_handled(c);
            if (isInList(s.toString(), keyword_values)){ // must check if it is a keyword
                // todo here you discovered a keyword
                System.out.println(s);
                return new Keyword(s.toString());
            } else if (isInList(s.toString(), boolean_values)){ // must check if it is a boolean
                // todo here you discovered a boolean
                System.out.println(s);
                return new Boolean(s.toString());
            }else {
                // todo here you discovered a identifier
                System.out.println(s);
                return new Identifier(s.toString());
            }
        }

        // is it a String value ?
        if (c=='"'){
            c = getNextChar(); // ignore opening: "
            while (c != '"' ){
                // take the char and get the next one
                s.append(c);
                c=getNextChar();
            }
            // ignore closing: "
            // todo here you discovered a string
            System.out.println(s);
            return new Strings(s.toString());
        }

        // end of file ?
        if (c != END_OF_INPUT){
            // todo here you discovered an unrecognized char
            throw new Error("There is an unrecognized error !");
        }
        // yes end of file
        return null;
    }
    public char getNextChar(){
        try {
            //inputData.mark(1);
            char curr_char = (char) inputData.read();
            return curr_char;
        } catch (IOException e){
            return 0;
        }
    }

    public void unread_handled(char c){
        try {
            inputData.unread(c);
        } catch (IOException e){
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
