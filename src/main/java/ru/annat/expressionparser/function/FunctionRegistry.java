package ru.annat.expressionparser.function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, содержащий реестр доступных математических функций.
 *
 * <p>Хранит соответствие имени функции и её реализации,
 * а также предоставляет методы для получения функций.</p>
 */
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

    /**
     * Возвращает функцию по её имени.
     *
     * @param name имя функции
     * @return объект функции или null, если функция не найдена
     */
    public static MathFunction getFunction(String name) {
        return functions.get(name);
    }

    /**
     * Проверяет, существует ли функция с заданным именем.
     *
     * @param name имя функции
     * @return true, если функция существует, иначе false
     */
    public static boolean hasFunction(String name) {
        return functions.containsKey(name);
    }
}