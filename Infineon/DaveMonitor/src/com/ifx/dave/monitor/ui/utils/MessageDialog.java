package com.ifx.dave.monitor.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MessageDialog {

    public static void show(String infoMessage, String titleBar) {
        /*
         * By specifying a null headerMessage String, we cause the dialog to not
         * have a header
         */
        showInformation(infoMessage, titleBar, null);
    }

    public static void showInformation(String infoMessage, String titleBar,
            String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void showError(String infoMessage, String titleBar,
            String headerMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void showWarning(String infoMessage, String titleBar,
            String headerMessage) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static ButtonType showConfirmation(String infoMessage,
            String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
        return alert.getResult();
    }
}
