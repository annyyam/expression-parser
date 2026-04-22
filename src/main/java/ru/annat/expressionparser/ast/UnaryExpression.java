package ru.annat.expressionparser.ast;

import ru.annat.expressionparser.exception.ParseException;

import java.util.Map;

public class UnaryExpression implements Expression {

    private final char operator;
    private final Expression expression;

    public UnaryExpression(char operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double value = expression.evaluate(variables);

        return switch (operator) {
            case '+' -> value;
            case '-' -> -value;
            default -> throw new ParseException("Unknown unary operator: " + operator);
        };
    }
    public Expression getExpression() {
        return expression;
    }
}