package compiler.Parser;

import compiler.*;
import compiler.Lexer.*;

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

    public static void main(String[] args) {
        //String input = "p.x;";
        String input = "proc copyPoints(p Point[]) Point {\n" +
                "     return Point(p[0].x+p[1].x, p[0].y+p[1].y)\n" +
                "}";
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

    public ASTNode getAST() {
        AST ast = new AST();
        // var a int = 2;
        while (lookahead != null) {
            if (isSymbol(Token.Semicolon)) {
                match(Token.Semicolon);
            }
            ast.add(parse());
        }
        return ast.getRoot();
    }

    public void parseValueHelper(ArrayList<Object> arrayList){
        if (isSymbol(Token.Identifier)) {
            Identifier identifier = (Identifier) match(Token.Identifier);
            // is it an identifier or a function call ?
            if (isSymbol(Token.OpeningParenthesis)) {
                FunctionCall functionCall = parseFunctionCall(identifier);
                arrayList.add(functionCall);
            } else if (isSymbol(Token.OpeningBracket)) {
                match(Token.OpeningBracket);
                Expression expression = parseExpression();
                match(Token.ClosingBracket);
                AccessToIndexArray accessToIndexArray = new AccessToIndexArray(identifier.getAttribute(), expression);
                if (isSymbol(Token.Point)) {
                    MethodCallFromIndexArray methodCallFromIndexArray = (MethodCallFromIndexArray) parseMethodCall(accessToIndexArray);
                    arrayList.add(methodCallFromIndexArray);
                } else {
                    arrayList.add(accessToIndexArray);
                }
            } else if (isSymbol(Token.Point)) {
                MethodCallFromIdentifier methodCallFromIdentifier = (MethodCallFromIdentifier) parseMethodCall(identifier);
                arrayList.add(methodCallFromIdentifier);
            } else {
                // here you ve found an identifier value
                arrayList.add(identifier);
            }
        }
        else if(isSymbol(Token.OpeningParenthesis)){
            pop();
            arrayList.add(parseExpression());
        }else {
            arrayList.add(parseValue());
        }
    }

    // todo Bea
    // here we stop when we meet the terminal symbol ";"
    public Expression parseExpression() {
        ArrayList<Object> arrayList = new ArrayList<>();
        // look for a first value
        parseValueHelper(arrayList);
        // look for an operator
        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null ) {
            arrayList.add(operatorSymbol);
            pop();
            parseValueHelper(arrayList);
            operatorSymbol = whichSymbol(operatorValues);
        }
        if (isSymbol(Token.ClosingParenthesis)){
            pop();
        }
        //System.out.println("---> Here is the discovered expression:\n    " + arrayList.toString());
        Expression expression = new Expression(arrayList);
        expression.buildTree();
        return expression;
    }


    public Expression parseParameterFunctionCall() {
        /***
         * Extends parseExpression with arrayInitializer -> int [] (a*2) is a valid parameter for a record
         */
        ArrayList<Object> arrayList = new ArrayList<>();

        if (isSymbol(Token.Identifier)) {
            Identifier currValue = (Identifier) match(Token.Identifier);
            // is it an identifier or a function call ?
            if (isSymbol(Token.OpeningParenthesis)) {

                FunctionCall functionCall = parseFunctionCall(currValue);
                arrayList.add(functionCall);
            } else if (isSymbol(Token.OpeningBracket)) {
                ArrayType arrayType = new ArrayType(currValue);
                match(Token.OpeningBracket);
                match(Token.ClosingBracket);
                match(Token.OpeningParenthesis);
                Expression expression = parseExpression();
                match(Token.ClosingParenthesis);

                ArrayInitializer arrayInitializer = new ArrayInitializer(arrayType, expression);
                arrayList.add(arrayInitializer);

            } else {
                // here you ve found an identifier value
                arrayList.add(currValue);
            }
        } else {
            arrayList.add(parseValue());
        }
        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null) {
            arrayList.add(operatorSymbol);
            pop();
            arrayList.add(parseExpression());
            operatorSymbol = whichSymbol(operatorValues);
        }
        //System.out.println("---> Here is the discovered expression:\n    " + arrayList.toString());
        return new Expression(arrayList);
    }

    public Param parseParam() {
        Identifier identifier = (Identifier) match(Token.Identifier);
        Type type = parseType();
        return new Param(type, identifier);
    }

    public ArrayList<Param> parseParameters() {
        // assumes we read the opening parenthesis "("
        ArrayList<Param> paramArrayList = new ArrayList<>();
        if (!isSymbol(Token.ClosingParenthesis)) {
            paramArrayList.add(parseParam());
            while (isSymbol(Token.Comma)) {
                match(Token.Comma);
                paramArrayList.add(parseParam());
            }
        }
        // TODO : do we have to match ")" ?
        return paramArrayList;
    }

    public Expression parseFunctionCallParameters() {
        // assumes we read the opening parenthesis "("
        ArrayList<Object> paramArrayList = new ArrayList<>();
        if (!lookahead.getAttribute().equals(Token.ClosingParenthesis)) {
            paramArrayList.add(parseParameterFunctionCall());
            while (isSymbol(Token.Comma)) {
                match(Token.Comma);
                paramArrayList.add(parseParameterFunctionCall());
            }
        }
        // TODO : do we have to match ")" ?
        return new Expression(paramArrayList);
    }

    public Block parseBlock() {
        ArrayList<ASTNode> symbolArrayList = new ArrayList<>();
        match(Token.OpeningCurlyBracket);

        while (!isSymbol(Token.ClosingCurlyBracket)) {
            symbolArrayList.add(parse());
            if (isSymbol(Token.Semicolon)) {
                match(Token.Semicolon);
            }
        }
        match(Token.ClosingCurlyBracket);
        return new Block(symbolArrayList);
    }

    public Type parseType() {
        Identifier type = (Identifier) match(Token.Identifier);
        if (isSymbol(Token.OpeningBracket)) {
            match(Token.OpeningBracket);
            match(Token.ClosingBracket);
            return new ArrayType(type);
        }
        return new Type(type);
    }

    /**
     * @param token
     * @return
     */
    public Symbol match(Token token) {
        // match of either class name or attribute
        if (isSymbol(token)) {
            Symbol match = lookahead;
            lookahead = lexer.getNextSymbol();
            return match;
        }
        throw new RuntimeException();
    }

    /**
     * It updates the lookahead with the next symbol and return the initial lookahead
     * @return the current lookahead
     */
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

    /**
     * @param tokenArray : an array of token
     * @return the lookahead Symbol if the lookahead is in the tokenArray null otherwise
     */
    public Symbol whichSymbol(Token[] tokenArray) {
        for (Token token : tokenArray) {
            if (isSymbol(token))
                return lookahead;
        }
        return null;
    }

    /**
     * @param token: the token to compare with the lookahead Symbol
     * @return true if they are the same kind (same attribute)
     */
    public boolean isSymbol(Token token) {
        return lookahead != null && (lookahead.getName().equals(token.getName()) || lookahead.getAttribute().equals(token.getName()));
    }

    public FunctionCall parseFunctionCall(Symbol identifier) {

        ArrayList<Expression> arrayList = new ArrayList<>();
        match(Token.OpeningParenthesis);
        while (!isSymbol(Token.ClosingParenthesis)) {
            arrayList.add(parseExpression());
            if (isSymbol(Token.Comma)) {
                match(Token.Comma);
            }
        }
        match(Token.ClosingParenthesis);
        return new FunctionCall(identifier, arrayList);
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

    public ArrayList<RecordParameter> parseRecordVariables() {
        ArrayList<RecordParameter> arrayList = new ArrayList<>();
        while (isSymbol(Token.Identifier)) {
            Identifier identifier = (Identifier) match(Token.Identifier);
            Type type = parseType();
            match(Token.Semicolon);
            arrayList.add(new RecordParameter(identifier, type));
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
            CreateVariables createVariables = (parseCreationStateVariable());
            match(Token.ToKeyword);
            Expression endExpression = parseExpression();
            match(Token.ByKeyword);
            Expression incrementByExpression = parseExpression();
            Block body = parseBlock();
            return new ForLoop(createVariables, endExpression, incrementByExpression, body);
        }
    }

    public ASTNode parse() {
        if (isSymbol(Token.Keyword)) {
            // check creation of variable : var a int = 2 ;
            // it is the only case
            if (isSymbol(Token.VarKeyword) || isSymbol(Token.ValKeyword) || isSymbol(Token.ConstKeyword)) {
                CreateVariables createVariables = parseCreationStateVariable();
                match(Token.Semicolon);
                return createVariables;
            }

            if (isSymbol(Token.RecordKeyword)) {
                Keyword keyword = (Keyword) match(Token.Keyword);
                Records record = new Records((String) match(Token.Identifier).getAttribute());
                match(Token.OpeningCurlyBracket);
                ArrayList<RecordParameter> recordVariable = parseRecordVariables();
                match(Token.ClosingCurlyBracket);
                return new InitializeRecords(keyword, record, recordVariable);

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
                // Empty function call
                return parseFunctionCall(identifier);
                // function call with parameters
            }
            if (isSymbol(Token.OpeningBracket)) {
                match(Token.OpeningBracket);
                Expression expression = parseExpression();
                match(Token.ClosingBracket);
                AccessToIndexArray accessToIndexArray = new AccessToIndexArray(identifier.getAttribute(), expression);
                if (isSymbol(Token.Point)) {
                    return parseMethodCall(accessToIndexArray);
                }

            }
            if (isSymbol(Token.Point)) {
                return parseMethodCall(identifier);
            }
        }


        return null;
    }

    public Condition parseCondition() {
        Expression condition = parseExpression();
        return new Condition(condition);
    }

    public AccessToIndexArray parseAccessToIndexArray(Identifier identifier) {
        match(Token.OpeningBracket);
        Expression expression = parseExpression();
        match(Token.ClosingBracket);
        AccessToIndexArray accessToIndexArray = new AccessToIndexArray(identifier.getAttribute(), expression);
        return accessToIndexArray;
    }

    public MethodCall parseMethodCall(Identifier identifier) {
        match(Token.Point);
        Identifier methodIdentifier = (Identifier) match(Token.Identifier);
        if (identifier.getName().equals(ClassName.AccessToIndexArray.getName())) {
            return new MethodCallFromIndexArray((AccessToIndexArray) identifier, methodIdentifier);
        }
        return new MethodCallFromIdentifier(identifier, methodIdentifier);
    }

    public WhileLoop parseWhileLoop() {
        match(Token.WhileKeyword);
        Condition condition = parseCondition();
        Block body = parseBlock();

        return new WhileLoop(condition, body);
    }

    public CreateVariables parseCreationStateVariable() {
        Keyword create_variable_identifier = parseStateVariable();

        Identifier identifier = (Identifier) match(Token.Identifier);
        Symbol symbol = whichSymbol(types);
        Type type = parseType();
        // can be either var x int; or var x int = 2;

        if (isSymbol(Token.Semicolon)) {
            return new CreateVoidVariable(create_variable_identifier, identifier, type);
        }
        match(Token.Assignment);


        if (isSymbol(Token.Identifier)) {
            Identifier referenceOrTypeIdentifier = (Identifier) match(Token.Identifier);
            if (isSymbol(Token.Semicolon)) {
                match(Token.Semicolon);
                return new CreateReferencedVariable(create_variable_identifier, identifier, type, referenceOrTypeIdentifier);
            }
            if (isSymbol(Token.OpeningBracket)) {
                match(Token.OpeningBracket);
                match(Token.ClosingBracket);
                ArrayType arrayType = new ArrayType(referenceOrTypeIdentifier);
                if (isSymbol(Token.OpeningParenthesis)) {
                    match(Token.OpeningParenthesis);
                    Expression arraySizeExpression = parseExpression();
                    match(Token.ClosingParenthesis);
                    ArrayInitializer arrayInitializer = new ArrayInitializer(arrayType, arraySizeExpression);
                    // TODO : cast to arrayType or rather if (...) throw new Exception()?
                    return new CreateArrayVariable(create_variable_identifier, identifier, (ArrayType) type, arrayInitializer);
                }
            }
            if (isSymbol(Token.OpeningParenthesis)) {
                // pointer is not set to the start of the expression

                if (symbol == null) {
                    match(Token.OpeningParenthesis);
                    Expression functionCallParameters = parseFunctionCallParameters();

                    match(Token.ClosingParenthesis);
                    RecordCall recordCall = new RecordCall(referenceOrTypeIdentifier.getAttribute(), functionCallParameters.getExpression());
                    CreateVariables expression = extendCreateExpressionVariable(create_variable_identifier, identifier, type);
                    if (expression != null) return expression;
                    return new CreateRecordVariables(create_variable_identifier, identifier, type, recordCall);
                } else {
                    FunctionCall functionCall = parseFunctionCall(referenceOrTypeIdentifier);
                    CreateVariables expression = extendCreateExpressionVariable(create_variable_identifier, identifier, type);
                    if (expression != null) return expression;
                    return new CreateExpressionVariable(create_variable_identifier, identifier, type, functionCall);
                }
            }
            CreateVariables expression = extendCreateExpressionVariable(create_variable_identifier, identifier, type);
            if (expression != null) return expression;

        }
        // TODO
        // if ArrayType
        if (type.getName().equals(ClassName.ArrayType.getName())) {
            //var c int[] = int[](5);  // new array of length 5
            // var d Person = Person("me", Point(3,7), int[](a*2));
        }
        Expression expression = parseExpression();
        return new CreateExpressionVariable(create_variable_identifier, identifier, type, expression);

    }

    private CreateVariables extendCreateExpressionVariable(Keyword create_variable_identifier, Identifier identifier, Type type) {
        Symbol operatorSymbol = whichSymbol(operatorValues);
        if (operatorSymbol != null) {
            pop();
            Expression expression = parseFunctionCallParameters();
            expression.getExpression().add(0, operatorSymbol);
            expression.getExpression().add(0, identifier);
            return new CreateExpressionVariable(create_variable_identifier, identifier, type, expression);
        }
        return null;
    }
}
