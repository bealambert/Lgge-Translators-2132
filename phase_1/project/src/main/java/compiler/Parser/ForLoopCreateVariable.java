package compiler.Parser;

public class ForLoopCreateVariable extends ForLoop {

    CreateVariables createVariables;

    public ForLoopCreateVariable(CreateVariables createVariables, Expression end, Expression incrementBy, Block body) {
        super(end, incrementBy, body);
        this.createVariables = createVariables;

    }

    public CreateVariables getCreateVariables() {
        return createVariables;
    }
}
