package com.template.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaInicialController {

    @FXML private Button btnComecar;

    @FXML
    public void initialize() {
        btnComecar.setOnAction(event -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/template/main.fxml"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}