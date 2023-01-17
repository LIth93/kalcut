package org.example;

import java.util.Scanner;

public class Main {
    static int index;
    static String expression;
    static Boolean rome;
    public static String buildRomeNumeral(int value) {
        StringBuilder stringBuilder = new StringBuilder();
        if (value < 1) {
            throw new ArithmeticException("Resulting rome numeral is less than 0");
        }
        if (value >= 100) {
            stringBuilder.append('C');
            value -= 100;
        }
        if (value >= 90) {
            stringBuilder.append("XC");
            value -= 90;
        }
        if (value >= 50) {
            stringBuilder.append('L');
            value -= 50;
        }
        if (value >= 40) {
            stringBuilder.append("XL");
            value -= 40;
        }
        while (value >= 10) {
            stringBuilder.append('X');
            value -= 10;
        }
        if (value == 9) {
            stringBuilder.append("IX");
            value -= 9;
        }
        if (value >= 5) {
            stringBuilder.append('V');
            value -= 5;
        }
        if (value == 4) {
            stringBuilder.append("IV");
            value -= 4;
        }

        while (value >= 1) {
            stringBuilder.append('I');
            value -= 1;
        }
        return stringBuilder.toString();
    }
    public static int parseNumber() {
        int result = 0;
        if (Character.isDigit(expression.charAt(index))) {
            if (rome != null) {
                if (rome) {
                    throw new IllegalArgumentException("Wrong number type of the second argument");
                }
            }
            else {
                rome = false;
            }
            if (expression.charAt(index) == '1') {
                if (((index + 1) < expression.length()) && (expression.charAt(index + 1) == '0')) {
                    result = Integer.parseInt(expression.substring(index, index + 2));
                    index++;
                }
                else {
                    result = Integer.parseInt(expression.substring(index, index + 1));
                }
            }
            else {
                result = Integer.parseInt(expression.substring(index, index + 1));
            }
            index++;
        } else if ((expression.charAt(index) == 'I') || (expression.charAt(index) == 'V') || (expression.charAt(index) == 'X')) {
            if (rome != null) {
                if (!rome) {
                    throw new IllegalArgumentException("Wrong number type of the second argument");
                }
            }
            else {
                rome = true;
            }
            if (expression.charAt(index) == 'X') {
                result = 10;
                index++;
            } else if (expression.charAt(index) == 'V') {
                int numberOfIs = 0;
                result = 5;
                index++;
                while ((index < expression.length()) && expression.charAt(index) == 'I') {
                    result++;
                    numberOfIs++;
                    index++;
                }
                if (numberOfIs > 3) {
                    throw new IllegalArgumentException("More than 3 Is");
                }
            }
            else {
                index++;
                if ((index < expression.length()) && (expression.charAt(index) == 'V')) {
                    result = 4;
                    index++;
                }
                else if ((index < expression.length()) && (expression.charAt(index) == 'X')) {
                    result = 9;
                    index++;
                }
                else {
                    int numberOfIs = 1;
                    result = 1;
                    index++;
                    while ((index < expression.length()) && (expression.charAt(index) == 'I')) {
                        result++;
                        numberOfIs++;
                        index++;
                    }
                    if (numberOfIs > 3) {
                        throw new IllegalArgumentException("More than 3 Is");
                    }
                }
            }
        }
        else {
            throw new IllegalArgumentException("Not a number at index: " + index);
        }
        return result;
    }
    public static String calc(String input) {
        index = 0;
        expression = input.replace(" ", "");
        try {
            int result = parseNumber();
            switch (input.charAt(index)) {
                case '+': {
                    index++;
                    result += parseNumber();
                    break;
                }
                case '-': {
                    index++;
                    result -= parseNumber();
                    break;
                }
                case '*': {
                    index++;
                    result *= parseNumber();
                    break;
                }
                case '/': {
                    index++;
                    result /= parseNumber();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unexpected operand");
                }
            }
            if (index != expression.length()) {
                throw new IllegalArgumentException("Did not reach end of expression");
            }
            if (rome) {
                return buildRomeNumeral(result);
            }
            else {
                return Integer.toString(result);
            }
        }
        catch (StringIndexOutOfBoundsException exc) {
            throw new IllegalArgumentException("Unexpected end of expression", exc);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }
}