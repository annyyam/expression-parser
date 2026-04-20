package ru.annat.expressionparser.lexer;

import ru.annat.expressionparser.exception.LexerException;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String input;
    private final List<Token> tokens;
    private int position;

    public Lexer(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }
        this.input = input;
        this.tokens = new ArrayList<>();
        this.position = 0;
    }

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

    private boolean isAtEnd() {
        return position >= input.length();
    }

    private char currentChar() {
        return input.charAt(position);
    }

    private void advance() {
        position++;
    }
}