package ru.annat.expressionparser.ast;

import java.util.Map;

public class VariableExpression implements Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable '" + name + "' is not defined");
        }
        return variables.get(name);
    }
}