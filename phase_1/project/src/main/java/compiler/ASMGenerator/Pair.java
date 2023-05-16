package compiler.ASMGenerator;

public class Pair {

    Integer value;
    boolean staticField;

    public Pair(Integer value, boolean staticField) {
        this.value = value;
        this.staticField = staticField;
    }

    public Integer getValue() {
        return value;
    }

    public boolean getStaticField() {
        return staticField;
    }

}
