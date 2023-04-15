package compiler.Semantic;

import compiler.Lexer.Identifier;
import compiler.Parser.Type;

import java.util.ArrayList;

public class SemanticFunctionType extends SemanticTypes {

    private final ArrayList<Type> parameterTypes;
    private final String name = "SemanticFunctionType";

    private SemanticFunctionType(ArrayList<Type> parameterTypes, Type returnType) {
        super(returnType);
        this.parameterTypes = parameterTypes;
    }


    public ArrayList<Type> getParameterTypes() {
        return parameterTypes;
    }

    public String getName() {
        return name;
    }
}
