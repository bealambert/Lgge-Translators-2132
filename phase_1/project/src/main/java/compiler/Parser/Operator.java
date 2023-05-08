package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.SemanticAnalysisException;
import org.objectweb.asm.MethodVisitor;

public class Operator extends Expression {
    int precedence_level;

    public int getPrecedenceLevel() {
        return precedence_level;
    }

    public void setPrecedence_level(int precedenceLevel) {
        this.precedence_level = precedenceLevel;
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }
}
