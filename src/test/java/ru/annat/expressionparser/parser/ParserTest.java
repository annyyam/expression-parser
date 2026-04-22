package ru.annat.expressionparser.parser;

import org.junit.jupiter.api.Test;
import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для класса Parser и вычисления выражений.
 *
 * <p>Проверяют корректность разбора выражений,
 * приоритет операций, работу со скобками и переменными.</p>
 */
class ParserTest {

    /**
     * Проверяет приоритет операций.
     */
    @Test
    void testOperatorPriority() {
        Lexer lexer = new Lexer("2 + 3 * 4");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(14.0, result);
    }

    /**
     * Проверяет корректную работу скобок.
     */
    @Test
    void testParentheses() {
        Lexer lexer = new Lexer("(2 + 3) * 4");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(20.0, result);
    }

    /**
     * Проверяет работу с переменными.
     */
    @Test
    void testVariables() {
        Lexer lexer = new Lexer("a + b * 2");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 5.0);
        variables.put("b", 3.0);

        double result = expression.evaluate(variables);

        assertEquals(11.0, result);
    }

    /**
     * Проверяет унарный минус.
     */
    @Test
    void testUnaryMinus() {
        Lexer lexer = new Lexer("-5 + 2");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(-3.0, result);
    }
}