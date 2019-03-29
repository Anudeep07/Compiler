package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Pass file name as first argument.");
            System.exit(0);
        }

        try {
            Scanner file = new Scanner(new File(args[0]));
            file.useDelimiter("\\Z"); // end of file

            LexAnalyzer lex = new LexAnalyzer();

            String source = file.next();
            boolean error = lex.tokenize(source);

            System.out.println("Tokens are: ");
            for (Token token : lex.tokens) {
                System.out.println(token);
            }

            if (!error) {
                Parser p = new Parser(lex.tokens);

                if (p.isvalid())
                    System.out.println("Parsed successfully!");
                else {
                    if (p.ptr <= 0)
                        p.ptr = 1;

                    if (p.ptr >= p.tokens.size())
                        p.ptr = p.tokens.size() - 1;
                    System.err.println("\nError at line " + p.tokens.get(p.ptr - 1).line);
                }

            }

            file.close();
        } catch (FileNotFoundException f) {
            System.err.println(args[0] + " couldn't be opened.");
            System.exit(0);
        }

    }

}