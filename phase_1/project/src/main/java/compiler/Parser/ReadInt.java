package compiler.Parser;

import compiler.ASMGenerator.MakeOperationVisitor;
import compiler.Lexer.Identifier;
import compiler.Semantic.SemanticVisitor;
import compiler.Semantic.SymbolTable;
import compiler.Semantic.TypeCheckingVisitor;
import compiler.Semantic.Visitor;
import compiler.SemanticAnalysisException;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;

public class ReadInt extends ProcedureIO {

    public ReadInt(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
    }

    @Override
    public void accept(MakeOperationVisitor makeOperationVisitor, Type accept, MethodVisitor mv) {
        makeOperationVisitor.visit(this, accept, mv);
    }

    @Override
    public Type accept(TypeCheckingVisitor typeCheckingVisitor) throws SemanticAnalysisException {
        return typeCheckingVisitor.visit(this);
    }

    @Override
    public void accept(SemanticVisitor visitor) throws SemanticAnalysisException {
        visitor.visit(this);
    }

    @Override
    public void accept(Visitor visitor, SymbolTable symbolTable) {
        visitor.visit(this, symbolTable);
    }

}
