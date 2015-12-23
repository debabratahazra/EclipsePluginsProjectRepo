package com.ifx.dave.monitor.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChannelPropertiesController {

    @FXML
    TextField displaynametext;

    @FXML
    TextField variablenametext;

    @FXML
    ColorPicker channelcolor;

    @FXML
    Button okbutton;

    @FXML
    Button cancelbutton;

    private ButtonType buttonType;

    public void setButtonType(ButtonType buttonType) {
        this.buttonType = buttonType;
    }

    @FXML
    private void initialize() {
    }

    public void showChannelPropertiesDialog(final Stage dialogStage) {

        cancelbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // close the dialog
                // Don't add variable in Chart
                dialogStage.close();
                buttonType = ButtonType.CANCEL;
            }
        });

        okbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();
                buttonType = ButtonType.OK;
            }
        });
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public TextField getDisplaynametext() {
        return displaynametext;
    }

    public TextField getVariablenametext() {
        return variablenametext;
    }

    public void setVariablenametext(String variablenametext) {
        this.variablenametext.setText(variablenametext);
    }

    public ColorPicker getChannelcolor() {
        return channelcolor;
    }
}
