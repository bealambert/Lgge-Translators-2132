package compiler.Parser;

import compiler.Lexer.Identifier;

public class AssignToRecordAttribute extends AssignVariable {

    MethodCallFromIdentifier methodCallFromIdentifier;

    public AssignToRecordAttribute(MethodCallFromIdentifier methodCallFromIdentifier, ArrayOfExpression assignmentExpression) {
        super(assignmentExpression);
        this.methodCallFromIdentifier = methodCallFromIdentifier;
    }

    public MethodCallFromIdentifier getMethodCallFromIdentifier() {
        return methodCallFromIdentifier;
    }
}
