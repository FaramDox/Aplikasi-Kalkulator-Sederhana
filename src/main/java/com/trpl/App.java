package com.trpl;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    // Instansiasi kelas terpisah untuk logika
    private Kalkulator kalkulator = new Kalkulator();

    @Override
    public void start(Stage stage) {
        // 1. Tampilan Layar (Display) bergaya gelap
        Label display = new Label("0");
        display.setPrefSize(240, 60);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; " +
                         "-fx-font-size: 28px; -fx-padding: 10px; -fx-background-radius: 5px;");

        // 2. GridPane untuk tombol
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(8);

        // 3. Array Multidimensi untuk 16 tombol
        String[][] buttons = {
            {"7", "8", "9", "÷"},
            {"4", "5", "6", "×"},
            {"1", "2", "3", "-"},
            {"C", "0", "=", "+"}
        };

        // 4. Membuat tombol menggunakan perulangan
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                String text = buttons[row][col];
                Button btn = new Button(text);
                btn.setPrefSize(55, 55);
                btn.setStyle("-fx-font-size: 16px; -fx-background-color: #e2e8f0; " +
                             "-fx-text-fill: #0f172a; -fx-background-radius: 5px; -fx-cursor: hand;");

                // 5. Event Handling menggunakan Lambda
                btn.setOnAction(e -> {
                    if (text.matches("[0-9]")) {
                        display.setText(kalkulator.processNumber(display.getText(), text));
                    } else if (text.equals("C")) {
                        kalkulator.clear();
                        display.setText("0");
                    } else if (text.equals("=")) {
                        display.setText(kalkulator.calculate(display.getText()));
                    } else { 
                        // Operator (÷, ×, -, +)
                        display.setText(kalkulator.processOperator(display.getText(), text));
                    }
                });

                grid.add(btn, col, row);
            }
        }

        // 6. Menyusun Layout Utama
        VBox root = new VBox(15, display, grid);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f8fafc;");

        // 7. Mengonfigurasi dan Menampilkan Scene
        Scene scene = new Scene(root, 280, 370);
        stage.setTitle("Kalkulator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}