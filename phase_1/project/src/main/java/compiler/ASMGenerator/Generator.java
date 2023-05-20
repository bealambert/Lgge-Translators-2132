package compiler.ASMGenerator;

import compiler.ASTNode;
import compiler.Lexer.Strings;
import compiler.Parser.CreateProcedure;
import compiler.Parser.Parser;
import compiler.SemanticAnalysisException;
import jdk.nashorn.internal.codegen.types.Type;
import org.objectweb.asm.*;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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


    public static void main(String[] args) {

    }

    public void generateBytecode() throws SemanticAnalysisException {

        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null);
        asmClassWriterVisitor.setCw(cw, className);

        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        int flag = PUTSTATIC;
        asmClassWriterVisitor.addMethodVisitor(mv, flag);
        mv.visitCode();

        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        /*mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, className, "<init>", "()V", false);*/

        /*mv.visitIntInsn(BIPUSH, 3);
        mv.visitIntInsn(NEWARRAY, T_INT);
        mv.visitFieldInsn(flag, className, "y",
                "[I");

        mv.visitFieldInsn(GETSTATIC, "Test", "y", "[I");
        mv.visitInsn(ICONST_2);
        mv.visitInsn(ICONST_3);
        mv.visitInsn(IASTORE);*/


        ASTNode astNode = this.root;
        while (astNode != null) {
            if (astNode instanceof CreateProcedure && flag == PUTSTATIC) {
                if (!asmClassWriterVisitor.methodVisitorStack.isEmpty()) {
                    asmClassWriterVisitor.methodVisitorStack.pop();
                    asmClassWriterVisitor.flags.pop();
                    mv.visitInsn(RETURN);
                    mv.visitMaxs(-1, -1);
                    mv.visitEnd();
                    flag = PUTFIELD;
                    asmClassWriterVisitor.storeTable = new StoreTable(asmClassWriterVisitor.storeTable);
                }
            }
            astNode.accept(asmClassWriterVisitor);
            astNode = astNode.getNext();
        }
        if (!asmClassWriterVisitor.methodVisitorStack.isEmpty()) {
            mv.visitInsn(RETURN);
            mv.visitMaxs(-1, -1);
            mv.visitEnd();
        }
        MethodVisitor mainMethodWriter = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

        mainMethodWriter.visitCode();
        mainMethodWriter.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        mainMethodWriter.visitFieldInsn(GETSTATIC, className, "s", "Ljava/lang/String;");
        mainMethodWriter.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");


/*        // Declare the variable
        mainMethodWriter.visitInsn(Opcodes.ICONST_0); // Initialize variable to 0
        mainMethodWriter.visitVarInsn(Opcodes.ISTORE, 1); // Store the variable in index 1 of local variables

        // Loop condition
        Label loopCondition = new Label();
        mainMethodWriter.visitLabel(loopCondition);
        mainMethodWriter.visitVarInsn(Opcodes.ILOAD, 1); // Load the variable
        mainMethodWriter.visitIntInsn(Opcodes.BIPUSH, 10); // Push a constant value (10) onto the stack
        Label exitLoop = new Label();
        mainMethodWriter.visitJumpInsn(Opcodes.IF_ICMPGT, exitLoop); // Exit the loop if the variable is greater than 10

        // Loop body
        mainMethodWriter.visitIincInsn(1, 1); // Increment the variable by 1

        // Jump back to the loop condition
        mainMethodWriter.visitJumpInsn(Opcodes.GOTO, loopCondition);

        // Exit label
        mainMethodWriter.visitLabel(exitLoop);*/


        Label ifLabel = new Label();
        mainMethodWriter.visitIntInsn(Opcodes.BIPUSH, 3);
        mainMethodWriter.visitIntInsn(Opcodes.BIPUSH, 2);
        mainMethodWriter.visitJumpInsn(Opcodes.IF_ICMPLE, ifLabel); // Jump if 3 <= 2
        mainMethodWriter.visitLdcInsn("3 is greater than 2"); // Instruction to execute if the condition is true
        mainMethodWriter.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mainMethodWriter.visitInsn(Opcodes.SWAP);
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mainMethodWriter.visitLabel(ifLabel);
        /*// Call the square method
        mainMethodWriter.visitIntInsn(BIPUSH, 5); // Push the first argument
        mainMethodWriter.visitIntInsn(BIPUSH, 7); // Push the second argument
        mainMethodWriter.visitMethodInsn(INVOKESTATIC, "Test", "square", "(II)I", false);

        mainMethodWriter.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mainMethodWriter.visitInsn(SWAP);
        mainMethodWriter.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);*/

        mainMethodWriter.visitInsn(RETURN);
        mainMethodWriter.visitMaxs(-1, -1);
        mainMethodWriter.visitEnd();
        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();
        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> test = loader.defineClass(className, bytecode);
        try {
            try (FileOutputStream outputStream = new FileOutputStream("./Test.class")) {
                outputStream.write(bytecode);
                StringBuilder hexCode = new StringBuilder();
                for (byte b : bytecode) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1) {
                        hexCode.append('0'); // Pad single digit hexadecimal values with a leading zero
                    }
                    hexCode.append(hex);
                }
                System.out.println(hexCode.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            test.getMethod("main", String[].class).invoke(null, (Object) new String[0]);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
