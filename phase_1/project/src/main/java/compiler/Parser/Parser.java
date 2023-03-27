package compiler.Parser;

import compiler.AST;
import compiler.ASTNode;
import compiler.Lexer.*;
import compiler.Token;
import java.io.StringReader;
import java.util.*;

public class Parser {

    HashMap<Symbol, HashSet<Symbol>> follow_set;
    private final Lexer lexer;
    private Symbol lookahead;
    private final Token[] types = new Token[]{Token.IntIdentifier, Token.StringIdentifier, Token.BooleanIdentifier, Token.RealIdentifier};

    private final Token[] returnTypes = new Token[]{Token.IntIdentifier, Token.StringIdentifier, Token.BooleanIdentifier, Token.RealIdentifier, Token.VoidIdentifier};
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

    public static void main(String[] args) {
        //String input = "var a int = 2;";
        // String input = "azea_8z = 3.54;";
        //String input = "f();";
        /*String input = "record Point{\n" +
                "    x int;\n" +
                "    y int;\n" +
                "}";*/
        /*String input = "proc square(v int, a8aze_z real) int {\n" +
                "    return v*v;\n" +
                "}";*/
        //String input = "return v * v;";
        /*String input = "for var i int =2 to 100 by 1 {" +
                "return v*v;}";*/
        //String input = "if value <> 3 {return x*x;} else{return 2*3;}";
        //String input = "var c int[] = int[](5);";
        String input = "var a int = fun(a,3)*2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ASTNode root = parser.getAST();
        boolean flag = false;
        while (!flag) {
            System.out.println(root);
            root = root.getNext();
            flag = root == null;
        }
    }


    // todo Bea
    // var a int = 2;
    // this method is called after the match() of "=" Symbol
    // so the look ahead points to "NaturalNumber, 2" here
    public Expression parseExpression() {
        ArrayList<Object> arrayList = new ArrayList<>();

        if (isSymbol(Token.Identifier)){
            Symbol currValue = parseValue();
            // is it an identifier or a function call ?
            if (isSymbol(Token.OpeningParenthesis)){
                pop();
                arrayList.add(parseFunctionCall(currValue));
            } else{
                // here you ve found an identifier value
                arrayList.add(currValue);
            }
        }
        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null) {
            arrayList.add(operatorSymbol);
            arrayList.add(parseValue());
            operatorSymbol = whichSymbol(operatorValues);
        }
        System.out.println("---> Here is the discovered expression:\n    " + arrayList.toString());
        return new Expression(arrayList);
    }
    public Object parseExpressionBackup() {
        ArrayList<Object> arrayList = new ArrayList<>();
        // is it a value candidate or a parenthesis ???
        if (isSymbol(Token.OpeningParenthesis)){
            // here is a sub expression
            pop(); // no need to match as we do not use the "(" symbol
            arrayList.add(parseExpression());
        } else {
            // if it is not a '(' then it must be a value:
            arrayList.add(parseValue()); // match is included, so lookahead is updated
        }
        // here we must have empty or Operation
        if (whichSymbol(operatorValues) != null){
            arrayList.add(pop());
            arrayList.add(parseExpression());
        }

        System.out.println("---> Here is the discovered expression:\n    " + arrayList.toString());
        if (arrayList.size()==1){
            return arrayList.get(0);
        }
        return new Expression(arrayList);
    }

    public FunctionCall parseFunctionCall(Symbol identifier){
        ArrayList<Object> arrayList = new ArrayList<>();
        while(!isSymbol(Token.ClosingParenthesis)){
            if (isSymbol(Token.Comma)){
                pop();
            }
            arrayList.add(parseValue());
        }
        pop();
        return new FunctionCall(identifier, arrayList);
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

    public Type parseType() {
        Symbol type = match(Token.Identifier);
        boolean isArray = false;
        if (isSymbol(Token.OpeningBracket)) {
            match(Token.OpeningBracket);
            match(Token.ClosingBracket);
            isArray = true;
        }
        return new Type(String.valueOf(type.getAttribute()), isArray);
    }

    public Symbol match(Token token) {
        // match of either class name or attribute
        if (isSymbol(token)) {
            Symbol match = lookahead;
            lookahead = lexer.getNextSymbol();
            return match;
        }
        throw new RuntimeException();
    }
    public Symbol pop() {
        Symbol match = lookahead;
        lookahead = lexer.getNextSymbol();
        return match;

    }

    public Keyword parseStateVariable() {
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

    public Symbol whichSymbol(Token[] tokenArray) {
        for (Token token : tokenArray) {
            if (isSymbol(token))
                return lookahead;
        }
        return null;
    }

    public boolean isSymbol(Token token) {

        return lookahead != null && (lookahead.getName().equals(token.getName()) || lookahead.getAttribute().equals(token.getName()));
    }


    public ASTNode parse() {
        if (isSymbol(Token.Keyword)) {
            // check creation of variable : var a int = 2 ;
            // it is the only case
            if (isSymbol(Token.VarKeyword) || isSymbol(Token.ValKeyword) || isSymbol(Token.ConstKeyword)) {
                CreateVariable createVariable = parseCreationStateVariable();
                match(Token.Semicolon);
                return createVariable;
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
            if (isSymbol(Token.ForKeyword)) {
                return parseForLoop();
            }

            if (isSymbol(Token.WhileKeyword)) {
                return parseWhileLoop();
            }
            if (isSymbol(Token.IfKeyword)) {
                match(Token.IfKeyword);
                Condition condition = parseCondition();
                Block ifBlock = parseBlock();
                if (isSymbol(Token.ElseKeyword)) {
                    match(Token.ElseKeyword);
                    Block elseBlock = parseBlock();
                    return new IfElse(condition, ifBlock, elseBlock);
                }
                return new IfCondition(condition, ifBlock);
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

    public CreateProcedure parseCreateProcedure() {
        match(Token.ProcKeyword);
        Identifier procedure_name = (Identifier) match(Token.Identifier);
        match(Token.OpeningParenthesis);
        ArrayList<Param> params = parseParameters();
        match(Token.ClosingParenthesis);
        Type returnType = parseType();
        Block body = parseBlock();
        return new CreateProcedure(procedure_name, params, returnType, body);
    }

    public ArrayList<RecordVariable> parseRecordVariables() {
        ArrayList<RecordVariable> arrayList = new ArrayList<>();
        while (isSymbol(Token.Identifier)) {
            Identifier identifier = (Identifier) match(Token.Identifier);
            Type type = parseType();
            match(Token.Semicolon);
            arrayList.add(new RecordVariable(identifier, type));
        }
        return arrayList;
    }

    public ForLoop parseForLoop() {
        match(Token.ForKeyword);
        // for i=1 to 100 by 2
        // either var int i = 2; or i=2 with declaration of variable before
        if (isSymbol(Token.Identifier)) {
            Identifier identifier = (Identifier) match(Token.Identifier);
            match(Token.Assignment);
            Expression startExpression = parseExpression();
            match(Token.ToKeyword);
            Expression endExpression = parseExpression();
            match(Token.ByKeyword);
            Expression incrementByExpression = parseExpression();
            Block body = parseBlock();
            return new ForLoop(identifier, startExpression, endExpression, incrementByExpression, body);
        } else {
            CreateVariable createVariable = (parseCreationStateVariable());
            match(Token.ToKeyword);
            Expression endExpression = parseExpression();
            match(Token.ByKeyword);
            Expression incrementByExpression = parseExpression();
            Block body = parseBlock();
            return new ForLoop(createVariable, endExpression, incrementByExpression, body);
        }
    }

    public CreateVariable parseCreationStateVariable() {
        Keyword create_variable_identifier = parseStateVariable();
        Identifier identifier = (Identifier) match(Token.Identifier);
        Type type = parseType();

        // can be either var x int; or var x int = 2;
        if (isSymbol(Token.Semicolon)) {
            return new CreateVariable(create_variable_identifier, identifier, type);
        }
        AssignmentOperator assignmentOperator = new AssignmentOperator(match(Token.Assignment));
        if (type.isArray) {
            //var c int[] = int[](5);  // new array of length 5
            // var d Person = Person("me", Point(3,7), int[](a*2));
            if (isSymbol(Token.Identifier)) {
                Type typeOrFunctionIdentifier = parseType();
                if (isSymbol(Token.OpeningBracket)) {
                    match(Token.OpeningBracket);
                    match(Token.ClosingBracket);
                    match(Token.OpeningParenthesis);
                    Expression arraySizeExpression = parseExpression();
                    match(Token.ClosingParenthesis);
                    ArrayInitializer arrayInitializer = new ArrayInitializer(typeOrFunctionIdentifier, arraySizeExpression);
                    return new CreateVariable(create_variable_identifier, identifier, type, arrayInitializer);
                }
            }
        }
        Expression expression = parseExpression();
        return new CreateVariable(create_variable_identifier, identifier, type, expression);
    }

    public Condition parseCondition() {
        Expression condition = parseExpression();
        return new Condition(condition);
    }

    public WhileLoop parseWhileLoop() {
        match(Token.WhileKeyword);
        Condition condition = parseCondition();
        Block body = parseBlock();

        return new WhileLoop(condition, body);
    }
}
