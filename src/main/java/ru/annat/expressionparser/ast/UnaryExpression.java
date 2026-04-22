package ru.annat.expressionparser.ast;
import ru.annat.expressionparser.exception.ParseException;
import java.util.Map;

/**
 * Класс, представляющий унарную операцию.
 *
 * <p>Поддерживает операции унарного плюса (+) и минуса (-).</p>
 */
public class UnaryExpression implements Expression {

    private final char operator;
    private final Expression expression;

    /**
     * Создаёт унарное выражение.
     *
     * @param operator оператор (+ или -)
     * @param expression выражение, к которому применяется оператор
     */
    public UnaryExpression(char operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    /**
     * Вычисляет значение унарного выражения.
     *
     * @param variables таблица значений переменных
     * @return результат вычисления
     * @throws ParseException если оператор неизвестен
     */
    @Override
    public double evaluate(Map<String, Double> variables) {
        double value = expression.evaluate(variables);

        return switch (operator) {
            case '+' -> value;
            case '-' -> -value;
            default -> throw new ParseException("Unknown unary operator: " + operator);
        };
    }

    /**
     * Возвращает вложенное выражение.
     *
     * @return выражение
     */
    public Expression getExpression() {
        return expression;
    }
}