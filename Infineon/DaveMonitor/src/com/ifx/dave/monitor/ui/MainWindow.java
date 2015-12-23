package com.ifx.dave.monitor.ui;

import java.io.IOException;

import com.ifx.dave.monitor.ui.utils.TabPaneUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private static final String FXML_UI = "davemonitor.fxml";
    private static MainWindowController controller;
    private static Scene scene;
    private static Stage primaryStage;
    private String blackTheme = getClass().getResource("./css/styles_black.css").toExternalForm();
    public static final int MAX_OSCILLOSCOPE_TAB = 8;   // Can't be more than 8.

    private static final int MAIN_WINDOW_WIDTH = 1200;
    private static final int MAIN_WINDOW_HEIGHT = 700;

    public static final int DRAG_WINDOW_WIDTH = 1200;
    public static final int DRAG_WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	MainWindow.primaryStage = primaryStage;
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_UI));
            root = loader.load();

            controller = loader.getController();
            controller.setMain(this);

            scene = new Scene(root, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
            scene.getStylesheets().add(blackTheme);
            primaryStage.setTitle("xSPY");
            primaryStage.setScene(scene);

            TabPaneUtils.setMaximizeTab(scene);
            TabPaneUtils.tabKeyListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    public static void show(String[] args) throws Exception {
        launch(args);
    }

    public static MainWindowController getController() {
        return controller;
    }

    public static Scene getScene() {
        return scene;
    }

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
