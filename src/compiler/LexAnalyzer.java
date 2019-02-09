package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class LexAnalyzer {

    int line;
    boolean error;

    ArrayList<Token> tokens;
    HashMap<String, TokenType> keywords;

    LexAnalyzer() {
        line = 1;
        error = false;
        tokens = new ArrayList<>();
        keywords = new HashMap<>();
        keywords.put("int", TokenType.INT);
        keywords.put("main", TokenType.MAIN);
        keywords.put("char", TokenType.CHAR);
        keywords.put("switch", TokenType.SWITCH);
        keywords.put("begin", TokenType.BEGIN);
        keywords.put("case", TokenType.CASE);
        keywords.put("printf", TokenType.PRINTF);
        keywords.put("break", TokenType.BREAK);
        keywords.put("end", TokenType.END);
        keywords.put("return", TokenType.RETURN);
    }

    private boolean isalpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isdigit(char c) {
        return c >= '0' && c <= '9';
    }

    //returns false if no error after lexical analysis
    public boolean tokenize(String word) {

        for(int i=0 ; i<word.length() ; i++) {
            char c = word.charAt(i);

            switch(c) {
                case '(':
                    tokens.add(new Token(line, "(", null, TokenType.LEFT_PARAN));
                    break;

                case ')':
                    tokens.add(new Token(line, ")", null, TokenType.RIGHT_PARAN));
                    break;

                case ';':
                    tokens.add(new Token(line, ";", null, TokenType.SEMICOLON));
                    break;

                case ',':
                    tokens.add(new Token(line, ",", null, TokenType.COMMA));
                    break;

                case ':':
                    tokens.add(new Token(line, ":", null, TokenType.COLON));
                    break;

                case '+':
                    tokens.add(new Token(line, "+", null, TokenType.PLUS));
                    break;

                case '-':
                    tokens.add(new Token(line, "-", null, TokenType.MINUS));
                    break;

                case '\'':

                    if(i+2 < word.length() && word.charAt(i+2) == '\'') {
                        char betweenQuoteChar = word.charAt(i+1);

                        if(betweenQuoteChar == '\'') {
                            System.err.println("Expected character between quotes at line " + line);
                            error = true;
                        } else {
                            tokens.add(new Token(line, "'" + betweenQuoteChar +"'", (int) betweenQuoteChar, TokenType.LITERAL ));
                            //update i to ending '
                            i += 2;
                        }

                    } else {
                        System.err.println("Expected ' at line " + line);
                        error = true;
                    }


                    break;

                case '\n':
                    line++;
                    break;

                case ' ':
                case '\t':
                case '\r':
                    break;

                default:

                    //identifier or keyword
                    if(isalpha(c)) {
                        //keep reading until you get char other than num or letter
                        StringBuilder lexeme = new StringBuilder();
                        lexeme.append(c);
                        i++;
                        for(; i<word.length() ; i++) {
                            c = word.charAt(i);
                            if(isalpha(c) || isdigit(c))
                                lexeme.append(c);
                            else {
                                i--;                //have to do this because we are using same i in the upper for loop as well (otherwise char that break this loop will be skipped)
                                break;
                            }
                        }

                        String lex = lexeme.toString();
                        TokenType type = keywords.get(lex);
                        if(type == null)
                            type = TokenType.IDENTIFIER;

                        tokens.add(new Token(line, lex, null, type));

                    } else if(isdigit(c)) {
                        //keep reading until you get char other than num
                        StringBuilder lexeme = new StringBuilder();
                        lexeme.append(c);
                        i++;

                        for(; i<word.length() ; i++) {
                            c = word.charAt(i);
                            if(isdigit(c))
                                lexeme.append(c);
                            else {
                                i--;
                                break;
                            }
                        }

                        String lex = lexeme.toString();
                        tokens.add(new Token(line, lex, Integer.parseInt(lex), TokenType.LITERAL));

                    } else {
                        System.err.println("Error at line " + line);
                        error = true;
                    }
            }
        }
        return error;
    }

    public void addEOF(){
        tokens.add(new Token(line, "", null, TokenType.EOF));
    }

}