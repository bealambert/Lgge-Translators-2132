package compiler.Parser;

import java.util.ArrayList;

public class RecordCall extends Records {

    Expression expression;

    public RecordCall(String attribute, Expression expression) {
        super(attribute);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "RecordCall{" +
                "expression=" + expression +
                '}';
    }
}
