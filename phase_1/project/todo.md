## Parser

### Records

return new Records(keyword, identifier, openBracket, recordVariable, closeBracket);

### Expression - Precedence

Binary tree rather than an arraylist that does not take into account precedence

### ~~function call~~

- function_call : identifier ( Expression | Expression , MoreExpression )

remember an expression can also be a function call.

### Type

change how I implemented the type with isArray parameter???

extend type to int [] rather than only int

-> only one dimensional array

### Still does not even exist

    delete x;
    a[3] = 1234;   // a is an array of int
    a.x = 123;
    a[3].x = 12;

Built-in functions:

    not(bool) bool             negates a boolean value
    chr(int) string            turns the character (an int value) into a string
    len(string or array) int   gives the length of a string or array
    floor(real) int            returns the largest integer less than or equal the real value

There are built-in procedures for I/O:

    readInt, readReal, readString, writeInt, writeReal, write, writeln


