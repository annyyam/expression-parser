package ru.annat.expressionparser.parser;

import org.junit.jupiter.api.Test;
import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для проверки работы функций.
 *
 * <p>Проверяют корректность вычисления встроенных функций
 * и их использования в выражениях.</p>
 */
class FunctionTest {

    /**
     * Проверяет функцию sqrt.
     */
    @Test
    void testSqrt() {
        Lexer lexer = new Lexer("sqrt(25)");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(5.0, result);
    }

    /**
     * Проверяет функцию abs.
     */
    @Test
    void testAbs() {
        Lexer lexer = new Lexer("abs(-5)");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(5.0, result);
    }

    /**
     * Проверяет использование функций в выражении.
     */
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