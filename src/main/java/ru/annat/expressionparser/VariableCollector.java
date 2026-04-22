package ru.annat.expressionparser;

import ru.annat.expressionparser.ast.BinaryExpression;
import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.ast.NumberExpression;
import ru.annat.expressionparser.ast.UnaryExpression;
import ru.annat.expressionparser.ast.VariableExpression;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс для сбора имён переменных из абстрактного синтаксического дерева (AST).
 *
 * <p>Проходит по выражению и находит все используемые переменные,
 * чтобы затем можно было запросить их значения у пользователя.</p>
 */
public class VariableCollector {

    /**
     * Собирает все переменные, используемые в выражении.
     *
     * @param expression выражение
     * @return множество имён переменных
     */
    public Set<String> collect(Expression expression) {
        Set<String> variables = new LinkedHashSet<>();
        collectRecursive(expression, variables);
        return variables;
    }

    /**
     * Рекурсивно обходит дерево выражения и добавляет найденные переменные в множество.
     *
     * @param expression текущее выражение
     * @param variables множество переменных
     */
    private void collectRecursive(Expression expression, Set<String> variables) {
        if (expression instanceof NumberExpression) {
            return;
        }

        if (expression instanceof VariableExpression variableExpression) {
            variables.add(variableExpression.getName());
            return;
        }

        if (expression instanceof UnaryExpression unaryExpression) {
            collectRecursive(unaryExpression.getExpression(), variables);
            return;
        }

        if (expression instanceof BinaryExpression binaryExpression) {
            collectRecursive(binaryExpression.getLeft(), variables);
            collectRecursive(binaryExpression.getRight(), variables);
        }
    }
}