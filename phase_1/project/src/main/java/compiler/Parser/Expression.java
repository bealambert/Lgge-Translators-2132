package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.ASTNode;
import compiler.Lexer.Symbol;
import compiler.Semantic.*;
import compiler.SemanticAnalysisException;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Expression extends ASTNode implements Visitable {

    public Expression() {
        super();
    }

    public Expression getExpression() {
        return this;
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    public void accept(MakeOperationVisitor makeOperationVisitor, Type accept, MethodVisitor mv) {
        makeOperationVisitor.visit(this, accept, mv);
    }
}
