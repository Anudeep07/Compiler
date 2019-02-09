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
            file.useDelimiter(" ");

            LexAnalyzer lex = new LexAnalyzer();

            while(file.hasNext()) {
                String lexeme = file.next();
                lex.tokenize(lexeme);
            }


        } catch(FileNotFoundException f) {
            f.printStackTrace();
        }


    }

}