package ru.annat.expressionparser;

import ru.annat.expressionparser.ast.BinaryExpression;
import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.ast.NumberExpression;
import ru.annat.expressionparser.ast.UnaryExpression;
import ru.annat.expressionparser.ast.VariableExpression;

import java.util.LinkedHashSet;
import java.util.Set;

public class VariableCollector {

    public Set<String> collect(Expression expression) {
        Set<String> variables = new LinkedHashSet<>();
        collectRecursive(expression, variables);
        return variables;
    }

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