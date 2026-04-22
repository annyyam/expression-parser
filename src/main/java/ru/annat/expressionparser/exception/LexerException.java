package ru.annat.expressionparser.exception;

/**
 * Исключение, возникающее при ошибке лексического анализа.
 *
 * <p>Выбрасывается, если во входной строке обнаружен недопустимый символ
 * или нарушен формат токена.</p>
 */
public class LexerException extends RuntimeException {

    public LexerException(String message) {
        super(message);
    }
}