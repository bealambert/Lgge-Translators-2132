package compiler.Parser;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.*;
import compiler.Lexer.Boolean;
import compiler.Token;
import org.checkerframework.checker.units.qual.A;

import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class Parser {

    HashMap<Symbol, HashSet<Symbol>> follow_set;
    private Lexer lexer;
    private Symbol lookahead;
    private Token[] types = new Token[]{Token.IntIdentifier, Token.Strings, Token.BooleanIdentifier, Token.RealIdentifier};

    private Token[] values = new Token[]{Token.Boolean, Token.NaturalNumber, Token.RealNumber, Token.Strings};
    private Token[] operatorValues = new Token[]{
            Token.PlusOperator, Token.MinusOperator, Token.DivideOperator,
            Token.MultiplyOperator, Token.ModuloOperator, Token.IsDifferentOperator,
            Token.IsLessOperator, Token.IsEqualOperator, Token.IsGreaterOperator,
            Token.IsLessOrEqualOperator, Token.IsGreaterOrEqualOperator
    };
    private String[] mutable_immutable = new String[]{"var", "val", "const"};

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        lookahead = lexer.getNextSymbol();
        follow_set = new HashMap<>();
    }

    public static void main(String[] args) {
        String input = "var a int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        System.out.println(root);
    }

    public ASTNode getAST() {
        AST ast = new AST();
        // var a int = 2;
        while (lookahead != null) {
            ast.add(parse());
        }
        return ast.getRoot();
    }

    public ASTNode getNextASTNode() {
        // const i int = 3;
        // keywords
        AST ast = new AST();
        /*while(lookahead != null) {
            parse();
        }*/
        parse();
        return ast.getRoot();
    }

    public ASTNode parse() {
        if (isSymbol(Token.Keyword)) {
            // check creation of variable : var a int = 2 ;
            // it is the only case
            if (isSymbol(Token.VarKeyword) || isSymbol(Token.ValKeyword) || isSymbol(Token.ConstKeyword)) {
                Keyword create_variable_identifier = parseCreationStateVariable();
                Identifier identifier = (Identifier) match(Token.Identifier);
                Type type = parseType();
                AssignmentOperator assignmentOperator = new AssignmentOperator(match(Token.Assignment));
                Expression expression = parseExpression();
                match(Token.Semicolon);
                return new CreateVariable(create_variable_identifier, identifier, type, assignmentOperator, expression);
            }

            if (isSymbol(Token.RecordKeyword)) {

            }
        }
        return null;
    }

    public Symbol parseValue() {
        if (isSymbol(Token.Boolean)) {
            return match(Token.Boolean);
        } else if (isSymbol(Token.Strings)) {
            return match(Token.Strings);
        } else if (isSymbol(Token.RealNumber)) {
            return match(Token.RealNumber);
        }
        return match(Token.NaturalNumber);

    }

    public Expression parseExpression() {
        ArrayList<Symbol> arrayList = new ArrayList<>();
        arrayList.add(parseValue());
        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null) {
            arrayList.add(operatorSymbol);
            arrayList.add(parseValue());
            operatorSymbol = whichSymbol(operatorValues);
        }

        return new Expression(arrayList);


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

    public Keyword parseCreationStateVariable() {
        Keyword create_variable_identifier;
        if (isSymbol(Token.VarKeyword)) {
            create_variable_identifier = (Keyword) match(Token.VarKeyword);
        } else if (isSymbol(Token.ValKeyword)) {
            create_variable_identifier = (Keyword) match(Token.ValKeyword);
        } else {
            create_variable_identifier = (Keyword) match(Token.ConstKeyword);
        }
        return create_variable_identifier;
    }

    public boolean isSymbol(Token token) {
        return (lookahead.getName().equals(token.getName()) || lookahead.getAttribute().equals(token.getName()));
    }

    public Symbol whichSymbol(Token[] tokenArray) {
        for (Token token : tokenArray) {
            if (isSymbol(token))
                return lookahead;
        }
        return null;
    }


}
