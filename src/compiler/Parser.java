package compiler;

/*
 *  Akshay Kamath
 */

import java.util.ArrayList;

public class Parser {

    int ptr;
    ArrayList<Token> tokens;

    Parser(ArrayList<Token> tokens) {
        ptr = 0;
        this.tokens = tokens;
    }

    // returns true if syntactically correct
    boolean isvalid() {
        return code();
    }

    boolean code() {
        if (main())
            if (stm() && terminal(TokenType.RETURN)) {
                if (terminal(TokenType.LITERAL))
                    if (terminal(TokenType.SEMICOLON))
                        return true;
            }
        return false;
    }

    // checks for the tokens [ int main ( ) ]
    boolean main() {
        if (terminal(TokenType.INT))
            if (terminal(TokenType.MAIN))
                if (terminal(TokenType.LEFT_PARAN))
                    if (terminal(TokenType.RIGHT_PARAN)) {
                        return true;
                    }
        return false;
    }

    boolean stm() {
        if (declare() || print()) {

            // declare and print statements to be followed by ;
            if (terminal(TokenType.SEMICOLON)) {
                if (stm())
                    return true;
                else
                    return false;
            }
            return false;
        } else if (tswitch()) {

            // switch statement should be followed by END
            if (terminal(TokenType.END)) {
                if (stm()) {
                    return true;
                } else
                    return false;
            } else
                return false;
        } else
            return true;

    }

    boolean declare() {
        if (type())
            if (id())
                if (variables() && terminal(TokenType.SEMICOLON)) {
                    ptr--;
                    return true;
                } else {
                    ptr--;
                    return false;
                }

        return false;

    }

    boolean variables() {
        if (terminal(TokenType.COMMA)) {

            if (id())
                if (variables())
                    return true;
                else
                    return false;
            else {
                ptr--;
                return false;
            }
        }
        return true;
    }

    boolean tswitch() {
        if (terminal(TokenType.SWITCH))
            if (terminal(TokenType.LEFT_PARAN))
                if (operand())
                    if (terminal(TokenType.RIGHT_PARAN))
                        if (beginToEnd()) {
                            return true;
                        }
        return false;
    }

    boolean operand() {
        if (terminal(TokenType.IDENTIFIER) || terminal(TokenType.LITERAL))
            return true;
        else
            return false;
    }

    boolean beginToEnd() {
        if (terminal(TokenType.BEGIN))
            if (tcase()) {
                return true;
            } else
                return false;
        else
            return false;
    }

    boolean tcase() {
        if (terminal(TokenType.CASE)) {
            if (terminal(TokenType.LITERAL))
                if (terminal(TokenType.COLON))
                    if (stm()) {
                        if (terminal(TokenType.BREAK)) {
                            if (terminal(TokenType.SEMICOLON)) {
                                if (tcase()) {
                                    return true;
                                }

                            } else
                                return false;
                        }
                    }
            return false;
        } else
            return true;
    }

    boolean print() {
        if (terminal(TokenType.PRINTF))
            if (terminal(TokenType.LEFT_PARAN))
                if (expr())
                    if (terminal(TokenType.RIGHT_PARAN)) {
                        return true;
                    }
        return false;

    }

    boolean expr() {
        if (terminal(TokenType.IDENTIFIER)) {
            if (exprTail()) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    boolean exprTail() {
        if (terminal(TokenType.PLUS) || terminal(TokenType.MINUS)) {
            if (terminal(TokenType.IDENTIFIER)) {
                if (exprTail())
                    return true;
            }
            return false;
        } else
            return true;
    }

    boolean type() {
        if (terminal(TokenType.INT) || terminal(TokenType.CHAR)) {
            return true;
        } else
            return false;
    }

    TokenType nextToken() {

        Token t = tokens.get(ptr);
        return t.type;
    }

    boolean terminal(TokenType tt) {
        if (ptr >= tokens.size())
            return false;

        if (nextToken() == tt) {
            ptr++;
            return true;
        } else
            return false;
    }

    boolean id() {
        if (terminal(TokenType.IDENTIFIER)) {
            return true;
        } else
            return false;
    }

}