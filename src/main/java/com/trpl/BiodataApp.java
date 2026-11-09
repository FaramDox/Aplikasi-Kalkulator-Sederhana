package com.trpl;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BiodataApp extends Application {

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15); 
        grid.setVgap(10); 
        grid.setStyle("-fx-background-color: white;");

        Label lblNama = new Label("Nama");
        TextField txtNama = new TextField();
        txtNama.setPromptText("Contoh: Damar Adin Firdaus");
        txtNama.setPrefWidth(220);
        
        Label lblNim = new Label("NIM");
        TextField txtNim = new TextField();
        txtNim.setPromptText("Contoh: 25783110");

        Label lblProdi = new Label("Prodi");
        ComboBox<String> cmbProdi = new ComboBox<>();
        cmbProdi.getItems().addAll(
            "Teknologi Rekayasa Perangkat Lunak",
            "Manajemen Informatika", 
            "Sistem Informasi"
        );
        cmbProdi.setPromptText("Pilih Prodi");
        cmbProdi.setPrefWidth(220);

        Button btnSimpan = new Button("Simpan");
        btnSimpan.setPrefWidth(220);
        btnSimpan.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        Label lblOutput = new Label();
        lblOutput.setPrefWidth(220);
        lblOutput.setPadding(new Insets(5, 10, 5, 10));
        lblOutput.setVisible(false); 

        grid.add(lblNama, 0, 0);
        grid.add(txtNama, 1, 0);
        
        grid.add(lblNim, 0, 1);
        grid.add(txtNim, 1, 1);
        
        grid.add(lblProdi, 0, 2);
        grid.add(cmbProdi, 1, 2);
        
        grid.add(btnSimpan, 1, 3);
        grid.add(lblOutput, 1, 4);

        btnSimpan.setOnAction(e -> {
            String nama = txtNama.getText().trim();
            String nim = txtNim.getText().trim();
            String prodi = cmbProdi.getValue();

            if (nama.isEmpty() || nim.isEmpty() || prodi == null) {
                lblOutput.setText("Peringatan: Ada isian kosong!");
                lblOutput.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-background-radius: 4px;");
                lblOutput.setVisible(true);
            } else {
                String singkatanProdi = prodi.equals("Teknologi Rekayasa Perangkat Lunak") ? "TRPL" : prodi;
                lblOutput.setText(nama + " (" + singkatanProdi + ") tersimpan");
                lblOutput.setStyle("-fx-background-color: #ffedd5; -fx-text-fill: #431407; -fx-background-radius: 4px;");
                lblOutput.setVisible(true);
            }
        });

        Scene scene = new Scene(grid, 340, 220);
        stage.setTitle("Biodata - Damar Adin Firdaus");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}