package com.ifx.dave.monitor.ui.controller;

import com.ifx.dave.monitor.ui.MainWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class ThemeMenuController {

	private static final String whiteTheme = ThemeMenuController.class.getResource("../css/styles_white.css")
			.toExternalForm();
	private static final String blackTheme = ThemeMenuController.class.getResource("../css/styles_black.css")
			.toExternalForm();
	private static final String blueTheme = ThemeMenuController.class.getResource("../css/styles_blue.css")
			.toExternalForm();

	public void themeMenuAction() {

		// Black Theme
		MainWindow.getController().getBlackthememenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Scene scene = MainWindow.getScene();
				themeChange(scene);
			}
		});

		// White Theme
		MainWindow.getController().getWhitethememenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Scene scene = MainWindow.getScene();
				themeChange(scene);
			}
		});

		// Blue Theme
		MainWindow.getController().getBluethememenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Scene scene = MainWindow.getScene();
				themeChange(scene);
			}
		});
	}

	public static void themeChange(Scene scene) {
		if(MainWindow.getController().getBluethememenu().isSelected()){
			// Blue Theme
			scene.getStylesheets().remove(whiteTheme);
			scene.getStylesheets().remove(blackTheme);
			MainWindow.getController().getLinechart11().getStylesheets().remove(whiteTheme);
			MainWindow.getController().getLinechart11().getStylesheets().remove(blackTheme);
			if (!scene.getStylesheets().contains(blueTheme)) {
				scene.getStylesheets().add(blueTheme);
			}
		}else if(MainWindow.getController().getWhitethememenu().isSelected()){
			// White Theme
			scene.getStylesheets().remove(blueTheme);
			scene.getStylesheets().remove(blackTheme);
			MainWindow.getController().getLinechart11().getStylesheets().remove(blueTheme);
			MainWindow.getController().getLinechart11().getStylesheets().remove(blackTheme);
			if (!scene.getStylesheets().contains(whiteTheme)) {
				scene.getStylesheets().add(whiteTheme);
			}
		}else if(MainWindow.getController().getBlackthememenu().isSelected()){
			// Black Theme
			scene.getStylesheets().remove(blueTheme);
			scene.getStylesheets().remove(whiteTheme);
			MainWindow.getController().getLinechart11().getStylesheets().remove(blueTheme);
			MainWindow.getController().getLinechart11().getStylesheets().remove(whiteTheme);
			if (!scene.getStylesheets().contains(blackTheme)) {
				scene.getStylesheets().add(blackTheme);
			}
		}
	}
}
