package ru.annat.expressionparser.lexer;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void testSimpleExpression() {
        Lexer lexer = new Lexer("2 + 3");
        List<Token> tokens = lexer.tokenize();

        assertEquals(4, tokens.size());

        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("2", tokens.get(0).getText());

        assertEquals(TokenType.PLUS, tokens.get(1).getType());

        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals("3", tokens.get(2).getText());

        assertEquals(TokenType.EOF, tokens.get(3).getType());
    }

    @Test
    void testIdentifier() {
        Lexer lexer = new Lexer("abc");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("abc", tokens.get(0).getText());
    }

    @Test
    void testInvalidCharacter() {
        Lexer lexer = new Lexer("2 + @");

        assertThrows(RuntimeException.class, lexer::tokenize);
    }
}
