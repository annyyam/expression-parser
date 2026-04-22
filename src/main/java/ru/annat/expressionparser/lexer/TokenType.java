package ru.annat.expressionparser.lexer;

/**
 * Перечисление всех типов токенов, используемых при лексическом анализе.
 *
 * <p>Определяет возможные элементы выражения:
 * числа, идентификаторы (переменные и функции),
 * операторы, скобки и служебные символы.</p>
 */

public enum TokenType {
    NUMBER,
    IDENTIFIER,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    LEFT_PAREN,
    RIGHT_PAREN,
    COMMA,
    EOF
}