package compiler.ASMGenerator;

import compiler.ASTNode;
import compiler.BinaryTree;
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
    ByteArrayClassLoader loader;

    public Generator(ASTNode root) {
        this.root = root;
        this.loader = new ByteArrayClassLoader();
    }


    public static void main(String[] args) {

    }

    public void createNecessaryMethods(ClassWriter cw) {
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


        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "write", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "writeInt", "(I)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "writeReal", "(F)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(FLOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(F)Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod
                (ACC_PUBLIC | ACC_STATIC, "writeln", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "readString", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        mv.visitInsn(Opcodes.DUP);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextLine", "()Ljava/lang/String;", false);
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();

        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "readInt", "()I", null, null);
        mv.visitCode();

        mv.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        mv.visitInsn(Opcodes.DUP);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I", false);

        mv.visitInsn(Opcodes.IRETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "readReal", "()F", null, null);
        mv.visitCode();

        mv.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        mv.visitInsn(Opcodes.DUP);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextFloat", "()F", false);

        mv.visitInsn(Opcodes.FRETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();


    }

    public void generateBytecode() throws SemanticAnalysisException {

        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null);
        asmClassWriterVisitor.setCw(cw, className);
        asmClassWriterVisitor.setLoader(loader);

        createNecessaryMethods(cw);

        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
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


        Label ifLabel = new Label();
        mainMethodWriter.visitIntInsn(Opcodes.BIPUSH, 3);
        mainMethodWriter.visitIntInsn(Opcodes.BIPUSH, 2);
        mainMethodWriter.visitJumpInsn(Opcodes.IF_ICMPLE, ifLabel); // Jump if 3 <= 2
        mainMethodWriter.visitLdcInsn("3 is greater than 2"); // Instruction to execute if the condition is true
        mainMethodWriter.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mainMethodWriter.visitInsn(Opcodes.SWAP);
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mainMethodWriter.visitLabel(ifLabel);

        mainMethodWriter.visitLdcInsn("abc");
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "writeln", "(Ljava/lang/String;)V", false);

        mainMethodWriter.visitLdcInsn("aezrar");
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "write", "(Ljava/lang/String;)V", false);

        mainMethodWriter.visitLdcInsn(15);
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "writeInt", "(I)V", false);

        mainMethodWriter.visitLdcInsn((float) 3.14);
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "writeReal", "(F)V", false);

        mainMethodWriter.visitLdcInsn("");
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "writeln", "(Ljava/lang/String;)V", false);

        mainMethodWriter.visitLdcInsn("azer123");
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "len", "(Ljava/lang/String;)I", false);
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "writeInt", "(I)V", false);

        mainMethodWriter.visitLdcInsn("");
        mainMethodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, className, "writeln", "(Ljava/lang/String;)V", false);



        //mainMethodWriter.visitLdcInsn(65);
        //mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, "chr", "(I)Ljava/lang/String;", false);

        mainMethodWriter.visitInsn(RETURN);
        mainMethodWriter.visitMaxs(-1, -1);
        mainMethodWriter.visitEnd();

        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();
        Class<?> test = this.loader.defineClass(className, bytecode);
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
            test.getMethod("writeln", String.class).invoke(null, "zzzzzzzzz");
            //test.getMethod("square", int.class, int.class).invoke(null, 1, 2);
            test.getMethod("not", boolean.class).invoke(null, true);
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
