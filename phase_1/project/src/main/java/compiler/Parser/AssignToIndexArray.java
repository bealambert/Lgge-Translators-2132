package compiler.Parser;

public class AssignToIndexArray extends AssignVariable {

    // a[3] = 123;
    AccessToIndexArray accessToIndexArray;
    ArrayOfExpression assignmentExpression;

    public AssignToIndexArray(AccessToIndexArray accessToIndexArray, ArrayOfExpression assignmentExpression) {
        super(assignmentExpression);
        this.accessToIndexArray = accessToIndexArray;
    }


    public AccessToIndexArray getAccessToIndexArray() {
        return accessToIndexArray;
    }
}
