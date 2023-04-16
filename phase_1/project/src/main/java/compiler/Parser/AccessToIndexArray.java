package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;

public class AccessToIndexArray extends Identifier implements Visitable {

    Expression expression;
    private final String name = "AccessToIndexArray";

    public AccessToIndexArray(String attribute, Expression expression) {
        super(attribute);
        this.expression = expression;
    }

    @Override
    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public String toString() {
        return "AccessToIndexArray{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }
}
