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

public class Parser {

    private final List<Token> tokens;
    private int position;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    public Expression parse() {
        Expression expression = parseExpression();

        if (!isAtEnd()) {
            throw new ParseException("Unexpected token at position " + current().getPosition());
        }

        return expression;
    }

    private Expression parseExpression() {
        Expression expression = parseTerm();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = parseTerm();
            expression = new BinaryExpression(expression, operator.getText().charAt(0), right);
        }

        return expression;
    }

    private Expression parseTerm() {
        Expression expression = parseUnary();

        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            Token operator = previous();
            Expression right = parseUnary();
            expression = new BinaryExpression(expression, operator.getText().charAt(0), right);
        }

        return expression;
    }
    private Expression parseUnary() {
        if (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = parseUnary();
            return new UnaryExpression(operator.getText().charAt(0), right);
        }

        return parsePrimary();
    }
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
    private boolean isAtEnd() {
        return current().getType() == TokenType.EOF;
    }

    private Token current() {
        return tokens.get(position);
    }

    private Token advance() {
        if (!isAtEnd()) position++;
        return tokens.get(position - 1);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return current().getType() == type;
    }

    private Token previous() {
        return tokens.get(position - 1);
    }
}