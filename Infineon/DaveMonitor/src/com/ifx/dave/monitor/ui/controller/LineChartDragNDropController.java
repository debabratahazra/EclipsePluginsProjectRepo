package com.ifx.dave.monitor.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ifx.dave.monitor.elf.model.Variable;
import com.ifx.dave.monitor.ui.ChannelPropertiesDialogBox;
import com.ifx.dave.monitor.ui.utils.LineChartKey;
import com.ifx.dave.monitor.ui.utils.MessageDialog;

import javafx.event.EventHandler;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class LineChartDragNDropController {

    private final int MAX_CHANNELS = 4;
    private Variable variables[];
    private int total_channels_added = 0;
    private List<Integer> remove_index = new ArrayList<Integer>();

    public LineChartDragNDropController() {
        variables = new Variable[MAX_CHANNELS];
        for (int i = 0; i < MAX_CHANNELS; i++) {
            variables[i] = null;
            remove_index.add(i);
        }
        Collections.sort(remove_index);
    }

    public void addDragNDropEventHandler(
            final LineChart<Number, Number> linechart,
            Map<LineChartKey, Series<Number, Number>> mapCharts,
            Map<LineChartKey, Button> mapButton,
            Map<LineChartKey, Axis<Number>> mapYAxis) {
        linechart.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != linechart
                        && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        linechart.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != linechart
                        && event.getDragboard().hasString()) {
                }
                event.consume();
            }
        });

        linechart.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        linechart.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                int channel_no = -1;
                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                    Object listOfVariables = db
                            .getContent(DataFormat.PLAIN_TEXT);
                    if (listOfVariables instanceof List<?>) {
                        List<?> variables = (List<?>) listOfVariables;
                        if (variables.get(0) instanceof Variable) {
                            // Handle with Variable class object only
                            Variable variable = (Variable) variables.get(0);

                            if (total_channels_added >= MAX_CHANNELS) {
                                ButtonType result = MessageDialog
                                        .showConfirmation(
                                                "Maximum " + MAX_CHANNELS
                                                        + " Channels supported. "
                                                        + "Do you want to replace with existing Channel?",
                                                "Max Limit: " + MAX_CHANNELS,
                                                null);
                                if (result.equals(ButtonType.OK)) {
                                    // Open Channel Properties DialogBox
                                    ChannelPropertiesDialogBox
                                            .showChannelPropertiesDialogBox(
                                                    event, variable);
                                    ButtonType dialog_result = ChannelPropertiesDialogBox
                                            .getController().getButtonType();
                                    if (dialog_result
                                            .equals(ButtonType.CANCEL)) {
                                        return;
                                    }
                                    channel_no = setVariables(variable);
                                    success = true;
                                } else {
                                    success = false;
                                }
                            } else {
                                ChannelPropertiesDialogBox
                                        .showChannelPropertiesDialogBox(event,
                                                variable);
                                ButtonType dialog_result = ChannelPropertiesDialogBox
                                        .getController().getButtonType();
                                if (dialog_result.equals(ButtonType.CANCEL)) {
                                    return;
                                }
                                channel_no = setVariables(variable);
                                success = true;
                            }
                        }
                    }
                }
                if (success && channel_no != -1) {
                    String color = ChannelPropertiesDialogBox.getController()
                            .getChannelcolor().getValue().toString();
                    color = color.replace("0x", "#").substring(0, 7);
                    String displayName = ChannelPropertiesDialogBox
                            .getController().getDisplaynametext().getText();
                    // Chart style
                    LineChartKey KEY = LineChartKey.getChartKey(channel_no);
                    Series<Number, Number> series = mapCharts.get(KEY);
                    String style = "-fx-stroke: " + color + ";";
                    series.getNode().setStyle(style);
                    // Button style
                    Button channel_button = mapButton.get(KEY);
                    if (!displayName.isEmpty()) {
                        channel_button.setText(displayName);
                    }
                    style = "-fx-text-fill: " + color + ";";
                    channel_button.setStyle(style);
                    // Y Axis Color
                    style = "-fx-tick-label-fill: " + color + ";"
                            + "-fx-text-label-fill: " + color + ";";
                    NumberAxis yAxis = (NumberAxis) mapYAxis.get(KEY);
                    yAxis.setStyle(style);
                    if (!displayName.isEmpty()) {
                        yAxis.setLabel(displayName);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private int setVariables(Variable variable) {
        int channel_no = -1;
        if (remove_index.isEmpty()) {
            channel_no = MAX_CHANNELS - 1;
            this.variables[channel_no] = variable;
        } else {
            channel_no = remove_index.remove(0);
            this.variables[channel_no] = variable;
            total_channels_added++;
        }
        return channel_no + 1;
    }

    public Variable[] getVariableArrayList() {
        return variables;
    }

    public void removeVariable(int index) {
        if (index < 0 || index > 3) {
            return;
        }
        variables[index] = null;
        remove_index.add(index);
        Collections.sort(remove_index);
        total_channels_added--;
    }
}
