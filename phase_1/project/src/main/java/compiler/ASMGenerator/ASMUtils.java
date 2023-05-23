package compiler.ASMGenerator;

import compiler.Lexer.Identifier;
import compiler.Parser.*;
import compiler.Semantic.ExpressionTypeVisitor;
import compiler.SemanticAnalysisException;
import compiler.Token;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.objectweb.asm.Opcodes.*;

public class ASMUtils {

    HashMap<String, String> mapTypeToASMTypes;
    HashMap<String, Integer> mapReturnType;
    HashMap<String, Integer> mapLoadType;
    HashMap<String, Integer> mapStoreType;
    HashMap<String, Integer> mapArrayType;
    HashMap<String, Integer> mapAssignToIndex;
    HashMap<String, Integer> mapLoadArray;
    HashMap<Integer, Integer> mapConstValues;

    public ASMUtils() {
        mapTypeToASMTypes = new HashMap<>();
        mapTypeToASMTypes.put(Token.Strings.getName(), "Ljava/lang/String");
        mapTypeToASMTypes.put(Token.StringIdentifier.getName(), "Ljava/lang/String;");
        mapTypeToASMTypes.put(Token.NaturalNumber.getName(), "I");
        mapTypeToASMTypes.put(Token.IntIdentifier.getName(), "I");
        mapTypeToASMTypes.put(Token.RealNumber.getName(), "F");
        mapTypeToASMTypes.put(Token.RealIdentifier.getName(), "F");
        mapTypeToASMTypes.put(Token.Boolean.getName(), "Z");
        mapTypeToASMTypes.put(Token.BooleanIdentifier.getName(), "Z");
        mapTypeToASMTypes.put(Token.VoidIdentifier.getName(), "V");

        mapLoadType = new HashMap<>();
        //mapLoadType.put(Token.Strings.getName(), "Ljava/lang/String");
        //mapLoadType.put(Token.StringIdentifier.getName(), "Ljava/lang/String;");
        mapLoadType.put(Token.NaturalNumber.getName(), ILOAD);
        mapLoadType.put(Token.IntIdentifier.getName(), ILOAD);
        mapLoadType.put(Token.RealNumber.getName(), FLOAD);
        mapLoadType.put(Token.RealIdentifier.getName(), FLOAD);
        mapLoadType.put(Token.Boolean.getName(), ILOAD);
        mapLoadType.put(Token.BooleanIdentifier.getName(), ILOAD);

        mapStoreType = new HashMap<>();
        mapStoreType.put(Token.Strings.getName(), ASTORE);
        mapStoreType.put(Token.StringIdentifier.getName(), ASTORE);
        mapStoreType.put(Token.NaturalNumber.getName(), ISTORE);
        mapStoreType.put(Token.IntIdentifier.getName(), ISTORE);
        mapStoreType.put(Token.RealNumber.getName(), FSTORE);
        mapStoreType.put(Token.RealIdentifier.getName(), FSTORE);
        mapStoreType.put(Token.Boolean.getName(), ISTORE);
        mapStoreType.put(Token.BooleanIdentifier.getName(), ISTORE);


        mapReturnType = new HashMap<>();
        mapReturnType.put(Token.Strings.getName(), ARETURN);
        mapReturnType.put(Token.StringIdentifier.getName(), ARETURN);
        mapReturnType.put(Token.NaturalNumber.getName(), IRETURN);
        mapReturnType.put(Token.IntIdentifier.getName(), IRETURN);
        mapReturnType.put(Token.RealNumber.getName(), FRETURN);
        mapReturnType.put(Token.RealIdentifier.getName(), FRETURN);
        mapReturnType.put(Token.Boolean.getName(), IRETURN);
        mapReturnType.put(Token.BooleanIdentifier.getName(), IRETURN);
        mapReturnType.put(Token.VoidIdentifier.getName(), RETURN);

        mapArrayType = new HashMap<>();
        mapArrayType.put(Token.NaturalNumber.getName(), T_INT);
        mapArrayType.put(Token.IntIdentifier.getName(), T_INT);
        mapArrayType.put(Token.RealNumber.getName(), T_FLOAT);
        mapArrayType.put(Token.RealIdentifier.getName(), T_FLOAT);
        mapArrayType.put(Token.Boolean.getName(), T_BOOLEAN);
        mapArrayType.put(Token.BooleanIdentifier.getName(), T_BOOLEAN);

        mapAssignToIndex = new HashMap<>();
        mapAssignToIndex.put(Token.NaturalNumber.getName(), IASTORE);
        mapAssignToIndex.put(Token.IntIdentifier.getName(), IASTORE);
        mapAssignToIndex.put(Token.RealNumber.getName(), FASTORE);
        mapAssignToIndex.put(Token.RealIdentifier.getName(), FASTORE);
        mapAssignToIndex.put(Token.Boolean.getName(), BASTORE);
        mapAssignToIndex.put(Token.BooleanIdentifier.getName(), BASTORE);

        mapLoadArray = new HashMap<>();
        mapLoadArray.put(Token.NaturalNumber.getName(), IALOAD);
        mapLoadArray.put(Token.IntIdentifier.getName(), IALOAD);
        mapLoadArray.put(Token.RealNumber.getName(), FALOAD);
        mapLoadArray.put(Token.RealIdentifier.getName(), FALOAD);
        mapLoadArray.put(Token.Boolean.getName(), BASTORE);
        mapLoadArray.put(Token.BooleanIdentifier.getName(), BALOAD);

        mapConstValues = new HashMap<>();
        mapConstValues.put(0, ICONST_0);
        mapConstValues.put(1, ICONST_1);
        mapConstValues.put(2, ICONST_2);
        mapConstValues.put(3, ICONST_3);
        mapConstValues.put(4, ICONST_4);
        mapConstValues.put(5, ICONST_5);
    }

    public int getAccess(String keyword) {
        int access = 0;
        if (keyword.equals(Token.VarKeyword.getName())) {
            access = ACC_PUBLIC;
        } else if (keyword.equals(Token.ValKeyword.getName())) {
            access = ACC_PUBLIC;
        } else if (keyword.equals(Token.ConstKeyword.getName())) {
            access |= ACC_PUBLIC | ACC_FINAL;
        }
        return access;
    }

    /*public void makeLeftRightValueInstruction(MyNode myNode) throws SemanticAnalysisException {
        myNode.getLeft().accept(this);
        myNode.getRight().accept(this);
    }*/

    public void makeConversionIntReal(Type expectedType, Type leftType, MethodVisitor mv) {

        boolean real = expectedType.getAttribute().equals(Token.RealNumber.getName()) || expectedType.getAttribute().equals(Token.RealIdentifier.getName());
        boolean integerToconvert = leftType.getAttribute().equals(Token.IntIdentifier.getName()) || leftType.getAttribute().equals(Token.NaturalNumber.getName());
        boolean applyConversion = real && integerToconvert;

        if (applyConversion) {
            mv.visitInsn(I2F);
        }
    }


    public String createDescFromParam(ArrayList<Param> params, Type returnType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int j = 0; j < params.size(); j++) {
            Type paramType = params.get(j).getType();
            String asmParamType = "";
            if (paramType instanceof ArrayType) {
                asmParamType = asmParamType + "[";
            }
            asmParamType = asmParamType + mapTypeToASMTypes.getOrDefault(paramType.getAttribute(), "A");
            stringBuilder.append(asmParamType);
            /*if (j < params.size() - 1) {
                stringBuilder.append(";");
            }*/
        }
        stringBuilder.append(")");
        stringBuilder.append(mapTypeToASMTypes.getOrDefault(returnType.getAttribute(), "A"));
        return stringBuilder.toString();
    }

    public Pair getFirstDeclarationInsideStoreStable(Identifier identifier, StoreTable storeTable) throws SemanticAnalysisException {
        Integer integer = storeTable.storeTable.get(identifier.getAttribute());

        if (integer != null) {
            boolean static_field = storeTable.previous == null;
            return new Pair(integer, static_field);
        }

        if (storeTable.previous != null) {
            return getFirstDeclarationInsideStoreStable(identifier, storeTable.previous);
        }

        throw new SemanticAnalysisException("Could not find identifier : " + identifier + " in this scope");

    }

    public Class<?> generateRecordBytecode(InitializeRecords initializeRecords, ByteArrayClassLoader loader) {

        int access = ACC_PUBLIC;
        String recordName = initializeRecords.getRecords().getIdentifier().getAttribute();
        ASMClassWriterVisitor asmClassWriterVisitor = new ASMClassWriterVisitor();

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_8, ACC_PUBLIC, recordName, null, "java/lang/Object", null);


        StringBuilder constructorDescription = new StringBuilder("(");
        ArrayList<RecordParameter> recordParameters = initializeRecords.getRecordVariable();
        for (int i = 0; i < recordParameters.size(); i++) {
            Identifier fieldName = recordParameters.get(i).getIdentifier();
            Type fieldType = recordParameters.get(i).getType();
            String desc = "";
            if (fieldType instanceof ArrayType) {
                desc += "[";
            }
            desc += mapTypeToASMTypes.getOrDefault(fieldType.getAttribute(), "A");
            constructorDescription.append(desc);
            cw.visitField(access, fieldName.getAttribute(), desc, null, null).visitEnd();
        }
        constructorDescription.append(")V");

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", constructorDescription.toString(), null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        for (int i = 0; i < recordParameters.size(); i++) {
            createFieldInConstructorWithRecordParameter(recordParameters.get(i), i + 1, mv, recordName);
        }

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();
        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();
        Class<?> test = loader.defineClass(recordName, bytecode);
        try (FileOutputStream outputStream = new FileOutputStream(recordName + ".class")) {
            outputStream.write(bytecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;

    }

    public void createFieldInConstructorWithRecordParameter(RecordParameter recordParameter, int store_index, MethodVisitor mv, String recordName) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        int loadType = ALOAD;
        if (!(recordParameter.getType() instanceof ArrayType)) {
            loadType = mapLoadType.getOrDefault(recordParameter.getType().getAttribute(), ALOAD);
        }

        String desc = "";
        if (recordParameter.getType() instanceof ArrayType) {
            desc += "[";
        }
        desc += mapTypeToASMTypes.getOrDefault(recordParameter.getType().getAttribute(), "A");
        mv.visitVarInsn(loadType, store_index);
        mv.visitFieldInsn(Opcodes.PUTFIELD, recordName, recordParameter.getIdentifier().getAttribute(), desc);

    }

    public String createDescriptionFromFunctionCallParamaters(ArrayList<FunctionCallParameter> functionCallParameters) throws SemanticAnalysisException {
        StringBuilder desc = new StringBuilder("(");
        for (int i = 0; i < functionCallParameters.size(); i++) {
            FunctionCallParameter functionCallParameter = functionCallParameters.get(i);
            if (functionCallParameter instanceof ArrayInitializerParameter) {
                ArrayInitializerParameter arrayInitializerParameter = (ArrayInitializerParameter) functionCallParameter;
                Type type = arrayInitializerParameter.getArrayInitializer().getType();
                desc.append("[").append(mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A"));
            } else if (functionCallParameter instanceof ExpressionParameter) {
                ExpressionParameter expressionParameter = (ExpressionParameter) functionCallParameter;
                Type type = expressionParameter.getExpression().accept(ExpressionTypeVisitor.typeCheckingVisitor);
                desc.append(mapTypeToASMTypes.getOrDefault(type.getAttribute(), "A"));
            } else if (functionCallParameter instanceof RecordCall) {
                desc.append("A");
            }
        }
        desc.append(")V");
        return desc.toString();
    }
}
