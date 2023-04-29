package compiler.Parser;

import compiler.Lexer.Identifier;

public class AssignToRecordAttributeAtIndex extends AssignVariable {

    MethodCallFromIndexArray methodCallFromIndexArray;

    public AssignToRecordAttributeAtIndex(MethodCallFromIndexArray methodCallFromIndexArray, ArrayOfExpression assignmentExpression) {
        super(assignmentExpression);
        this.methodCallFromIndexArray = methodCallFromIndexArray;
    }

    public MethodCallFromIndexArray getMethodCallFromIndexArray() {
        return methodCallFromIndexArray;
    }
}
