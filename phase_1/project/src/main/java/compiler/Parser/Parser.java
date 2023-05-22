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

    public Expression parseExpression() {
        if (isSymbol(Token.Identifier)) {

            Identifier identifier = (Identifier) match(Token.Identifier);
            // is it an identifier or a function call ?
            if (isSymbol(Token.OpeningParenthesis)) {
                FunctionCall functionCall = parseFunctionCall(identifier);

                return functionCall;
            } else if (isSymbol(Token.OpeningBracket)) {
                match(Token.OpeningBracket);
                ArrayOfExpression expressions = parseArrayOfExpression();
                match(Token.ClosingBracket);
                AccessToIndexArray accessToIndexArray = new AccessToIndexArray(identifier, expressions);
                if (isSymbol(Token.Point)) {
                    MethodCallFromIndexArray methodCallFromIndexArray = (MethodCallFromIndexArray) parseMethodCall(accessToIndexArray);

                    return methodCallFromIndexArray;
                } else {

                    return accessToIndexArray;
                }

            } else if (isSymbol(Token.Point)) {
                MethodCallFromIdentifier methodCallFromIdentifier = (MethodCallFromIdentifier) parseMethodCall(identifier);
                return methodCallFromIdentifier;
            } else {
                // here you ve found an identifier value
                return new Variable(identifier);
            }
        }
        Symbol symbol = parseValue();

        return new Values(symbol);
    }

    // var a int = 2;
    // this method is called after the match() of "=" Symbol
    // so the look ahead points to "NaturalNumber, 2" here
    public ArrayOfExpression parseArrayOfExpression() {
        ArrayList<Expression> arrayList = new ArrayList<>();

        if (isSymbol(Token.MinusOperator)){
            pop();
            arrayList.add(new NegativeValue(parseExpression()));
        } else if (isSymbol(Token.OpeningParenthesis)){
            pop();
            arrayList.add(parseSubExpression());
        } else{
            arrayList.add(parseExpression());
        }

        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null) {
            arrayList.add(parseOperator((String) operatorSymbol.getAttribute()));
            pop();
            /*
            // to add if we want to allow "3+-4"
            if (isSymbol(Token.MinusOperator)){
                pop();
                arrayList.add(new NegativeValue(parseExpression()));
            } else
            */
            if (isSymbol(Token.OpeningParenthesis)){
                pop();
                arrayList.add(parseSubExpression());
            } else{
                arrayList.add(parseExpression());
            }

            operatorSymbol = whichSymbol(operatorValues);
        }

        //System.out.println("---> Here is the discovered expression:\n    " + arrayList.toString());
        return new ArrayOfExpression(arrayList);
    }

    public SubExpression parseSubExpression() {
        ArrayList<Expression> arrayList = new ArrayList<>();
        if (isSymbol(Token.MinusOperator)){
            pop();
            arrayList.add(new NegativeValue(parseExpression()));
        } else if (isSymbol(Token.OpeningParenthesis)){
            pop();
            arrayList.add(parseSubExpression());
        } else{
            arrayList.add(parseExpression());
        }

        Symbol operatorSymbol = whichSymbol(operatorValues);

        while (operatorSymbol != null) {
            arrayList.add(parseOperator((String) operatorSymbol.getAttribute()));
            pop();
            if (isSymbol(Token.OpeningParenthesis)){
                pop();
                arrayList.add(parseSubExpression());
            } else{
                arrayList.add(parseExpression());
            }
            if (isSymbol(Token.ClosingParenthesis)){
                pop();
                return new SubExpression(new ArrayOfExpression(arrayList));
            }
            operatorSymbol = whichSymbol(operatorValues);
        }

        //System.out.println("---> Here is the discovered expression:\n    " + arrayList.toString());
        return new SubExpression(new ArrayOfExpression(arrayList));
    }


    public FunctionCallParameter parseParameterFunctionCall() {
        /***
         * Extends parseExpression with arrayInitializer -> int [] (a*2) is a valid parameter for a record
         */
        if (isSymbol(Token.Identifier)) {
            Identifier currValue = (Identifier) match(Token.Identifier);
            // is it an identifier or a function call ?
            if (isSymbol(Token.OpeningParenthesis)) {

                FunctionCall functionCall = parseFunctionCall(currValue);
                ExpressionParameter expressionParameter = new ExpressionParameter(functionCall);
                return expressionParameter;
            } else if (isSymbol(Token.OpeningBracket)) {
                ArrayType arrayType = new ArrayType(currValue);
                match(Token.OpeningBracket);
                match(Token.ClosingBracket);
                match(Token.OpeningParenthesis);
                ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
                match(Token.ClosingParenthesis);

                ArrayInitializer arrayInitializer = new ArrayInitializer(arrayType, arrayOfExpression);
                ArrayInitializerParameter arrayInitializerParameter = new ArrayInitializerParameter(arrayInitializer);
                return arrayInitializerParameter;

            } else {
                // here you ve found an identifier value
                ExpressionParameter expressionParameter = new ExpressionParameter(new Variable(currValue));
                return expressionParameter;
            }
        }
        Symbol symbol = parseValue();
        ExpressionParameter expressionParameter = new ExpressionParameter(new Values(symbol));
        return expressionParameter;

    }

    public Operator parseOperator(String attribute) {
        switch (attribute) {
            case "+":
                return new OperatorAdd();
            case "-":
                return new OperatorMinus();
            case "*":
                return new OperatorMultiply();
            case "/":
                return new OperatorDivide();
            case "%":
                return new OperatorModulo();
            case "<":
                return new OperatorLessThan();
            case "<=":
                return new OperatorLessThanOrEqual();
            case ">":
                return new OperatorGreaterThan();
            case ">=":
                return new OperatorGreaterThanOrEqual();
            case "<>":
                return new OperatorNotEqual();
            case "==":
                return new OperatorEquality();
            case "and":
                return new OperatorAnd();
            case "or":
                return new OperatorOr();
        }
        throw new RuntimeException();
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

    public ArrayList<FunctionCallParameter> parseFunctionCallParameters() {
        // assumes we read the opening parenthesis "("
        ArrayList<FunctionCallParameter> paramArrayList = new ArrayList<>();
        if (!lookahead.getAttribute().equals(Token.ClosingParenthesis)) {
            parseFunctionCallParameter(paramArrayList);
            while (isSymbol(Token.Comma)) {
                match(Token.Comma);
                parseFunctionCallParameter(paramArrayList);
            }
        }
        // TODO : do we have to match ")" ?
        return paramArrayList;
    }

    public void parseFunctionCallParameter(ArrayList<FunctionCallParameter> paramArrayList) {
        paramArrayList.add(parseParameterFunctionCall());
        while (isSymbol(Token.Comma)) {
            match(Token.Comma);
            parseFunctionCallParametersLoop(paramArrayList);
            paramArrayList.add(parseParameterFunctionCall());
        }
    }

    public void parseFunctionCallParametersLoop(ArrayList<FunctionCallParameter> paramArrayList) {
        Symbol operatorSymbol = whichSymbol(operatorValues);
        while (operatorSymbol != null) {
            ExpressionParameter operatorParameter = new ExpressionParameter(parseOperator((String) operatorSymbol.getAttribute()));
            paramArrayList.add(operatorParameter);
            pop();
            ExpressionParameter expressionParameter = new ExpressionParameter(parseExpression());
            paramArrayList.add(expressionParameter);
            pop();
            operatorSymbol = whichSymbol(operatorValues);
        }
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

    public FunctionCall parseFunctionCall(Identifier identifier) {


        ArrayList<ArrayOfExpression> arrayList = new ArrayList<>();
        match(Token.OpeningParenthesis);

        // Built-in functions
        if (identifier.getAttribute().equals(Token.Not.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new Not(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.Len.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new Len(identifier, arrayList);
        }

        if (identifier.getAttribute().equals(Token.Chr.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new Chr(identifier, arrayList);
        }
        // Procedure - IO
        if (identifier.getAttribute().equals(Token.ReadInt.getName())) {
            match(Token.ClosingParenthesis);
            return new ReadInt(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.ReadReal.getName())) {
            match(Token.ClosingParenthesis);
            return new ReadReal(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.ReadString.getName())) {
            match(Token.ClosingParenthesis);
            return new ReadString(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.Write.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new Write(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.Writeln.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new Writeln(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.WriteInt.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new WriteInt(identifier, arrayList);
        }
        if (identifier.getAttribute().equals(Token.WriteReal.getName())) {
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            arrayList.add(arrayOfExpression);
            match(Token.ClosingParenthesis);
            return new WriteReal(identifier, arrayList);
        }


        while (!isSymbol(Token.ClosingParenthesis)) {
            arrayList.add(parseArrayOfExpression());
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
            return new ForLoopAssignVariable(identifier, startExpression, endExpression, incrementByExpression, body);
        } else {
            CreateVariables createVariables = (parseCreationStateVariable());
            match(Token.ToKeyword);
            Expression endExpression = parseExpression();
            match(Token.ByKeyword);
            Expression incrementByExpression = parseExpression();
            Block body = parseBlock();
            return new ForLoopCreateVariable(createVariables, endExpression, incrementByExpression, body);
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
                Identifier identifier = new Identifier((String) match(Token.Identifier).getAttribute());
                Records record = new Records(identifier);
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
                if (isSymbol(Token.Semicolon)) {
                    return new ReturnVoid();
                }
                ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
                match(Token.Semicolon);
                return new ReturnStatement(arrayOfExpression);
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
                ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
                match(Token.Semicolon);
                return new Reassignment(identifier, arrayOfExpression);
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
                ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
                match(Token.ClosingBracket);
                AccessToIndexArray accessToIndexArray = new AccessToIndexArray(identifier, arrayOfExpression);

                // parse AssignVariables
                if (isSymbol(Token.Point)) {
                    return parseAssignmentOrMethodAccess(accessToIndexArray);
                }

                if (isSymbol(Token.Assignment)) {
                    match(Token.Assignment);
                    ArrayOfExpression assignmentExpression = parseArrayOfExpression();
                    return new AssignToIndexArray(accessToIndexArray, assignmentExpression);
                }
                return accessToIndexArray;

            }
            if (isSymbol(Token.Point)) {
                return parseAssignmentOrMethodAccess(identifier);
            }
        }


        return null;
    }

    // parseAssignmentOrMethodAccess
    public ASTNode parseAssignmentOrMethodAccess(AccessToIndexArray accessToIndexArray) {
        MethodCall methodCall = parseMethodCall(accessToIndexArray);
        if (isSymbol(Token.Assignment)) {
            match(Token.Assignment);
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            return createAssignment(methodCall, arrayOfExpression);
        }
        return methodCall;
    }

    public ASTNode parseAssignmentOrMethodAccess(Identifier identifier) {
        MethodCall methodCall = parseMethodCall(identifier);
        if (isSymbol(Token.Assignment)) {
            match(Token.Assignment);
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            return createAssignment(methodCall, arrayOfExpression);
        }
        return methodCall;
    }

    public AssignVariable createAssignment(MethodCall methodCall, ArrayOfExpression arrayOfExpression) {
        if (methodCall instanceof MethodCallFromIdentifier) {
            return createAssignment((MethodCallFromIdentifier) methodCall, arrayOfExpression);
        }
        return createAssignment((MethodCallFromIndexArray) methodCall, arrayOfExpression);
    }

    public AssignToRecordAttribute createAssignment(MethodCallFromIdentifier methodCallFromIdentifier, ArrayOfExpression arrayOfExpression) {
        return new AssignToRecordAttribute(methodCallFromIdentifier, arrayOfExpression);
    }

    public AssignToRecordAttributeAtIndex createAssignment(MethodCallFromIndexArray methodCallFromIndexArray, ArrayOfExpression arrayOfExpression) {
        return new AssignToRecordAttributeAtIndex(methodCallFromIndexArray, arrayOfExpression);
    }

    public Condition parseCondition() {
        ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
        return new Condition(arrayOfExpression);
    }

    public AccessToIndexArray parseAccessToIndexArray(Identifier identifier) {
        match(Token.OpeningBracket);
        ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
        match(Token.ClosingBracket);
        AccessToIndexArray accessToIndexArray = new AccessToIndexArray(identifier, arrayOfExpression);
        return accessToIndexArray;
    }

    public MethodCall parseMethodCall(Identifier identifier) {
        match(Token.Point);
        Identifier methodIdentifier = (Identifier) match(Token.Identifier);
        return new MethodCallFromIdentifier(identifier, methodIdentifier);
    }

    public MethodCall parseMethodCall(AccessToIndexArray accessToIndexArray) {
        match(Token.Point);
        Identifier methodIdentifier = (Identifier) match(Token.Identifier);
        return new MethodCallFromIndexArray(accessToIndexArray, methodIdentifier);

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
            // check for built-in
            Identifier referenceOrTypeIdentifier = (Identifier) match(Token.Identifier);
            if (isSymbol(Token.Semicolon)) {
                match(Token.Semicolon);
                ArrayList<Expression> expressions = new ArrayList<>();
                expressions.add(0, (new Variable(referenceOrTypeIdentifier)));
                ArrayOfExpression arrayOfExpression = new ArrayOfExpression(expressions);
                return new CreateExpressionVariable(create_variable_identifier, identifier, type, arrayOfExpression);
            }
            if (isSymbol(Token.OpeningBracket)) {
                match(Token.OpeningBracket);
                match(Token.ClosingBracket);
                ArrayType arrayType = new ArrayType(referenceOrTypeIdentifier);
                if (isSymbol(Token.OpeningParenthesis)) {
                    match(Token.OpeningParenthesis);
                    ArrayOfExpression arraySizeExpression = parseArrayOfExpression();
                    match(Token.ClosingParenthesis);
                    ArrayInitializer arrayInitializer = new ArrayInitializer(arrayType, arraySizeExpression);
                    // TODO : cast to arrayType or rather if (...) throw new Exception()?
                    return new CreateArrayVariable(create_variable_identifier, identifier, (ArrayType) type, arrayInitializer);
                }
            }
            if (isSymbol(Token.OpeningParenthesis)) {
                // pointer is not set to the start of the expression
                // if ArrayType
                if (type.getName().equals(ClassName.ArrayType.getName())) {
                    match(Token.OpeningParenthesis);
                    match(Token.ClosingParenthesis);
                    match(Token.OpeningBracket);
                    ArrayOfExpression sizeOfArray = parseArrayOfExpression();
                    match(Token.ClosingBracket);
                    //var c int[] = int[](5);  // new array of length 5
                    // var d Person = Person("me", Point(3,7), int[](a*2));

                    ArrayInitializer arrayInitializer = new ArrayInitializer((ArrayType) type, sizeOfArray);
                    return new CreateArrayVariable(create_variable_identifier, identifier, (ArrayType) type, arrayInitializer);
                }
                if (symbol == null) {
                    match(Token.OpeningParenthesis);
                    ArrayList<FunctionCallParameter> functionCallParameters = parseFunctionCallParameters();

                    match(Token.ClosingParenthesis);
                    RecordCall recordCall = new RecordCall(referenceOrTypeIdentifier, functionCallParameters);
                    CreateVariables expression = extendCreateExpressionVariable(create_variable_identifier, identifier, type, referenceOrTypeIdentifier);
                    if (expression != null) return expression;
                    return new CreateRecordVariables(create_variable_identifier, identifier, type, recordCall);
                } else {
                    ArrayList<Expression> arrayList = new ArrayList<>();
                    FunctionCall functionCall = parseFunctionCall(referenceOrTypeIdentifier);
                    arrayList.add(functionCall);
                    CreateVariables expression = extendCreateExpressionVariable(create_variable_identifier, identifier, type, referenceOrTypeIdentifier);
                    if (expression != null) return expression;
                    return new CreateExpressionVariable(create_variable_identifier, identifier, type, new ArrayOfExpression(arrayList));
                }
            }
            CreateVariables expression = extendCreateExpressionVariable(create_variable_identifier, identifier, type, referenceOrTypeIdentifier);
            if (expression != null) return expression;

        }
        ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
        return new CreateExpressionVariable(create_variable_identifier, identifier, type, arrayOfExpression);

    }

    private CreateVariables extendCreateExpressionVariable(Keyword create_variable_identifier, Identifier identifier, Type type, Identifier lastValue) {
        Symbol operatorSymbol = whichSymbol(operatorValues);
        if (operatorSymbol != null) {
            pop();
            ArrayOfExpression arrayOfExpression = parseArrayOfExpression();
            Operator operator = parseOperator((String) operatorSymbol.getAttribute());
            arrayOfExpression.expressions.add(0, (operator));
            arrayOfExpression.expressions.add(0, (new Variable(lastValue)));
            arrayOfExpression.myTree = new BinaryTree(arrayOfExpression.expressions);
            return new CreateExpressionVariable(create_variable_identifier, identifier, type, arrayOfExpression);
        }
        return null;
    }
}
