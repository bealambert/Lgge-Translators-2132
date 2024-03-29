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

public class Chr extends BuiltIn {

    public Chr(Identifier identifier, ArrayList<ArrayOfExpression> params) {
        super(identifier, params);
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


}
