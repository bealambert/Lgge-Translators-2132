package compiler.Parser;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.Boolean;
import compiler.Lexer.Identifier;
import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Token;

import java.io.PushbackReader;
import java.io.Reader;
import java.util.*;

public class Parser {

    HashMap<Symbol, HashSet<Symbol>> follow_set;
    private Lexer lexer;
    private Symbol lookahead;
    private Token[] types = new Token[]{Token.IntIdentifier, Token.Strings, Token.BooleanIdentifier, Token.RealIdentifier};

    private Token[] values = new Token[]{Token.Boolean, Token.NaturalNumber, Token.RealNumber, Token.Strings};
    private String[] mutable_immutable = new String[]{"var", "val", "const"};

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        lookahead = lexer.getNextSymbol();
        follow_set = new HashMap<>();
    }

    public static void main(String[] args) {
        System.out.println(Token.Boolean);
        Boolean my_boolean = new Boolean("True");
    }

    public Object getAST() {
        AST ast = new AST();
        // var a int = 2;
        while (getNextASTNode() != null) {
            ast.add(getNextASTNode());
        }
        return ast.getRoot();
    }

    public ASTNode getNextASTNode() {
        // const i int = 3;
        // keywords
        ArrayList<Symbol> symbolArrayList = new ArrayList<>();
        while (lookahead != null && lookahead.getAttribute() != ";") {
            symbolArrayList.add(lookahead);
            lookahead = lexer.getNextSymbol();
        }
        if (lookahead != null) {
            lookahead = lexer.getNextSymbol();
        }

        return parse(symbolArrayList);
    }

    public ASTNode parse(ArrayList<Symbol> symbolArrayList) {
        for (int i = 0; i < symbolArrayList.size() - 2; i++) {
            Symbol current = symbolArrayList.get(i);

        }
        return null;
    }

    public Type parseType() {
        Symbol type = match(Token.Identifier);
        return new Type(String.valueOf(type.getAttribute()));
    }

    public Param parseParam() {
        Type type = parseType();
        Identifier identifier = (Identifier) match(Token.Identifier);
        return new Param(type, identifier);
    }

    public ArrayList<Param> parseParameters() {
        // assumes we read the opening parenthesis "("
        ArrayList<Param> paramArrayList = new ArrayList<>();
        if (!lookahead.getAttribute().equals(Token.ClosingParenthesis)) {
            paramArrayList.add(parseParam());
            while (lookahead.getAttribute().equals(Token.Comma)) {
                match(Token.Comma);
                paramArrayList.add(parseParam());
            }
        }
        // TODO : do we have to match ")" ?
        return paramArrayList;
    }

    public Block parseBlock() {
        if (lookahead.getAttribute().equals(Token.OpeningCurlyBracket)) {
            match(Token.OpeningCurlyBracket);
            ArrayList<Symbol> symbolArrayList = new ArrayList<>();
            int openingMinusClosingCount = 1;
            while (openingMinusClosingCount > 0) {

                symbolArrayList.add(lookahead);
                if (lookahead.getAttribute().equals(Token.OpeningCurlyBracket)) {
                    openingMinusClosingCount++;
                } else if (lookahead.getAttribute().equals(Token.ClosingCurlyBracket)) {
                    openingMinusClosingCount--;
                }

                lookahead = lexer.getNextSymbol();

            }
            symbolArrayList.remove(symbolArrayList.size() - 1);
            return new Block(symbolArrayList);
        }
        return new Block(new ArrayList<>());
    }

    public Symbol match(Token token) {
        // match of either class name or attribute
        if (lookahead.getName().equals(token.getName()) || lookahead.getAttribute().equals(token.getName())) {
            Symbol match = lookahead;
            lookahead = lexer.getNextSymbol();
            return match;
        }
        throw new RuntimeException();
    }


}
