package com.trpl;

public class Kalkulator {
    private double operand1;
    private String operator;
    private boolean startNew;

    public Kalkulator() {
        clear();
    }

    public void clear() {
        operand1 = 0;
        operator = "";
        startNew = true;
    }

    public String processNumber(String current, String digit) {
        if (current.equals("0") || startNew) {
            startNew = false;
            return digit;
        }
        return current + digit;
    }

    public String processOperator(String current, String op) {
        if (!operator.isEmpty()) {
            current = calculate(current);
        }
        operand1 = Double.parseDouble(current);
        operator = op;
        startNew = true;
        return current;
    }

    public String calculate(String current) {
        if (operator.isEmpty()) return current;
        double operand2 = Double.parseDouble(current);
        double result;
        switch (operator) {
            case "+":  result = operand1 + operand2; break;
            case "-":  result = operand1 - operand2; break;
            case "×":  result = operand1 * operand2; break;
            case "÷":  result = operand2 != 0 ? operand1 / operand2 : 0; break;
            default:   result = operand2;
        }
        operator = "";
        operand1 = result;
        startNew = true;
        // ponytail: format as int when whole number; add decimal precision control when needed
        if (result == (long) result) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }
}
