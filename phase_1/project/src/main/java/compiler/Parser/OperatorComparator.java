package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASMGenerator.OperatorVisitor;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.SemanticAnalysisException;
import org.objectweb.asm.MethodVisitor;

public class OperatorComparator extends Operator {
    public OperatorComparator() {
        super();
        this.setPrecedence_level(3);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type type, MethodVisitor mv) {
        makeOperationVisitor.visit(this, type, mv);
    }
}
