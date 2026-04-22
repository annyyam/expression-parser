package ru.annat.expressionparser;

import ru.annat.expressionparser.ast.Expression;
import ru.annat.expressionparser.lexer.Lexer;
import ru.annat.expressionparser.lexer.Token;
import ru.annat.expressionparser.parser.Parser;

import java.util.*;

/**
 * Главный класс программы.
 *
 * <p>Организует полный цикл работы приложения:
 * чтение выражения с консоли, лексический анализ, синтаксический анализ,
 * сбор переменных, ввод их значений и вычисление результата.</p>
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String expressionText = scanner.nextLine();

        try {
            // Lexer
            Lexer lexer = new Lexer(expressionText);
            List<Token> tokens = lexer.tokenize();

            // Parser
            Parser parser = new Parser(tokens);
            Expression expression = parser.parse();

            // Collect variables
            VariableCollector collector = new VariableCollector();
            Set<String> variableNames = collector.collect(expression);

            // Ask user for variable values
            Map<String, Double> variables = new HashMap<>();
            for (String name : variableNames) {
                System.out.print("Введите значение " + name + ": ");
                double value = Double.parseDouble(scanner.nextLine());
                variables.put(name, value);
            }

            // Evaluate
            double result = expression.evaluate(variables);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}