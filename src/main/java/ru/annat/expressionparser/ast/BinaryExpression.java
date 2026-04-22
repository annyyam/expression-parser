package ru.annat.expressionparser.ast;
import ru.annat.expressionparser.exception.ParseException;
import java.util.Map;

/**
 * Класс, представляющий бинарную операцию (двуместную).
 *
 * <p>Поддерживает основные арифметические операции:
 * сложение (+), вычитание (-), умножение (*) и деление (/).</p>
 */
public class BinaryExpression implements Expression {

    private final Expression left;
    private final Expression right;
    private final char operator;

    /**
     * Создаёт бинарное выражение.
     *
     * @param left левый операнд
     * @param operator оператор
     * @param right правый операнд
     */
    public BinaryExpression(Expression left, char operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * Вычисляет результат бинарной операции.
     *
     * @param variables таблица значений переменных
     * @return результат вычисления
     * @throws ArithmeticException при делении на ноль
     * @throws ParseException если оператор неизвестен
     */
    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = left.evaluate(variables);
        double rightValue = right.evaluate(variables);

        return switch (operator) {
            case '+' -> leftValue + rightValue;
            case '-' -> leftValue - rightValue;
            case '*' -> leftValue * rightValue;
            case '/' -> {
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield leftValue / rightValue;
            }
            default -> throw new ParseException("Unknown operator: " + operator);
        };
    }

    /**
     * Возвращает левый операнд.
     *
     * @return левое выражение
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Возвращает правый операнд.
     *
     * @return правое выражение
     */
    public Expression getRight() {
        return right;
    }
}