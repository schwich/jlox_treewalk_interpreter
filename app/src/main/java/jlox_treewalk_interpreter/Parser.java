package jlox_treewalk_interpreter;

import java.util.List;
import java.util.function.Supplier;

import static jlox_treewalk_interpreter.TokenType.*;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Expr parse() {
        try {
            return expression();
        } catch (ParseError error) {
            return null;
        }
    }

    private Expr expression() {
        return comma();
    }

    private Expr comma() {
        return parseLeftAssocBinaryOpSeries(this::ternary, COMMA);
    }

    private Expr ternary() {
        Expr expr = equality();

        while (true) {
            if (match(QUESTION)) {
                Expr middle = equality();
                if (match(COLON)) {
                    Expr right = equality();

                    if (match(QUESTION)) {
                        expr = new Expr.Ternary(expr, middle, right);
                    } else {
                        return new Expr.Ternary(expr, middle, right);
                    }
                } else {
                    Lox.error(peek().line, "fuck");
                    break;
                }
            }
        }

        return expr;
    }

    private Expr equality() {
        return parseLeftAssocBinaryOpSeries(this::comparison, BANG_EQUAL, EQUAL);
    }

    private Expr comparison() {
        return parseLeftAssocBinaryOpSeries(this::term, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL);
    }

    private Expr term() {
        return parseLeftAssocBinaryOpSeries(this::factor, PLUS, MINUS);
    }

    private Expr factor() {
        return parseLeftAssocBinaryOpSeries(this::unary, SLASH, STAR);
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }

    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression");
            return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect expression");
    }

    private Expr parseLeftAssocBinaryOpSeries(Supplier<Expr> operandMethodHandle, TokenType... types) {
        Expr expr = operandMethodHandle.get();

        while (match(types)) {
            Token operator = previous();
            Expr right = operandMethodHandle.get();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    // my version before reading
//    private Expr primary() {
//        if (match(LEFT_PAREN)) {
//            Expr expr = expression();
//
//            while (!match(RIGHT_PAREN)) {
//                expr = expression();
//            }
//
//            return new Expr.Grouping(expr);
//        }
//
//        return new Expr.Literal(advance().literal);
//    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private static class ParseError extends RuntimeException {
    }
}
