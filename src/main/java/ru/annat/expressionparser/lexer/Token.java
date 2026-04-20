package ru.annat.expressionparser.lexer;

import java.util.Objects;

public class Token {

    private final TokenType type;
    private final String text;
    private final int position;

    public Token(TokenType type, String text, int position) {
        this.type = Objects.requireNonNull(type);
        this.text = Objects.requireNonNull(text);
        this.position = position;
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

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