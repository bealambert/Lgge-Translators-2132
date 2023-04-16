package compiler.Lexer;

public class NaturalNumber extends GenericValue implements Symbol {

    private final Integer attribute;
    private final String name = "NaturalNumber";

    public NaturalNumber(String attribute) {
        this.attribute = Integer.valueOf(attribute);
    }

    @Override
    public Integer getAttribute() {
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