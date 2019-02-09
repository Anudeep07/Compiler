package compiler;

import java.util.ArrayList;

public class LexAnalyzer {

    int line;

    ArrayList<Token> tokens;

    LexAnalyzer() {
        line = 0;
    }

    public void tokenize(String lexeme) {

        for(int i=0 ; i<lexeme.length() ; i++) {
            char c = lexeme.charAt(i);

            switch(c) {
                case '(':
                    tokens.add(new Token(line, "(", null, TokenType.LEFT_PARAN));
                    break;

                case ')':
                case ';':
                case ',':
                case '\'':
                case ':':
                case '+':
                case '-':

                case '\n':
                    line++;
                    break;

                default:

                    System.err.println("Error at line " + line);
                    System.exit(0);
            }
        }

    }

}