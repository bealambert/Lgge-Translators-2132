package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;
import compiler.Token;

public class CreateVariable extends ASTNode {

    Keyword create_variable_identifier;
    Identifier identifier;
    Type type;
    Expression expression;
    ArrayInitializer arrayInitializer;

    public CreateVariable(Keyword create_variable_identifier, Identifier identifier,
                          Type type) {
        super();
        this.create_variable_identifier = create_variable_identifier;
        this.identifier = identifier;
        this.type = type;
    }

    public CreateVariable(Keyword create_variable_identifier, Identifier identifier,
                          Type type, Expression expression) {
        super();
        this.create_variable_identifier = create_variable_identifier;
        this.identifier = identifier;
        this.type = type;
        this.expression = expression;
    }

    public CreateVariable(Keyword create_variable_identifier, Identifier identifier,
                          Type type, ArrayInitializer arrayInitializer) {
        super();
        this.create_variable_identifier = create_variable_identifier;
        this.identifier = identifier;
        this.type = type;
        this.arrayInitializer = arrayInitializer;
    }

    @Override
    public String toString() {
        return "CreateVariable{" +
                "create_variable_identifier=" + create_variable_identifier +
                ", identifier=" + identifier +
                ", type=" + type +
                ", expression=" + expression +
                ", arrayInitializer=" + arrayInitializer +
                '}';
    }
}
