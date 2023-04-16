package compiler.Parser;

public class ArrayInitializerParameter extends FunctionCallParameter {

    ArrayInitializer arrayInitializer;

    public ArrayInitializerParameter(ArrayInitializer arrayInitializer) {
        this.arrayInitializer = arrayInitializer;
    }

    public ArrayInitializer getArrayInitializer() {
        return arrayInitializer;
    }
}
