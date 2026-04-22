package ru.annat.expressionparser.ast;

import java.util.Map;

/**
 * Класс, представляющий переменную в выражении.
 *
 * <p>Значение переменной берётся из таблицы переменных,
 * передаваемой при вычислении.</p>
 */
public class VariableExpression implements Expression {

    private final String name;

    /**
     * Создаёт выражение переменной.
     *
     * @param name имя переменной
     */
    public VariableExpression(String name) {
        this.name = name;
    }

    /**
     * Возвращает значение переменной.
     *
     * @param variables таблица значений переменных
     * @return значение переменной
     * @throws IllegalArgumentException если переменная не определена
     */
    @Override
    public double evaluate(Map<String, Double> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable '" + name + "' is not defined");
        }
        return variables.get(name);
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    public String getName() {
        return name;
    }
}