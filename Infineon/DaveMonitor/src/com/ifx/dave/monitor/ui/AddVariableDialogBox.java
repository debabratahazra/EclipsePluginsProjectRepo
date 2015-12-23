package com.ifx.dave.monitor.ui;

import java.io.IOException;

import com.ifx.dave.monitor.ui.controller.ThemeMenuController;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddVariableDialogBox {

    private static final String FXML_FILE = "addvariables.fxml";
    private static final String TITLE = "Add Variables";

    public static void openVariableDialogBox() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindow.class.getResource(FXML_FILE));
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
        dialogStage.setTitle(TITLE);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(MainWindow.getPrimaryStage());
        Scene scene = new Scene(page);
        ThemeMenuController.themeChange(scene);
        dialogStage.setScene(scene);
        dialogStage.setScene(scene);

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    dialogStage.close();
                }
            }
        });

        // Set the person into the controller.
        AddVariablesController controller = loader.getController();
        controller.setElfFile();
        try {
            controller.svdtreeviewer();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }
}
