package ru.annat.expressionparser.parser;

import org.junit.jupiter.api.Test;
import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void testOperatorPriority() {
        Lexer lexer = new Lexer("2 + 3 * 4");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(14.0, result);
    }

    @Test
    void testParentheses() {
        Lexer lexer = new Lexer("(2 + 3) * 4");
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        Expression expression = parser.parse();

        double result = expression.evaluate(new HashMap<>());

        assertEquals(20.0, result);
    }

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