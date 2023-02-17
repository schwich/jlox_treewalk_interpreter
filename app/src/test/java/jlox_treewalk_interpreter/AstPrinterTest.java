package jlox_treewalk_interpreter;

import org.junit.Test;

import static org.junit.Assert.*;

public class AstPrinterTest {

    @Test
    public void print() {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(
                        new Expr.Literal(45.67)));

        String expr = new AstPrinter().print(expression);
        assertEquals("(* (- 123) (group 45.67))", expr);
    }
}