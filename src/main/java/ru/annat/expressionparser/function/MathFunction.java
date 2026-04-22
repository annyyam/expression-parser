package ru.annat.expressionparser.function;
import java.util.List;

/**
 * Интерфейс, описывающий математическую функцию.
 *
 * <p>Каждая функция должна уметь принимать список числовых аргументов
 * и возвращать результат вычисления.</p>
 */
public interface MathFunction {

    /**
     * Применяет функцию к переданным аргументам.
     *
     * @param arguments список числовых аргументов
     * @return результат вычисления функции
     */
    double apply(List<Double> arguments);

    /**
     * Возвращает количество аргументов, которое ожидает функция.
     *
     * @return число аргументов
     */
    int getArgumentCount();
}