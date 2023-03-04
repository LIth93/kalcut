package org.example;

import java.util.Scanner;

public class Main {
    static int index;
    static String expression;

    public static int parseNumber() {
        int result = 0;
        if (Character.isDigit(expression.charAt(index))) {
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
                if (result <= 0) {
                    throw new IllegalArgumentException("Number is less than 0");
                } else if (result > 10) {
                    throw new IllegalArgumentException("Number is greater than 10");
                }
            }
            index++;
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
                case '+' -> {
                    index++;
                    result += parseNumber();
                }
                case '-' -> {
                    index++;
                    result -= parseNumber();
                }
                case '*' -> {
                    index++;
                    result *= parseNumber();
                }
                case '/' -> {
                    index++;
                    result /= parseNumber();
                }
                default -> {
                    throw new IllegalArgumentException("Unexpected operand");
                }
            }
            if (index != expression.length()) {
                throw new IllegalArgumentException("Did not reach end of expression");
            }
            return Integer.toString(result);
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