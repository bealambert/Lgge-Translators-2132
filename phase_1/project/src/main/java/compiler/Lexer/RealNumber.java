package compiler.Lexer;

public class RealNumber extends GenericValue implements Symbol {

    private final Double attribute;
    private final String name = "RealNumber";

    public RealNumber(String attribute) {
        this.attribute = Double.valueOf(attribute);
    }

    @Override
    public Double getAttribute() {
        return this.attribute;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "<" + this.name + ", " + this.getAttribute() + ">";
    }
}