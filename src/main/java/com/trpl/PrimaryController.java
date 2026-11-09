package com.trpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField display;

    private double num1 = 0;
    private String operator = "";
    private boolean startNewInput = true;

    // Menangani penekanan tombol angka dan titik desimal
    @FXML
    private void processNumber(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        if (startNewInput) {
            if (value.equals(".")) {
                display.setText("0.");
            } else {
                display.setText(value);
            }
            startNewInput = false;
        } else {
            if (value.equals(".") && display.getText().contains(".")) {
                return; // Cegah memasukkan lebih dari satu titik desimal
            }
            display.setText(display.getText() + value);
        }
    }

    // Menangani penekanan operator (+, -, *, /, =)
    @FXML
    private void processOperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        if (!value.equals("=")) {
            if (!operator.isEmpty() && !startNewInput) {
                calculate();
            }
            operator = value;
            num1 = Double.parseDouble(display.getText());
            startNewInput = true;
        } else {
            if (operator.isEmpty()) return;
            calculate();
            operator = "";
            startNewInput = true;
        }
    }

    // Menghitung hasil operasi matematika
    private void calculate() {
        double num2 = Double.parseDouble(display.getText());
        double result = 0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    display.setText("Error");
                    return;
                }
                result = num1 / num2;
                break;
        }

        // Tampilkan sebagai integer jika hasil tidak memiliki desimal
        if (result % 1 == 0) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }
    }

    // Menangani tombol Clear (C)
    @FXML
    private void processClear(ActionEvent event) {
        display.setText("0");
        num1 = 0;
        operator = "";
        startNewInput = true;
    }
}