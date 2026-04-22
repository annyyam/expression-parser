package ru.annat.expressionparser.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionRegistry {

    private static final Map<String, MathFunction> functions = new HashMap<>();

    static {
        // sqrt(x)
        functions.put("sqrt", new MathFunction() {
            @Override
            public double apply(List<Double> arguments) {
                return Math.sqrt(arguments.get(0));
            }

            @Override
            public int getArgumentCount() {
                return 1;
            }
        });

        // abs(x)
        functions.put("abs", new MathFunction() {
            @Override
            public double apply(List<Double> arguments) {
                return Math.abs(arguments.get(0));
            }

            @Override
            public int getArgumentCount() {
                return 1;
            }
        });
    }

    public static MathFunction getFunction(String name) {
        return functions.get(name);
    }

    public static boolean hasFunction(String name) {
        return functions.containsKey(name);
    }
}