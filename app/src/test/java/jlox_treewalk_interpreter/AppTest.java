/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jlox_treewalk_interpreter;

import org.junit.Test;

public class AppTest {

    private static void scanAndParseSourceCode(String sourceCode) {
        Scanner scanner = new Scanner(sourceCode);
        Parser parser = new Parser(scanner.scanTokens());
        Expr expression = parser.parse();
        System.out.println(new AstPrinter().print(expression));
    }

    @Test
    public void parseExpression() {
        String sourceCode = "!(2 >= 3) + 5 * 3 / 2 ";
        scanAndParseSourceCode(sourceCode);
    }

    @Test
    public void parseExprWithCommaOperator() {
        String sourceCode = "2 + 3, 4 + 2";
        scanAndParseSourceCode(sourceCode);
        String sourceCode2 = "!(2 >= 3) + 5, 2 * 3 / 2 ";
        scanAndParseSourceCode(sourceCode2);
    }
}
