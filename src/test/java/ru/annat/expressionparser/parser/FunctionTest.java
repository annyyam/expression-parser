package ru.annat.expressionparser.parser;

import org.junit.jupiter.api.Test;
import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest {

    @Test
    void testSqrt() {
        Lexer lexer = new Lexer("sqrt(25)");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(5.0, result);
    }

    @Test
    void testAbs() {
        Lexer lexer = new Lexer("abs(-5)");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(5.0, result);
    }

    @Test
    void testFunctionInExpression() {
        Lexer lexer = new Lexer("sqrt(16) + abs(-3)");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(7.0, result);
    }
}