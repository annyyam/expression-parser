package ru.annat.expressionparser.lexer;

import ru.annat.expressionparser.exception.LexerException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для выполнения лексического анализа входного выражения.
 *
 * <p>Преобразует строку выражения в последовательность токенов,
 * которые затем используются парсером.</p>
 */
public class Lexer {

    private final String input;
    private final List<Token> tokens;
    private int position;

    /**
     * Создаёт лексер для заданного выражения.
     *
     * @param input строка выражения
     */
    public Lexer(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }
        this.input = input;
        this.tokens = new ArrayList<>();
        this.position = 0;
    }

    /**
     * Выполняет разбор строки и возвращает список токенов.
     *
     * @return список токенов
     * @throws LexerException если встречен недопустимый символ
     */
    public List<Token> tokenize() {
        while (!isAtEnd()) {
            char current = currentChar();

            if (Character.isWhitespace(current)) {
                advance();
                continue;
            }

            if (Character.isDigit(current) || current == '.') {
                tokens.add(readNumber());
                continue;
            }

            if (Character.isLetter(current) || current == '_') {
                tokens.add(readIdentifier());
                continue;
            }

            switch (current) {
                case '+' -> {
                    tokens.add(new Token(TokenType.PLUS, "+", position));
                    advance();
                }
                case '-' -> {
                    tokens.add(new Token(TokenType.MINUS, "-", position));
                    advance();
                }
                case '*' -> {
                    tokens.add(new Token(TokenType.MULTIPLY, "*", position));
                    advance();
                }
                case '/' -> {
                    tokens.add(new Token(TokenType.DIVIDE, "/", position));
                    advance();
                }
                case '(' -> {
                    tokens.add(new Token(TokenType.LEFT_PAREN, "(", position));
                    advance();
                }
                case ')' -> {
                    tokens.add(new Token(TokenType.RIGHT_PAREN, ")", position));
                    advance();
                }
                case ',' -> {
                    tokens.add(new Token(TokenType.COMMA, ",", position));
                    advance();
                }
                default -> throw new LexerException(
                        "Unexpected character '" + current + "' at position " + position
                );
            }
        }

        tokens.add(new Token(TokenType.EOF, "", position));
        return tokens;
    }

    /**
     * Считывает числовой токен из входной строки.
     *
     * <p>Поддерживает как целые, так и дробные числа.
     * Проверяет корректность формата (например, не допускает несколько точек).</p>
     *
     * @return токен типа NUMBER
     * @throws LexerException если формат числа некорректен
     */
    private Token readNumber() {
        int start = position;
        boolean hasDot = false;

        if (currentChar() == '.') {
            hasDot = true;
            advance();

            if (isAtEnd() || !Character.isDigit(currentChar())) {
                throw new LexerException("Invalid number format at position " + start);
            }
        }

        while (!isAtEnd()) {
            char current = currentChar();

            if (Character.isDigit(current)) {
                advance();
            } else if (current == '.') {
                if (hasDot) {
                    throw new LexerException("Invalid number format at position " + position);
                }
                hasDot = true;
                advance();
            } else {
                break;
            }
        }

        String text = input.substring(start, position);
        return new Token(TokenType.NUMBER, text, start);
    }

    /**
     * Считывает идентификатор (имя переменной или функции).
     *
     * <p>Идентификатор может содержать буквы, цифры и символ подчёркивания,
     * но должен начинаться с буквы или символа '_'.</p>
     *
     * @return токен типа IDENTIFIER
     */
    private Token readIdentifier() {
        int start = position;

        while (!isAtEnd()) {
            char current = currentChar();
            if (Character.isLetterOrDigit(current) || current == '_') {
                advance();
            } else {
                break;
            }
        }

        String text = input.substring(start, position);
        return new Token(TokenType.IDENTIFIER, text, start);
    }

    /**
     * Проверяет, достигнут ли конец входной строки.
     *
     * @return true, если достигнут конец строки, иначе false
     */
    private boolean isAtEnd() {
        return position >= input.length();
    }

    /**
     * Возвращает текущий символ входной строки.
     *
     * @return текущий символ
     */
    private char currentChar() {
        return input.charAt(position);
    }

    /**
     * Переходит к следующему символу во входной строке.
     */
    private void advance() {
        position++;
    }
}