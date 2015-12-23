package com.ifx.dave.monitor.ui.utils;

import com.ifx.dave.monitor.ui.MainWindow;
import com.ifx.dave.monitor.ui.controller.DragableTabController;
import com.ifx.dave.monitor.ui.controller.MenuController;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class TabPaneUtils {

    private static double splitPosition = -1.0F;
    private static boolean fullScreen = false;

    public static void setMaximizeTab(Scene scene) {
        TabPane tabPane = MainWindow.getController().getTabpane();

        tabPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (event.getButton().equals(MouseButton.PRIMARY)
                        && event.getClickCount() == 2) {
                    if (!fullScreen) {
                        SplitPane splitPane = MainWindow.getController()
                                .getSplitPane();
                        splitPosition = splitPane.getDividerPositions()[0];

                        splitPane.setDividerPositions(1);
                        fullScreen = true;
                    } else {
                        normalWindowSize();
                    }

                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE) && fullScreen) {
                    normalWindowSize();
                }
            }
        });
    }

    public static void tabKeyListener() {
        TabPane tabPane = MainWindow.getController().getTabpane();
        final BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);

        tabPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.CONTROL)) {
                    ctrlPressed.set(true);
                }
            }
        });

        tabPane.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.W) && ctrlPressed.get()) {
                    // Close the current Tab
                    Tab tab = tabPane.getSelectionModel().getSelectedItem();
                    if (!tab.getId().equals("1")) {
                        DragableTabController
                                .addClosedTabId(Integer.parseInt(tab.getId()));
                        DragableTabController
                                .removeOpenTabId(Integer.parseInt(tab.getId()));
                        tabPane.getTabs().remove(tab);
                    }
                }
                if (event.getCode().equals(KeyCode.T) && ctrlPressed.get()) {
                    // Open the current Tab
                    MenuController.openNewOscilloscope();
                }
                if (event.getCode().equals(KeyCode.M) && ctrlPressed.get()) {
                    // Maximize the current Tab
                    if (!fullScreen) {
                        SplitPane splitPane = MainWindow.getController()
                                .getSplitPane();
                        splitPosition = splitPane.getDividerPositions()[0];

                        splitPane.setDividerPositions(1);
                        fullScreen = true;
                    } else {
                        normalWindowSize();
                    }
                }
            }
        });
    }

    private static void normalWindowSize() {
        SplitPane splitPane = MainWindow.getController().getSplitPane();
        splitPane.setDividerPositions(splitPosition);
        fullScreen = false;
    }
}
