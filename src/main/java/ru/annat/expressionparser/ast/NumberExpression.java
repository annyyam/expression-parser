package ru.annat.expressionparser.ast;

import java.util.Map;

/**
 * Класс, представляющий числовое выражение.
 *
 * <p>Используется для хранения и возврата числового значения,
 * которое не зависит от переменных.</p>
 */
public class NumberExpression implements Expression {

    private final double value;

    /**
     * Создаёт числовое выражение.
     *
     * @param value числовое значение
     */
    public NumberExpression(double value) {
        this.value = value;
    }

    /**
     * Возвращает значение числа.
     *
     * @param variables таблица переменных (не используется)
     * @return числовое значение
     */
    @Override
    public double evaluate(Map<String, Double> variables) {
        return value;
    }
}