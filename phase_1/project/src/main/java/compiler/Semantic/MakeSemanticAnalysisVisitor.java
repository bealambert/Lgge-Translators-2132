package compiler.Semantic;

import compiler.*;
import compiler.Lexer.Identifier;
import compiler.Parser.*;

import java.util.ArrayList;

public class MakeSemanticAnalysisVisitor implements SemanticVisitor {

    public int CONST = 0;
    public int RECORD = 1;
    public int OTHER = 2;

    int state;

    public MakeSemanticAnalysisVisitor() {
        this.state = CONST;
    }


    @Override
    public void visit(ForLoop forLoop) throws SemanticAnalysisException {
        // parent class
    }

    @Override
    public void visit(ForLoopAssignVariable forLoopAssignVariable) throws SemanticAnalysisException {
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(forLoopAssignVariable.getIdentifier(), forLoopAssignVariable.getSymbolTable());
        Type expectedInt = astNode.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        if (!expectedInt.getAttribute().equals(Token.IntIdentifier.getName())) {
            throw new SemanticAnalysisException("");
        }
        Type observedType = forLoopAssignVariable.getStart().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        treatSemanticCases.isEqual(expectedInt, observedType);

        Type observedEndExpression = forLoopAssignVariable.getEnd().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        treatSemanticCases.isEqual(expectedInt, observedEndExpression);

        Type observedIncrementExpression = forLoopAssignVariable.getIncrementBy().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        treatSemanticCases.isEqual(expectedInt, observedIncrementExpression);
        Block body = forLoopAssignVariable.getBody();
        body.accept(this);

    }

    @Override
    public void visit(ForLoopCreateVariable forLoopCreateVariable) throws SemanticAnalysisException {

        CreateVariables createVariables = forLoopCreateVariable.getCreateVariables();
        createVariables.accept(this);

        Type type = createVariables.getType();

        if (!type.getAttribute().equals(Token.IntIdentifier.getName())) {
            throw new SemanticAnalysisException("");
        }

        Type observedEndExpression = forLoopCreateVariable.getEnd().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        treatSemanticCases.isEqual(type, observedEndExpression);

        Type observedIncrementExpression = forLoopCreateVariable.getIncrementBy().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        treatSemanticCases.isEqual(type, observedIncrementExpression);
        Block body = forLoopCreateVariable.getBody();
        body.accept(this);
    }

    @Override
    public void visit(ReadInt readInt) throws SemanticAnalysisException {
        // nothing to do
    }

    @Override
    public void visit(ReadReal readReal) throws SemanticAnalysisException {
        // nothing to do
    }

    @Override
    public void visit(ReadString readString) throws SemanticAnalysisException {
        // nothing to do
    }

    @Override
    public void visit(WriteInt writeInt) throws SemanticAnalysisException {
        assert writeInt.getParams().size() == 1;
        Type type = writeInt.getParams().get(0).accept(ExpressionTypeVisitor.typeCheckingVisitor);
        if (!(type.getAttribute().equals(Token.IntIdentifier.getName()) || type.getAttribute().equals(Token.NaturalNumber.getName()))) {
            throw new SemanticAnalysisException("Expected an int as parameter of the writeInt function, observed : " + type.getAttribute());
        }
    }

    @Override
    public void visit(WriteReal writeReal) throws SemanticAnalysisException {
        assert writeReal.getParams().size() == 1;
        Type type = writeReal.getParams().get(0).accept(ExpressionTypeVisitor.typeCheckingVisitor);
        if (!(type.getAttribute().equals(Token.RealNumber.getName()) || type.getAttribute().equals(Token.RealIdentifier.getName()))) {
            throw new SemanticAnalysisException("Expected a real as parameter of the writeReal function, observed : " + type.getAttribute());
        }
    }

    @Override
    public void visit(Write write) throws SemanticAnalysisException {
        assert write.getParams().size() == 1;
        Type type = write.getParams().get(0).accept(ExpressionTypeVisitor.typeCheckingVisitor);
        if (!(type.getAttribute().equals(Token.Strings.getName()) || type.getAttribute().equals(Token.StringIdentifier.getName()))) {
            throw new SemanticAnalysisException("Expected a string as parameter of the write function, observed : " + type.getAttribute());
        }
    }

    @Override
    public void visit(Writeln writeln) throws SemanticAnalysisException {
        assert writeln.getParams().size() == 1;
        Type type = writeln.getParams().get(0).accept(ExpressionTypeVisitor.typeCheckingVisitor);
        if (!(type.getAttribute().equals(Token.Strings.getName()) || type.getAttribute().equals(Token.StringIdentifier.getName()))) {
            throw new SemanticAnalysisException("Expected a string as parameter of the writeln function, observed : " + type.getAttribute());
        }
    }

    @Override
    public void visit(Floor floor) throws SemanticAnalysisException {
        floor.accept(ExpressionTypeVisitor.typeCheckingVisitor);
    }

    @Override
    public void visit(Len len) throws SemanticAnalysisException {
        len.accept(ExpressionTypeVisitor.typeCheckingVisitor);
    }

    @Override
    public void visit(Chr chr) throws SemanticAnalysisException {
        chr.accept(ExpressionTypeVisitor.typeCheckingVisitor);
    }

    @Override
    public void visit(Not not) throws SemanticAnalysisException {
        not.accept(ExpressionTypeVisitor.typeCheckingVisitor);
    }

    @Override
    public void visit(SubExpression subExpression) throws SemanticAnalysisException {
        ArrayOfExpression arrayOfExpression = subExpression.getSubExpression();
        arrayOfExpression.accept(this);
    }

    @Override
    public void visit(ReturnVoid returnVoid) throws SemanticAnalysisException {

        SymbolTable symbolTable = returnVoid.getSymbolTable();
        Type observedType = returnVoid.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Identifier functionName = functionNameStack.peek();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionName, symbolTable);
        Type expectedType = createProcedure.getReturnType();
        treatSemanticCases.isEqual(expectedType, observedType);

    }

    @Override
    public void visit(AssignVariable assignVariable) throws SemanticAnalysisException {
        // parent class
    }

    @Override
    public void visit(AssignToIndexArray assignToIndexArray) throws SemanticAnalysisException {
        assignToIndexArray.getAccessToIndexArray().accept(this);
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable
                (assignToIndexArray.getAccessToIndexArray().getIdentifier(), assignToIndexArray.getSymbolTable());
        Type type = astNode.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Type assignmentType = assignToIndexArray.getAssignmentExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);

        treatSemanticCases.isEqual(type, assignmentType);


    }

    @Override
    public void visit(AssignToRecordAttribute assignToRecordAttribute) throws SemanticAnalysisException {
        assignToRecordAttribute.getMethodCallFromIdentifier().accept(this);
        Type type = assignToRecordAttribute.getMethodCallFromIdentifier().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Type assignmentType = assignToRecordAttribute.getAssignmentExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);

        treatSemanticCases.isEqual(type, assignmentType);
    }

    @Override
    public void visit(AssignToRecordAttributeAtIndex assignToRecordAttributeAtIndex) throws SemanticAnalysisException {
        assignToRecordAttributeAtIndex.getMethodCallFromIndexArray().accept(this);
        Type type = assignToRecordAttributeAtIndex.getMethodCallFromIndexArray().accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Type assignmentType = assignToRecordAttributeAtIndex.getAssignmentExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);

        treatSemanticCases.isEqual(type, assignmentType);
    }

    @Override
    public void visit(ExpressionParameter expressionParameter) throws SemanticAnalysisException {
        expressionParameter.getExpression().accept(this);

    }

    @Override
    public void visit(ArrayInitializerParameter arrayInitializerParameter) throws SemanticAnalysisException {
        arrayInitializerParameter.getArrayInitializer().accept(this);

    }

    @Override
    public void visit(FunctionCallParameter functionCallParameter) throws SemanticAnalysisException {
        // parent class
    }

    @Override
    public void visit(AccessToIndexArray accessToIndexArray) throws SemanticAnalysisException {
        //String input = "var c int[] = int[](5);";
        // var p int = c[0];
        accessToIndexArray.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Identifier identifier = accessToIndexArray.getIdentifier();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, identifier.getSymbolTable());
        Type type = astNode.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        if (!(type instanceof ArrayType)) {
            throw new SemanticAnalysisException("Trying to access an array through index but the variable is not an array");
        }
    }

    @Override
    public void visit(ArrayInitializer arrayInitializer) throws SemanticAnalysisException {
        Type observed = treatSemanticCases.treatExpression(arrayInitializer.getArraySize());
        if (!observed.getAttribute().equals("NaturalNumber")) {
            throw new SemanticAnalysisException("expected int \t  observed : " + observed.getAttribute());
        }
        treatSemanticCases.TypeExists(arrayInitializer.getType());
    }


    @Override
    public void visit(ArrayType arrayType) throws SemanticAnalysisException {
        treatSemanticCases.TypeExists(arrayType);
    }

    @Override
    public void visit(Block block) throws SemanticAnalysisException {
        ArrayList<ASTNode> astNodeArrayList = block.getAttribute();
        for (int i = 0; i < astNodeArrayList.size(); i++) {
            ASTNode astNode = astNodeArrayList.get(i);
            astNode.accept(this);
        }
    }

    @Override
    public void visit(Condition condition) throws SemanticAnalysisException {
        // expected boolean expression at the end
        Type observedType = treatSemanticCases.treatExpression(condition.getArrayOfExpression());
        if (!observedType.getAttribute().equals("bool")) {
            throw new SemanticAnalysisException("");
        }

    }

    @Override
    public void visit(CreateArrayVariable createArrayVariable) throws SemanticAnalysisException {
        this.state = treatSemanticCases.ensuresStateRespected(createArrayVariable, this.state);
        ArrayInitializer arrayInitializer = createArrayVariable.getArrayInitializer();
        ArrayType left = (ArrayType) createArrayVariable.getType();
        treatSemanticCases.TypeExists(left);
        ArrayType right = arrayInitializer.getType();
        treatSemanticCases.isEqual(left, right);
    }

    @Override
    public void visit(CreateExpressionVariable createExpressionVariable) throws SemanticAnalysisException {
        this.state = treatSemanticCases.ensuresStateRespected(createExpressionVariable, this.state);
        Type expectedType = createExpressionVariable.getType();
        treatSemanticCases.TypeExists(expectedType);
        Type observedType = treatSemanticCases.treatExpression(createExpressionVariable.getArrayOfExpression());
        boolean expectedIsReal = expectedType.getAttribute().equals(Token.RealNumber.getName()) || expectedType.getAttribute().equals(Token.RealIdentifier.getName());
        boolean observedIsInt = observedType.getAttribute().equals(Token.IntIdentifier.getName()) || observedType.getAttribute().equals(Token.NaturalNumber.getName());

        if (!(expectedIsReal && observedIsInt)) {
            treatSemanticCases.isEqual(expectedType, observedType);
        }
        createExpressionVariable.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        createExpressionVariable.getArrayOfExpression().accept(this);


    }

    @Override
    public void visit(CreateProcedure createProcedure) throws SemanticAnalysisException {
        // need to check the return value
        this.state = OTHER;
        functionNameStack.push(createProcedure.getProcedureName());
        Block body = createProcedure.getBody();
        body.accept(this);
        functionNameStack.pop();


    }

    @Override
    public void visit(CreateRecordVariables createRecordVariables) throws SemanticAnalysisException {

        // var d Person = Person(\"me\", Point(3,7), int[](a*2));  // new record";
        this.state = treatSemanticCases.ensuresStateRespected(createRecordVariables, this.state);
        SymbolTable symbolTable = createRecordVariables.getSymbolTable();
        Identifier identifier = createRecordVariables.getType();
        // Person - Person
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, symbolTable);
        if (!createRecordVariables.getType().getAttribute().equals(identifier.getAttribute())) {
            throw new SemanticAnalysisException("");
        }

        createRecordVariables.getRecordCall().accept(this);

    }

    @Override
    public void visit(CreateReferencedVariable createReferencedVariable) throws SemanticAnalysisException {
        this.state = treatSemanticCases.ensuresStateRespected(createReferencedVariable, this.state);
        Identifier referencedIdentifier = createReferencedVariable.getReferencedIdentifier();
        SymbolTable symbolTable = createReferencedVariable.getSymbolTable();
        CreateVariables createVariables = (CreateVariables) treatSemanticCases.getFirstDeclarationInsideSymbolTable(referencedIdentifier, symbolTable);
        Type expected = createVariables.getType();
        treatSemanticCases.TypeExists(expected);
        Type observed = createReferencedVariable.getType();
        treatSemanticCases.isEqual(expected, observed);
    }

    @Override
    public void visit(CreateVariables createVariables) throws SemanticAnalysisException {
        // only used as a disjoint parent class -> either A or B or C but the parent is not useful in this case
    }

    @Override
    public void visit(CreateVoidVariable createVoidVariable) throws SemanticAnalysisException {
        treatSemanticCases.TypeExists(createVoidVariable.getType());
    }

    @Override
    public void visit(Expression expression) throws SemanticAnalysisException {
        // parent class
    }


    @Override
    public void visit(FunctionCall functionCall) throws SemanticAnalysisException {

        SymbolTable symbolTable = functionCall.getSymbolTable();
        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionCall.getIdentifier(), symbolTable);
        if (astNode instanceof CreateProcedure) {
            CreateProcedure createProcedure = (CreateProcedure) astNode;
            ArrayList<Param> params = createProcedure.getParams();
            for (int i = 0; i < params.size(); i++) {
                Type expected = params.get(i).getType();
                Type observed = treatSemanticCases.treatExpression(functionCall.getParams().get(i));
                treatSemanticCases.isEqual(expected, observed);

                functionCall.getParams().get(i).accept(this);

            }
        } else {
            astNode = treatSemanticCases.getAccessToRecordDeclaration(functionCall.getIdentifier(), symbolTable);
            InitializeRecords initializeRecords = (InitializeRecords) astNode;
            ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
            for (int i = 0; i < recordParameters.size(); i++) {
                RecordParameter recordParameter = recordParameters.get(i);
                Type expected = recordParameter.accept(ExpressionTypeVisitor.typeCheckingVisitor);
                Type observed = treatSemanticCases.treatExpression(functionCall.getParams().get(i));
                treatSemanticCases.isEqual(expected, observed);
            }
        }
    }

    @Override
    public void visit(IfCondition ifCondition) throws SemanticAnalysisException {
        Condition condition = ifCondition.getCondition();
        Block body = ifCondition.getIfBlock();

        condition.accept(this);
        body.accept(this);
    }

    @Override
    public void visit(IfElse ifElse) throws SemanticAnalysisException {
        Condition condition = ifElse.getCondition();
        Block ifBlock = ifElse.getIfBlock();
        Block elseBlock = ifElse.getElseBlock();

        condition.accept(this);
        ifBlock.accept(this);
        elseBlock.accept(this);
    }

    @Override
    public void visit(InitializeRecords initializeRecords) throws SemanticAnalysisException {
        treatSemanticCases.ensuresStateRespected(initializeRecords, this.state);
        // function above throws error if state is "OTHER" so we can safely assign it to RECORD
        state = RECORD;
        Records records = initializeRecords.getRecords();
        SymbolTable symbolTable = initializeRecords.getSymbolTable();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(records.getIdentifier(), symbolTable);

        ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
        for (int i = 0; i < recordParameters.size(); i++) {
            RecordParameter recordParam = (RecordParameter) treatSemanticCases.getFirstDeclarationInsideSymbolTable
                    (recordParameters.get(i).getIdentifier(), symbolTable);
            recordParam.accept(this);
        }

    }

    @Override
    public void visit(MethodCall methodCall) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(MethodCallFromIdentifier methodCallFromIdentifier) throws SemanticAnalysisException {
        // <Object>.<method>
        SymbolTable symbolTable = methodCallFromIdentifier.getSymbolTable();
        Identifier objectIdentifier = methodCallFromIdentifier.getObjectIdentifier();
        //treatSemanticCases.getFirstDeclarationInsideSymbolTable(objectIdentifier, symbolTable);
        InitializeRecords initializeRecords = (InitializeRecords) treatSemanticCases.getAccessToRecordDeclaration(objectIdentifier, symbolTable);
        // Initialize Records or ArrayInitializer :'(

        // Do I have to check if the function can be applied on that object?
        // no class or something -> maybe built-in functions that are not yet implemented
        Identifier identifier = methodCallFromIdentifier.getMethodIdentifier();
        ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
        boolean found = false;
        for (int i = 0; i < recordParameters.size() && !found; i++) {
            if (recordParameters.get(i).getIdentifier().getAttribute().equals(identifier.getAttribute())) {
                found = true;
            }
        }
        if (!found) {
            throw new SemanticAnalysisException("attribute " + identifier.getAttribute() + " was not found inside record " + objectIdentifier.getAttribute());
        }
    }

    @Override
    public void visit(MethodCallFromIndexArray methodCallFromIndexArray) throws SemanticAnalysisException {
        SymbolTable symbolTable = methodCallFromIndexArray.getSymbolTable();

        AccessToIndexArray accessToIndexArray = methodCallFromIndexArray.getAccessToIndexArray();
        accessToIndexArray.accept(this);

        InitializeRecords initializeRecords = treatSemanticCases.getAccessToRecordDeclaration
                (methodCallFromIndexArray.getAccessToIndexArray().getIdentifier(), symbolTable);

        Identifier identifier = methodCallFromIndexArray.getMethodIdentifier();
        ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
        boolean found = false;
        for (int i = 0; i < recordParameters.size() && !found; i++) {
            if (recordParameters.get(i).getIdentifier().getAttribute().equals(identifier.getAttribute())) {
                found = true;
            }
        }
        if (!found) {
            throw new SemanticAnalysisException
                    ("attribute " + identifier.getAttribute() + " was not found inside record "
                            + initializeRecords.getRecords().getIdentifier().getAttribute());
        }
    }

    @Override
    public void visit(Param param) throws SemanticAnalysisException {
        SymbolTable symbolTable = param.getSymbolTable();

        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(param.getIdentifier(), symbolTable);
        astNode.accept(this);
    }

    @Override
    public void visit(Reassignment reassignment) throws SemanticAnalysisException {
        // var x int = 2;
        // var y int = 4;
        // x = y;
        SymbolTable symbolTable = reassignment.getSymbolTable();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(reassignment.getIdentifier(), symbolTable);
        reassignment.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        // implement visitor pattern for Expression to check types
    }

    @Override
    public void visit(RecordCall recordCall) throws SemanticAnalysisException {

        SymbolTable symbolTable = recordCall.getSymbolTable();
        Identifier identifier = recordCall.getRecords().getIdentifier();
        ArrayList<FunctionCallParameter> functionCallParameters = recordCall.getFunctionCallParameters();

        ASTNode astNode = treatSemanticCases.getFirstDeclarationInsideSymbolTable(identifier, symbolTable);
        if (astNode instanceof CreateProcedure) {
            treatSemanticCases.helperRecordCall((CreateProcedure) astNode, functionCallParameters);
        } else {
            treatSemanticCases.helperRecordCall((InitializeRecords) astNode, functionCallParameters);
        }
    }

    @Override
    public void visit(RecordParameter recordParameter) throws SemanticAnalysisException {
        // check the type exists
        SymbolTable symbolTable = recordParameter.getSymbolTable();
        RecordParameter storedRecordParameter = (RecordParameter)
                treatSemanticCases.getFirstDeclarationInsideSymbolTable(recordParameter.getIdentifier(), symbolTable);

        Type expected = storedRecordParameter.getType();
        Type observed = recordParameter.getType();
        treatSemanticCases.isEqual(expected, observed);
        // check for i-th param is of type <> should be inside the CreateRecordVariables

    }

    @Override
    public void visit(Records records) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(ReturnStatement returnStatement) throws SemanticAnalysisException {
        SymbolTable symbolTable = returnStatement.getSymbolTable();
        ASTNode astNode = treatSemanticCases.treatExpression(returnStatement.getArrayOfExpression());
        Type observedType = astNode.accept(ExpressionTypeVisitor.typeCheckingVisitor);
        Identifier functionName = functionNameStack.peek();
        CreateProcedure createProcedure = (CreateProcedure) treatSemanticCases.getFirstDeclarationInsideSymbolTable(functionName, symbolTable);
        Type expectedType = createProcedure.getReturnType();
        treatSemanticCases.isEqual(expectedType, observedType);
    }

    @Override
    public void visit(Type type) throws SemanticAnalysisException {
        SymbolTable symbolTable = type.getSymbolTable();
        treatSemanticCases.getFirstDeclarationInsideSymbolTable(type, symbolTable);
    }

    @Override
    public void visit(Values values) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(WhileLoop whileLoop) throws SemanticAnalysisException {
        Condition condition = whileLoop.getCondition();
        Block body = whileLoop.getBody();

        condition.accept(this);
        body.accept(this);
    }

    @Override
    public void visit(Identifier identifier) throws SemanticAnalysisException {
        //
    }

    @Override
    public void visit(BinaryTree binaryTree) throws SemanticAnalysisException {
        MyNode myNode = binaryTree.getRoot();

        MyNode left = myNode.getLeft();
        MyNode right = myNode.getRight();

    }

    @Override
    public void visit(MyNode myNode) throws SemanticAnalysisException {
        Expression expression = myNode.getValue();
        expression.accept(this);
        MyNode left = myNode.getLeft();
        MyNode right = myNode.getRight();
        if (left != null) {
            left.accept(this);
        }
        if (right != null) {
            right.accept(this);
        }
    }

    @Override
    public void visit(ArrayOfExpression arrayOfExpression) throws SemanticAnalysisException {

        for (int i = 0; i < arrayOfExpression.getExpressions().size(); i++) {
            arrayOfExpression.getExpressions().get(i).accept(this);
        }
    }

    @Override
    public void visit(Variable variable) throws SemanticAnalysisException {
        //
    }
}



