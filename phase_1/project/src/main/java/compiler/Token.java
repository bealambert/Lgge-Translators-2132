package compiler;

public enum Token {
    // match the class name
    Boolean("Boolean"),
    Comment("Comment"),
    Identifier("Identifier"),
    Keyword("Keyword"),
    NaturalNumber("NaturalNumber"),
    RealNumber("RealNumber"),
    SpecialCharacter("SpecialCharacter"),
    Strings("Strings"),

    // match the attribute
    True("true"),
    False("false"),

    BooleanIdentifier("bool"),
    IntIdentifier("int"),
    RealIdentifier("real"),
    StringIdentifier("string"),

    ConstKeyword("const"),
    RecordKeyword("record"),
    VarKeyword("var"),
    ValKeyword("val"),
    ProcKeyword("proc"),
    ForKeyword("for"),
    ToKeyword("to"),
    ByKeyword("by"),
    WhileKeyword("while"),
    IfKeyword("if"),
    ElseKeyword("else"),
    ReturnKeyword("return"),
    AndKeyword("and"),
    OrKeyword("or"),

    Assignment("="),
    PlusOperator("+"),
    MinusOperator("-"),
    MultiplyOperator("*"),
    DivideOperator("/"),
    ModuloOperator("%"),
    IsEqualOperator("=="),
    IsDifferentOperator("<>"),
    IsLessOperator("<"),
    IsGreaterOperator(">"),
    IsLessOrEqualOperator("<="),
    IsGreaterOrEqualOperator(">="),
    OpeningParenthesis("("),
    ClosingParenthesis(")"),
    OpeningCurlyBracket("{"),
    ClosingCurlyBracket("}"),
    OpeningBracket("["),
    ClosingBracket("]"),
    Point("."),
    Semicolon(";"),
    Comma(",");


    private final String name;

    private Token(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

