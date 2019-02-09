package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String []args) {
        if(args.length != 1) {
            System.err.println("Pass file name as first argument.");
            System.exit(0);
        }

        try {
            Scanner file = new Scanner(new File(args[0]));
            file.useDelimiter("\\Z");                       //end of file

            LexAnalyzer lex = new LexAnalyzer();

            String source = file.next();
            boolean error = lex.tokenize(source);

            if(!error) {
                //parse
            }

            lex.addEOF();

            for(Token token : lex.tokens) {
                System.out.println(token);
            }


        } catch(FileNotFoundException f) {
            System.err.println(args[0] + " couldn't be opened.");
            System.exit(0);
        }
    }

}