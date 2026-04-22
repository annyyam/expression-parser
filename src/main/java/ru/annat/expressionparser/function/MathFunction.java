package ru.annat.expressionparser.function;

import java.util.List;

public interface MathFunction {

    double apply(List<Double> arguments);

    int getArgumentCount();
}