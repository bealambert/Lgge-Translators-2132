package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Token;

public class CreateVariable extends ASTNode {

    Keyword create_variable_identifier;
    Identifier identifier;
    Type type;
    AssignmentOperator assignmentOperator;
    Expression expression;

    public CreateVariable(Keyword create_variable_identifier, Identifier identifier,
                          Type type, AssignmentOperator assignmentOperator, Expression expression) {
        super();
        this.create_variable_identifier = create_variable_identifier;
        this.identifier = identifier;
        this.type = type;
        this.assignmentOperator = assignmentOperator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
