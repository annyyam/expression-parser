package ru.annat.expressionparser;

import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String expression = "sqrt(a) + 2 * b";

        Lexer lexer = new Lexer(expression);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}