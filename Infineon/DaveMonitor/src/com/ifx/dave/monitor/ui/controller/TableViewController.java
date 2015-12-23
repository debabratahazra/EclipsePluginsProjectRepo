package com.ifx.dave.monitor.ui.controller;

import java.util.Iterator;
import java.util.List;

import com.ifx.dave.monitor.elf.model.Variable;
import com.ifx.dave.monitor.ui.MainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class TableViewController {

    private ObservableList<Variable> tableList = FXCollections
            .observableArrayList();
    private Button removevariable = MainWindow.getController()
            .getRemovevariable();

    public void tableViewInit(TableView<Variable> tableview,
            TableColumn<Variable, String> tname,
            TableColumn<Variable, String> taddress,
            TableColumn<Variable, String> ttype,
            TableColumn<Variable, String> tvalue) {

        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableview.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != tableview
                        && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        tableview.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != tableview
                        && event.getDragboard().hasString()) {
                }
                event.consume();
            }
        });

        tableview.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        tableview.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                    Object listOfVariables = db
                            .getContent(DataFormat.PLAIN_TEXT);
                    if (listOfVariables instanceof List<?>) {
                        List<?> variables = (List<?>) listOfVariables;
                        for (Iterator<?> iterator = variables
                                .iterator(); iterator.hasNext();) {
                            Object object = (Object) iterator.next();
                            if (object instanceof Variable) {
                                Variable variable = (Variable) object;
                                tableList.add(variable);
                            }
                        }
                    }
                    tableview.setItems(tableList);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        tableview.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getButton() == MouseButton.PRIMARY) {

                            removevariable.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    final ObservableList<Variable> selectedVariableList = FXCollections
                                            .observableArrayList(tableview
                                                    .getSelectionModel()
                                                    .getSelectedItems());
                                    if (selectedVariableList != null) {
                                        tableList.removeAll(
                                                selectedVariableList);
                                        tableview.getSelectionModel()
                                                .clearSelection();
                                    }
                                }
                            });

                        }
                    }

                });

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem removeVaraible = new MenuItem("Remove Variable");
        contextMenu.getItems().add(removeVaraible);
        tableview.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getButton() == MouseButton.PRIMARY) {
                            contextMenu.hide();
                        }
                        if (t.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(tableview, t.getScreenX(),
                                    t.getScreenY());
                            final ObservableList<Variable> selectedVariableList = FXCollections
                                    .observableArrayList(
                                            tableview.getSelectionModel()
                                                    .getSelectedItems());

                            removeVaraible.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    if (selectedVariableList != null) {

                                        tableList.removeAll(
                                                selectedVariableList);
                                        tableview.getSelectionModel()
                                                .clearSelection();

                                    }
                                }
                            });

                        }
                    }
                });

    }
}
