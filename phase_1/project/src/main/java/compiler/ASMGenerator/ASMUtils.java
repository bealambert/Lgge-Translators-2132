package compiler.ASMGenerator;

import compiler.MyNode;
import compiler.Parser.*;
import compiler.SemanticAnalysisException;
import compiler.Token;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.HashMap;

import static org.objectweb.asm.Opcodes.*;

public class ASMUtils {

    HashMap<String, String> mapTypeToASMTypes;
    HashMap<String, Integer> mapReturnType;

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


        mapReturnType = new HashMap<>();
        mapReturnType.put(Token.Strings.getName(), ARETURN);
        mapReturnType.put(Token.StringIdentifier.getName(), ARETURN);
        mapReturnType.put(Token.NaturalNumber.getName(), IRETURN);
        mapReturnType.put(Token.IntIdentifier.getName(), IRETURN);
        mapReturnType.put(Token.RealNumber.getName(), FRETURN);
        mapReturnType.put(Token.RealIdentifier.getName(), FRETURN);
        mapReturnType.put(Token.Boolean.getName(), IRETURN);
        mapReturnType.put(Token.BooleanIdentifier.getName(), IRETURN);
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


    public String createDescFromParam(ArrayList<Param> params, Type returnType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int j = 0; j < params.size(); j++) {
            Type paramType = params.get(j).getType();
            String asmParamType = mapTypeToASMTypes.getOrDefault(paramType.getAttribute(), "A");
            stringBuilder.append(asmParamType);
            if (j < params.size() - 1) {
                stringBuilder.append(";");
            }
        }
        stringBuilder.append(")");
        stringBuilder.append(mapTypeToASMTypes.getOrDefault(returnType.getAttribute(), "A"));
        return stringBuilder.toString();
    }
}
