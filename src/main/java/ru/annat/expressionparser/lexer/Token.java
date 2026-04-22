package ru.annat.expressionparser.lexer;
import java.util.Objects;

/**
 * Класс, представляющий один токен (элемент выражения).
 *
 * <p>Токен содержит:
 * тип (TokenType),
 * текстовое представление,
 * позицию в исходной строке.</p>
 */
public class Token {

    private final TokenType type;
    private final String text;
    private final int position;

    /**
     * Создаёт токен.
     *
     * @param type тип токена
     * @param text текст токена
     * @param position позиция в исходной строке
     */
    public Token(TokenType type, String text, int position) {
        this.type = Objects.requireNonNull(type);
        this.text = Objects.requireNonNull(text);
        this.position = position;
    }

    /**
     * Возвращает тип токена.
     *
     * @return тип токена
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Возвращает текст токена.
     *
     * @return текст токена
     */
    public String getText() {
        return text;
    }

    /**
     * Возвращает позицию токена в строке.
     *
     * @return индекс позиции
     */
    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", position=" + position +
                '}';
    }
}