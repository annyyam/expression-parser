package ru.annat.expressionparser.ast;

import java.util.List;
import java.util.Map;
import ru.annat.expressionparser.function.FunctionRegistry;
import ru.annat.expressionparser.function.MathFunction;
import java.util.ArrayList;

public class FunctionCallExpression implements Expression {

    private final String functionName;
    private final List<Expression> arguments;

    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        MathFunction function = FunctionRegistry.getFunction(functionName);

        if (function == null) {
            throw new IllegalArgumentException("Unknown function: " + functionName);
        }

        if (arguments.size() != function.getArgumentCount()) {
            throw new IllegalArgumentException(
                    "Function '" + functionName + "' expects " +
                            function.getArgumentCount() + " arguments, but got " + arguments.size()
            );
        }

        List<Double> evaluatedArguments = new ArrayList<>();
        for (Expression argument : arguments) {
            evaluatedArguments.add(argument.evaluate(variables));
        }

        return function.apply(evaluatedArguments);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}