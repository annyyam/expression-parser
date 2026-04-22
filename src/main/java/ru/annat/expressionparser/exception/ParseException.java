package ru.annat.expressionparser.exception;

/**
 * Исключение, возникающее при ошибке синтаксического анализа.
 *
 * <p>Выбрасывается, если структура выражения некорректна
 * (например, отсутствует скобка или неверный порядок токенов).</p>
 */
public class ParseException extends RuntimeException {

    public ParseException(String message) {
        super(message);
    }
}