package com.ifx.dave.monitor.ui;

import java.io.IOException;

import com.ifx.dave.monitor.elf.model.Variable;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChannelPropertiesDialogBox {

    private static ChannelPropertiesController controller;

    public static void showChannelPropertiesDialogBox(DragEvent event,
            Variable variable) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(
                MainWindow.class.getResource("ChannelProperties.fxml"));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (page == null) {
            return;
        }
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Channel Properties");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(MainWindow.getPrimaryStage());
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setX(event.getSceneX());
        dialogStage.setY(event.getSceneY());

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Add Key Listener for OK and Cancel
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    // Cancel and close the operation
                    dialogStage.close();
                    controller.setButtonType(ButtonType.CANCEL);
                }
                if (event.getCode().equals(KeyCode.ENTER)) {
                    // Accept the operation and close the dialog
                    dialogStage.close();
                    controller.setButtonType(ButtonType.OK);
                }
            }
        });

        controller = loader.getController();
        controller.showChannelPropertiesDialog(dialogStage);
        controller.setVariablenametext(variable.getVariableName());

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    public static ChannelPropertiesController getController() {
        return controller;
    }
}
