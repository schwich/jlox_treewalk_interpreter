package jlox_treewalk_interpreter;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScannerTest {

    @Test
    public void testBlockComment() {

        String source = "var x = 2;\n\t\t/*\n\n Hello, world!\n\n */\nvar y = 3;";
        Scanner scanner = new Scanner(source);
        scanner.scanTokens().forEach(System.out::println);

    }

}