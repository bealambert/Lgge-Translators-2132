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
import java.lang.reflect.Method;
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

        MethodVisitor mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "floor", "(F)I", null, null);

        mv.visitCode();
        mv.visitVarInsn(FLOAD, 0);
        mv.visitInsn(Opcodes.F2D);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math", "floor", "(D)D", false);
        mv.visitInsn(Opcodes.D2I);
        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();


        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "not", "(Z)Z", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitInsn(Opcodes.IXOR);
        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "chr", "(I)Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitInsn(Opcodes.I2C);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(C)Ljava/lang/String;", false);
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "len", "(Ljava/lang/String;)I", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "len", "([Ljava/lang/Object;)I", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitInsn(Opcodes.ARRAYLENGTH);
        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();


        mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        int flag = PUTSTATIC;
        asmClassWriterVisitor.addMethodVisitor(mv, flag);
        mv.visitCode();

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
/*        mainMethodWriter.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        mainMethodWriter.visitFieldInsn(GETSTATIC, className, "s", "Ljava/lang/String;");
        mainMethodWriter.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");*/


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

/*            Method squareMethod = test.getDeclaredMethod("square", int.class, int.class); // Retrieves the "square" method with two int parameters
            squareMethod.setAccessible(true); // If the method is private, make it accessible

            int var0 = 8; // Provide the value for var0
            int var1 = 3; // Provide the value for var1

            int result = (int) squareMethod.invoke(null, var0, var1); // Invokes the square method with the provided int arguments

            // Process the result as needed
            System.out.println("Result: " + result);*/

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
