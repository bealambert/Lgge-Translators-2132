package compiler.Semantic;

import compiler.Lexer.Identifier;
import compiler.Parser.Type;

import java.util.ArrayList;

public class SemanticTypes {

    private final Type returnType;
    private final String name = "SemanticTypes";

    SemanticTypes(Type returnType) {
        this.returnType = returnType;
    }

    public Type getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

}
