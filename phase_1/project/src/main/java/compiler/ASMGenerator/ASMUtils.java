package compiler.ASMGenerator;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.MyNode;
import compiler.Parser.*;
import compiler.Semantic.SymbolTable;
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
    HashMap<String, Integer> mapLoadType;
    HashMap<String, Integer> mapStoreType;
    HashMap<String, Integer> mapArrayType;
    HashMap<String, Integer> mapAssignToIndex;

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
            /*if (j < params.size() - 1) {
                stringBuilder.append(";");
            }*/
        }
        stringBuilder.append(")");
        stringBuilder.append(mapTypeToASMTypes.getOrDefault(returnType.getAttribute(), "A"));
        return stringBuilder.toString();
    }

    public Integer getFirstDeclarationInsideStoreStable(Identifier identifier, StoreTable storeTable) throws SemanticAnalysisException {
        Integer integer = storeTable.storeTable.get(identifier.getAttribute());

        if (integer != null) {
            return integer;
        }

        if (storeTable.previous != null) {
            return getFirstDeclarationInsideStoreStable(identifier, storeTable.previous);
        }

        throw new SemanticAnalysisException("Could not find identifier : " + identifier + " in this scope");

    }
}
