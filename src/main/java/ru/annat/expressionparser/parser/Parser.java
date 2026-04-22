package ru.annat.expressionparser.parser;

import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.exception.ParseException;
import ru.annat.expressionparser.lexer.Token;
import ru.annat.expressionparser.lexer.TokenType;
import ru.annat.expressionparser.ast.BinaryExpression;
import ru.annat.expressionparser.ast.UnaryExpression;
import ru.annat.expressionparser.ast.NumberExpression;
import ru.annat.expressionparser.ast.VariableExpression;
import ru.annat.expressionparser.ast.FunctionCallExpression;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

/**
 * Класс, выполняющий синтаксический анализ (парсинг) выражения.
 *
 * <p>Получает список токенов от лексера и строит абстрактное синтаксическое дерево (AST),
 * учитывая приоритет операций и структуру выражения.</p>
 */
public class Parser {

    private final List<Token> tokens;
    private int position;

    /**
     * Создаёт парсер на основе списка токенов.
     *
     * @param tokens список токенов
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    /**
     * Запускает процесс парсинга и возвращает корневое выражение AST.
     *
     * @return выражение (корень дерева)
     * @throws ParseException если структура выражения некорректна
     */
    public Expression parse() {
        Expression expression = parseExpression();

        if (!isAtEnd()) {
            throw new ParseException("Unexpected token at position " + current().getPosition());
        }

        return expression;
    }

    /**
     * Разбирает выражение с операциями сложения и вычитания.
     *
     * @return выражение
     */
    private Expression parseExpression() {
        Expression expression = parseTerm();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = parseTerm();
            expression = new BinaryExpression(expression, operator.getText().charAt(0), right);
        }

        return expression;
    }

    /**
     * Разбирает выражение с операциями умножения и деления.
     *
     * @return выражение
     */
    private Expression parseTerm() {
        Expression expression = parseUnary();

        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            Token operator = previous();
            Expression right = parseUnary();
            expression = new BinaryExpression(expression, operator.getText().charAt(0), right);
        }

        return expression;
    }

    /**
     * Разбирает унарные операции (плюс и минус).
     *
     * @return выражение
     */
    private Expression parseUnary() {
        if (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = parseUnary();
            return new UnaryExpression(operator.getText().charAt(0), right);
        }

        return parsePrimary();
    }

    /**
     * Разбирает базовые элементы выражения:
     * числа, переменные, функции и выражения в скобках.
     *
     * @return выражение
     */
    private Expression parsePrimary() {
        if (match(TokenType.NUMBER)) {
            Token number = previous();
            return new NumberExpression(Double.parseDouble(number.getText()));
        }

        if (match(TokenType.IDENTIFIER)) {
            Token identifier = previous();

            if (match(TokenType.LEFT_PAREN)) {
                List<Expression> arguments = new ArrayList<>();

                if (!check(TokenType.RIGHT_PAREN)) {
                    do {
                        arguments.add(parseExpression());
                    } while (match(TokenType.COMMA));
                }

                if (!match(TokenType.RIGHT_PAREN)) {
                    throw new ParseException("Expected ')' after function arguments at position " + current().getPosition());
                }

                return new FunctionCallExpression(identifier.getText(), arguments);
            }

            return new VariableExpression(identifier.getText());
        }

        if (match(TokenType.LEFT_PAREN)) {
            Expression expression = parseExpression();

            if (!match(TokenType.RIGHT_PAREN)) {
                throw new ParseException("Expected ')' at position " + current().getPosition());
            }

            return expression;
        }

        throw new ParseException("Expected number, variable or '(' at position " + current().getPosition());
    }

    /**
     * Проверяет, достигнут ли конец списка токенов.
     *
     * @return true, если достигнут конец
     */
    private boolean isAtEnd() {
        return current().getType() == TokenType.EOF;
    }

    /**
     * Возвращает текущий токен.
     *
     * @return текущий токен
     */
    private Token current() {
        return tokens.get(position);
    }

    /**
     * Переходит к следующему токену.
     *
     * @return предыдущий токен
     */
    private Token advance() {
        if (!isAtEnd()) position++;
        return tokens.get(position - 1);
    }

    /**
     * Проверяет, соответствует ли текущий токен одному из указанных типов,
     * и при совпадении продвигается вперёд.
     *
     * @param types типы токенов
     * @return true, если найдено совпадение
     */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }


    /**
     * Проверяет тип текущего токена без продвижения.
     *
     * @param type тип токена
     * @return true, если совпадает
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return current().getType() == type;
    }

    /**
     * Возвращает предыдущий токен.
     *
     * @return токен
     */
    private Token previous() {
        return tokens.get(position - 1);
    }
}