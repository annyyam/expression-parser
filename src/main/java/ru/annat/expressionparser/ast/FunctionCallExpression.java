package ru.annat.expressionparser.ast;

import java.util.List;
import java.util.Map;
import ru.annat.expressionparser.function.FunctionRegistry;
import ru.annat.expressionparser.function.MathFunction;
import java.util.ArrayList;

/**
 * Класс, представляющий вызов функции.
 *
 * <p>Содержит имя функции и список аргументов,
 * которые вычисляются и передаются в соответствующую реализацию функции.</p>
 */
public class FunctionCallExpression implements Expression {

    private final String functionName;
    private final List<Expression> arguments;

    /**
     * Создаёт выражение вызова функции.
     *
     * @param functionName имя функции
     * @param arguments список аргументов функции
     */
    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    /**
     * Вычисляет результат вызова функции.
     *
     * <p>Находит функцию в реестре, проверяет количество аргументов
     * и вычисляет их значения перед вызовом функции.</p>
     *
     * @param variables таблица значений переменных
     * @return результат работы функции
     * @throws IllegalArgumentException если функция не найдена или передано неверное количество аргументов
     */
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

    /**
     * Возвращает имя функции.
     *
     * @return имя функции
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Возвращает список аргументов функции.
     *
     * @return список выражений-аргументов
     */
    public List<Expression> getArguments() {
        return arguments;
    }
}