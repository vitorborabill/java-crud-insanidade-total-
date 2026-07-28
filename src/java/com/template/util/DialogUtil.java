package com.template.util;

import javafx.scene.control.Alert;

public class DialogUtil {
    public static void showInfo(String mensagem){
        Alert alert  = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMACAOOOOOOOO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
