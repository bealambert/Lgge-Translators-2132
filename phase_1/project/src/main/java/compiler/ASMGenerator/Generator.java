package compiler.ASMGenerator;

import compiler.ASTNode;
import compiler.Lexer.Strings;
import compiler.Parser.CreateProcedure;
import compiler.Parser.Parser;
import compiler.SemanticAnalysisException;
import jdk.nashorn.internal.codegen.types.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static org.objectweb.asm.Opcodes.*;


public class Generator {

    static final String className = "Test";
    private static ClassWriter cw;
    Parser parser;
    ASTNode root;

    ASMClassWriterVisitor asmClassWriterVisitor = new ASMClassWriterVisitor();
    ASMVisitorMethod asmVisitorMethod = new ASMVisitorMethod();

    public Generator(ASTNode root) {
        this.root = root;
    }

    public Generator() {

    }


    public void generateBytecode() throws SemanticAnalysisException {

        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);
        asmClassWriterVisitor.setCw(cw);


        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        int flag = PUTSTATIC;
        asmClassWriterVisitor.addMethodVisitor(mv, flag);
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

        ASTNode astNode = this.root;
        while (astNode != null) {
            if (astNode instanceof CreateProcedure && flag == PUTSTATIC) {
                if (!asmClassWriterVisitor.methodVisitorStack.isEmpty()) {
                    mv.visitEnd();
                    flag = PUTFIELD;
                }
            }
            astNode.accept(asmClassWriterVisitor);
            astNode = astNode.getNext();
        }
        if (!asmClassWriterVisitor.methodVisitorStack.isEmpty()) {
            mv.visitEnd();
        }
        /*mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        mv.visitFieldInsn(GETSTATIC, "Test", "i",  "I");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V");
        mv.visitEnd();*/
        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();
        /*ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> test = loader.defineClass(className, bytecode);*/
        try {
            try (FileOutputStream outputStream = new FileOutputStream("./Test.class")) {
                outputStream.write(bytecode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            test.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }*/

    }
}
