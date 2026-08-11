package com.template.util;

import javafx.scene.control.Alert;

public class DialogUtil {

    public static void showInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void showWarning(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void showError(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Métodos alias em português para garantir compatibilidade
    public static void mostrarInfo(String mensagem) {
        showInfo(mensagem);
    }

    public static void mostrarErro(String mensagem) {
        showError(mensagem);
    }

    public static void mostrarAviso(String mensagem) {
        showWarning(mensagem);
    }
}