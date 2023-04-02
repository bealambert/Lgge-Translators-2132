package compiler.Parser;

import compiler.ASTNode;
import compiler.Lexer.Identifier;
import compiler.Lexer.Keyword;

import java.util.ArrayList;

public class InitializeRecords extends ASTNode {


    Keyword keyword;
    Records records;
    ArrayList<RecordParameter> recordVariable;

    public InitializeRecords(Keyword keyword, Records records,
                             ArrayList<RecordParameter> recordVariable) {
        this.keyword = keyword;
        this.records = records;
        this.recordVariable = recordVariable;
    }


}
