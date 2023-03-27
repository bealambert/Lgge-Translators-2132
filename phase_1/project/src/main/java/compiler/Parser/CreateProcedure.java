package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;

import java.util.ArrayList;

public class CreateProcedure extends ASTNode {

    Identifier procedureName;
    ArrayList<Param> params;
    Type returnType;
    ArrayInitializer returnTypeArray;
    Block body;

    /*
proc square(v int) int {
    return v*v;
}

proc copyPoints(p Point[]) Point {
     return Point(p[0].x+p[1].x, p[0].y+p[1].y)
}

proc main() void {
    var value int = readInt();
    writeln(square(value));
    var i int;
    for i=1 to 100 by 2 {
        while value!=3 {
            // ....
        }
    }

    i = (i+2)*2
}
     */


    public CreateProcedure(Identifier procedureName, ArrayList<Param> params, Type returnType, Block body) {
        this.procedureName = procedureName;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    public String toString() {
        return "CreateProcedure{" +
                "procedureName=" + procedureName +
                ", params=" + params +
                ", returnType=" + returnType +
                ", body=" + body +
                '}';
    }
}
