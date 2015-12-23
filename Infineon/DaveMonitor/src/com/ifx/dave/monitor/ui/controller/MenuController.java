package com.ifx.dave.monitor.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.ifx.dave.monitor.elf.model.Variable;
import com.ifx.dave.monitor.saveapplicationstate.SessionProperties;
import com.ifx.dave.monitor.ui.MainWindow;
import com.ifx.dave.monitor.ui.utils.DraggableTab;
import com.ifx.dave.monitor.ui.utils.MessageDialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class MenuController {
    int tabnum = 1;
    private SessionProperties sessionProp;

    public MenuController() {
        initializeMenuAction();
    }

    /**
     * Initialize All Menu Actions
     */
    private void initializeMenuAction() {
        newOscilloscopeMenu();
        newVariableTableMenu();
        new ThemeMenuController().themeMenuAction();
        saveSession();
    }

    private void newVariableTableMenu() {

        MainWindow.getController().getNewvariablemenu()
                .setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        tableViewTabCreater();
                    }
                });
    }

    private void saveSession() {
        MainWindow.getController().getSaveSession()
                .setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        FileChooser fileChooser = new FileChooser();

                        // Set extension filter
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                "XSPY files (*.xspy)", "*.xspy");
                        fileChooser.getExtensionFilters().add(extFilter);

                        // Show save file dialog

                        File file = fileChooser
                                .showSaveDialog(MainWindow.getPrimaryStage());
                        if (file != null) {
                            SaveFile(file);
                        }

                    }

                });

    }

    private void SaveFile(File file) {
        try {
            sessionProp = new SessionProperties();
            Kryo kryo = new Kryo();
            Output output = new Output(new FileOutputStream(file));
            kryo.writeObject(output, sessionProp);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * New Oscilloscope Tab Open in Main Window
     */
    private void newOscilloscopeMenu() {

        EventHandler<ActionEvent> newOscilloscopeAction = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                openNewOscilloscope();
            }
        };

        MainWindow.getController().getNewoscilloscopemenu()
                .setOnAction(newOscilloscopeAction);
    }

    public static void openNewOscilloscope() {

        List<Integer> openTabs = DragableTabController.getOpenTabIds();

        if (openTabs.size() >= MainWindow.MAX_OSCILLOSCOPE_TAB) {
            // MAX LIMIT EXIST
            MessageDialog.show(
                    "Maximum Tab Limits are: "
                            + MainWindow.MAX_OSCILLOSCOPE_TAB,
                    "Max Limit exit");
            return;
        }

        // Find Close Tab
        Integer tabId = DragableTabController.getClosedTabId();
        if (tabId != null) {
            // Tab Close found
            DraggableTab tab = new DraggableTab(
                    DragableTabController.getTabName(tabId));
            tab.setId(String.valueOf(tabId));
            tab.setClosable(true);
            tab.setContent(DragableTabController.getTabContents(tabId));
            try {
                MainWindow.getController().createNewTabInstance(tabId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            MainWindow.getController().getTabpane().getTabs().add(tab);
            MainWindow.getController().getTabpane().getSelectionModel()
                    .select(tab);
            DragableTabController.setOnCloseTab(tab);
            DragableTabController.addOpenTabIds(tabId);
            return;
        }

        // Open New Tab
        DraggableTab tab = new DraggableTab(
                DragableTabController.getTabName(openTabs.size() + 1));
        tab.setId(String.valueOf(openTabs.size() + 1));
        tab.setClosable(true);
        tab.setContent(
                DragableTabController.getTabContents(openTabs.size() + 1));
        try {
            MainWindow.getController()
                    .createNewTabInstance(openTabs.size() + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow.getController().getTabpane().getTabs().add(tab);
        MainWindow.getController().getTabpane().getSelectionModel().select(tab);
        DragableTabController.setOnCloseTab(tab);
        DragableTabController.addOpenTabIds(openTabs.size() + 1);

    }

    @SuppressWarnings("unchecked")
    private Tab tableViewTabCreater() {
        tabnum++;
        TabPane varTabPane = MainWindow.getController().getVariableTabPane();
        Tab varTab = new Tab();
        varTab.setText("VaraiableTab-" + (varTabPane.getTabs().size() + 1));

        varTabPane.getTabs().add(varTab);
        AnchorPane ancPane = new AnchorPane();
        ancPane.setMinHeight(0.0);
        ancPane.setMinWidth(0.0);
        ancPane.setPrefHeight(180);
        ancPane.setPrefWidth(200);
        TableView<Variable> tableView = new TableView<>();
        TableColumn<Variable, String> tname = new TableColumn<>();
        tname.setText("Name");
        tname.setPrefWidth(250);
        TableColumn<Variable, String> taddress = new TableColumn<>();
        taddress.setText("Address");
        taddress.setPrefWidth(100);
        TableColumn<Variable, String> ttype = new TableColumn<>();
        ttype.setText("Type");
        ttype.setPrefWidth(350);
        TableColumn<Variable, String> tvalue = new TableColumn<>();
        tvalue.setText("Value");
        tvalue.setPrefWidth(100);
        TableColumn<Variable, String> tscaledvalue = new TableColumn<>();
        tscaledvalue.setText("Scale");
        tscaledvalue.setPrefWidth(100);
        TableColumn<Variable, String> tunit = new TableColumn<>();
        tunit.setText("Unit");
        tunit.setPrefWidth(100);
        tableView.getColumns().addAll(tname, taddress, ttype, tvalue,
                tscaledvalue, tunit);
        varTab.setContent(tableView);
        new TableViewController().tableViewInit(tableView, tname, taddress,
                ttype, tvalue);
        tname.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variableName"));
        taddress.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("address"));
        ttype.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variableType"));
        tvalue.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("value"));
        tscaledvalue.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("scalevalue"));
        tunit.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("unit"));
        return varTab;

    }

}
