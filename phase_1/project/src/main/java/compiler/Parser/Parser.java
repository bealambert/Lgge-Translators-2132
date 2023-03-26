package compiler.Parser;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.*;
import compiler.Token;
import org.checkerframework.checker.units.qual.A;

import java.io.StringReader;
import java.util.*;

public class Parser {

    HashMap<Symbol, HashSet<Symbol>> follow_set;
    private final Lexer lexer;
    private Symbol lookahead;
    private final Token[] types = new Token[]{Token.IntIdentifier, Token.StringIdentifier, Token.BooleanIdentifier, Token.RealIdentifier};

    private final Token[] values = new Token[]{Token.Boolean, Token.NaturalNumber, Token.RealNumber, Token.Strings};
    private final Token[] operatorValues = new Token[]{
            Token.PlusOperator, Token.MinusOperator, Token.DivideOperator,
            Token.MultiplyOperator, Token.ModuloOperator, Token.IsDifferentOperator,
            Token.IsLessOperator, Token.IsEqualOperator, Token.IsGreaterOperator,
            Token.IsLessOrEqualOperator, Token.IsGreaterOrEqualOperator, Token.AndKeyword, Token.OrKeyword
    };
    private final Token[] mutable_immutable = new Token[]{Token.VarKeyword, Token.ValKeyword, Token.ConstKeyword};

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        lookahead = lexer.getNextSymbol();
        follow_set = new HashMap<>();
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


    /*public ArrayList<Symbol> parseFunctionCall(){
        match(Token.Identifier);
        match(Token.OpeningParenthesis);

        if (isSymbol(Token.Identifier)){
            // Either expression or f()
            match(Token.Identifier);
            Token operator = whichToken(operatorValues);
            if (operator != null){

            }
        }


    }*/
    public static void main(String[] args) {
        //String input = "var a int = 2;";
        // String input = "azea_8z = 3.54;";
        //String input = "f();";
        /*String input = "record Point{\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}";*/
        String input = "proc square(v int, a8aze_z real) int {\n" +
                "    return v*v;\n" +
                "}";
        //String input = "return v * v;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        System.out.println(root);
    }

    public Symbol parseValue() {
        if (isSymbol(Token.Boolean)) {
            return match(Token.Boolean);
        } else if (isSymbol(Token.Strings)) {
            return match(Token.Strings);
        } else if (isSymbol(Token.RealNumber)) {
            return match(Token.RealNumber);
        } else if (isSymbol(Token.NaturalNumber)) {
            return match(Token.NaturalNumber);
        }
        return match(Token.Identifier);

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

    public Expression parseExpression() {
        ArrayList<Symbol> arrayList = new ArrayList<>();
        arrayList.add(parseValue());
        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null) {
            arrayList.add(operatorSymbol);
            lookahead = lexer.getNextSymbol();
            arrayList.add(parseValue());
            operatorSymbol = whichSymbol(operatorValues);
        }

        return new Expression(arrayList);


    }

    public ArrayList<Param> parseParameters() {
        // assumes we read the opening parenthesis "("
        ArrayList<Param> paramArrayList = new ArrayList<>();
        if (!lookahead.getAttribute().equals(Token.ClosingParenthesis)) {
            paramArrayList.add(parseParam());
            while (isSymbol(Token.Comma)) {
                match(Token.Comma);
                paramArrayList.add(parseParam());
            }
        }
        // TODO : do we have to match ")" ?
        return paramArrayList;
    }

    public Block parseBlock() {
        ArrayList<ASTNode> symbolArrayList = new ArrayList<>();
        match(Token.OpeningCurlyBracket);

        while (!isSymbol(Token.ClosingCurlyBracket)) {
            symbolArrayList.add(parse());
        }
        match(Token.ClosingCurlyBracket);
        return new Block(symbolArrayList);
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
                Keyword keyword = (Keyword) match(Token.Keyword);
                Identifier identifier = (Identifier) match(Token.Identifier);
                SpecialCharacter openBracket = (SpecialCharacter) match(Token.OpeningCurlyBracket);
                ArrayList<RecordVariable> recordVariable = parseRecordVariables();
                SpecialCharacter closeBracket = (SpecialCharacter) match(Token.ClosingCurlyBracket);
                return new Record(keyword, identifier, openBracket, recordVariable, closeBracket);

            }

            if (isSymbol(Token.ProcKeyword)) {
                return parseCreateProcedure();
            }
            if (isSymbol(Token.ReturnKeyword)) {
                match(Token.ReturnKeyword);
                Expression expression = parseExpression();
                match(Token.Semicolon);
                return new ReturnStatement(expression);
            }

        }

        if (isSymbol(Token.Identifier)) {
            Identifier identifier = (Identifier) match(Token.Identifier);
            //     i = (i+2)*2
            if (isSymbol(Token.Assignment)) {
                match(Token.Assignment);
                Expression expression = parseExpression();
                match(Token.Semicolon);
                return new Reassignment(identifier, expression);
            }
            if (isSymbol(Token.OpeningParenthesis)) {
                // function call   f(....)
                // params are either :
                /** Expression : (i+1) * 2;  or function call writeln(square(value)); */
                match(Token.OpeningParenthesis);
                // Empty function call
                if (isSymbol(Token.ClosingParenthesis)) {
                    match(Token.ClosingParenthesis);
                    match(Token.Semicolon);
                    return new FunctionCall(identifier, new ArrayList<>());
                }

                // function call with parameters
            }
        }


        return null;
    }

    public Token whichToken(Token[] tokenArray) {
        for (Token token : tokenArray) {
            if (isSymbol(token))
                return token;
        }
        return null;
    }

    public ArrayList<RecordVariable> parseRecordVariables() {
        ArrayList<RecordVariable> arrayList = new ArrayList<>();
        while (isSymbol(Token.Identifier)) {
            Identifier identifier = (Identifier) match(Token.Identifier);
            Identifier type_identifier = (Identifier) match(whichToken(types));
            Type type = new Type(type_identifier.getAttribute());
            match(Token.Semicolon);
            arrayList.add(new RecordVariable(identifier, type));
        }
        return arrayList;
    }

    public CreateProcedure parseCreateProcedure() {
        match(Token.ProcKeyword);
        Identifier procedure_name = (Identifier) match(Token.Identifier);
        match(Token.OpeningParenthesis);
        ArrayList<Param> params = parseParameters();
        match(Token.ClosingParenthesis);
        Type returnType = parseType();
        // todo implement body extraction of procedure
        Block body = parseBlock();
        return new CreateProcedure(procedure_name, params, returnType, body);
    }
}
