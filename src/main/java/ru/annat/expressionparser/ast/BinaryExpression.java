package ru.annat.expressionparser.ast;

import ru.annat.expressionparser.exception.ParseException;

import java.util.Map;

public class BinaryExpression implements Expression {

    private final Expression left;
    private final Expression right;
    private final char operator;

    public BinaryExpression(Expression left, char operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = left.evaluate(variables);
        double rightValue = right.evaluate(variables);

        return switch (operator) {
            case '+' -> leftValue + rightValue;
            case '-' -> leftValue - rightValue;
            case '*' -> leftValue * rightValue;
            case '/' -> {
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield leftValue / rightValue;
            }
            default -> throw new ParseException("Unknown operator: " + operator);
        };
    }
    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}