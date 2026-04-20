package ru.annat.expressionparser;

import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;
import ru.annat.expressionparser.parser.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String expressionText = "(2 + 3) * 4";

        Lexer lexer = new Lexer(expressionText);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        Map<String, Double> variables = new HashMap<>();
        double result = expression.evaluate(variables);

        System.out.println("Result: " + result);
    }
}