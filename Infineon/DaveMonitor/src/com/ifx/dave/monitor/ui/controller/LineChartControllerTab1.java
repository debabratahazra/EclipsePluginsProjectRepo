package com.ifx.dave.monitor.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.ifx.dave.monitor.elf.model.Variable;
import com.ifx.dave.monitor.ui.AddVariableDialogBox;
import com.ifx.dave.monitor.ui.MainWindow;
import com.ifx.dave.monitor.ui.utils.AutoZoomLineChart;
import com.ifx.dave.monitor.ui.utils.LineChartKey;
import com.ifx.dave.monitor.ui.zoom.JFXChartUtil;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class LineChartControllerTab1 {

    private static final int MAX_POINTS = 256;
    private static final int WAIT_TIME = 40;

    private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch1 = FXCollections
            .observableArrayList();
    private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch2 = FXCollections
            .observableArrayList();
    private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch3 = FXCollections
            .observableArrayList();
    private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch4 = FXCollections
            .observableArrayList();

    private int xData_ch1 = 0;
    private int xData_ch2 = 0;
    private int xData_ch3 = 0;
    private int xData_ch4 = 0;

    private boolean firstRendering_ch1 = true;
    private boolean firstRendering_ch2 = true;
    private boolean firstRendering_ch3 = true;
    private boolean firstRendering_ch4 = true;

    private final ConcurrentLinkedQueue<Number> dataQ_Ch1 = new ConcurrentLinkedQueue<Number>();
    private final ConcurrentLinkedQueue<Number> dataQ_Ch2 = new ConcurrentLinkedQueue<Number>();
    private final ConcurrentLinkedQueue<Number> dataQ_Ch3 = new ConcurrentLinkedQueue<Number>();
    private final ConcurrentLinkedQueue<Number> dataQ_Ch4 = new ConcurrentLinkedQueue<Number>();
    private ExecutorService executor;
    private AddToQueue addToQueue;

    private AnimationTimer timer;

    private final ContextMenu contextMenu = new ContextMenu();

    private LineChart<Number, Number> lineChart_Ch1;
    private LineChart<Number, Number> lineChart_Ch2;
    private LineChart<Number, Number> lineChart_Ch3;
    private LineChart<Number, Number> lineChart_Ch4;

    private Series<Number, Number> channel1;
    private Series<Number, Number> channel2;
    private Series<Number, Number> channel3;
    private Series<Number, Number> channel4;

    private Label infoLabel;
    private LineChartDragNDropController dragNDropController;
    private MenuItem removeChannel1;
    private MenuItem removeChannel2;
    private MenuItem removeChannel3;
    private MenuItem removeChannel4;

    /** Constructor */
    public LineChartControllerTab1() throws Exception {
        lineChart_Ch1 = MainWindow.getController().getLinechart11();
        lineChart_Ch2 = MainWindow.getController().getLinechart12();
        lineChart_Ch3 = MainWindow.getController().getLinechart13();
        lineChart_Ch4 = MainWindow.getController().getLinechart14();
        infoLabel = MainWindow.getController().getInfolabel();
        lineChartDisplay();
        displayInfoLabel();
        zoomFunctionality();
        autoZoomContextMenu();
        dragNDropSupport();
        addChannelsContextMenu();
        removeChannelsContextMenu();
        setVisibleOscilloscope(false, 2, 3, 4);
    }

    public void clearDataForChannels() {
        channel1.getData().clear();
        firstRendering_ch1 = true;
        xData_ch1 = 0;

        channel2.getData().clear();
        firstRendering_ch2 = true;
        xData_ch2 = 0;

        channel3.getData().clear();
        firstRendering_ch3 = true;
        xData_ch3 = 0;

        channel4.getData().clear();
        firstRendering_ch4 = true;
        xData_ch4 = 0;

        dataQ_Ch1.clear();
        dataQ_Ch2.clear();
        dataQ_Ch3.clear();
        dataQ_Ch4.clear();
    }

    private void setVisibleOscilloscope(boolean visible, int... id) {
        for (int i = 0; i < id.length; i++) {
            switch (id[i]) {
            case 4:
                if (visible) {
                    lineChart_Ch4.getYAxis().setOpacity(1);
                } else {
                    lineChart_Ch4.getYAxis().setOpacity(0);
                }
                MainWindow.getController().getChannel4_button_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel4_scale_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel4_up_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel4_down_tab1()
                        .setVisible(visible);
                break;
            case 3:
                if (visible) {
                    lineChart_Ch3.getYAxis().setOpacity(1);
                } else {
                    lineChart_Ch3.getYAxis().setOpacity(0);
                }
                MainWindow.getController().getChannel3_button_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel3_scale_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel3_up_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel3_down_tab1()
                        .setVisible(visible);
                break;
            case 2:
                if (visible) {
                    lineChart_Ch2.getYAxis().setOpacity(1);
                } else {
                    lineChart_Ch2.getYAxis().setOpacity(0);
                }
                MainWindow.getController().getChannel2_button_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel2_scale_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel2_up_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel2_down_tab1()
                        .setVisible(visible);
                break;
            case 1:
                if (visible) {
                    lineChart_Ch1.getYAxis().setOpacity(1);
                } else {
                    lineChart_Ch1.getYAxis().setOpacity(0);
                }
                MainWindow.getController().getChannel1_button_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel1_scale_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel1_up_tab1()
                        .setVisible(visible);
                MainWindow.getController().getChannel1_down_tab1()
                        .setVisible(visible);
                break;
            default:
                break;
            }
        }
    }

    private void addChannelsContextMenu() {
        MenuItem addVariableDialoBox = new MenuItem(
                "Open variable selection dialog");
        contextMenu.getItems().add(addVariableDialoBox);

        lineChart_Ch4.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getButton() == MouseButton.PRIMARY) {
                            contextMenu.hide();
                        }
                        if (t.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(lineChart_Ch4, t.getScreenX(),
                                    t.getScreenY());

                            addVariableDialoBox.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    AddVariableDialogBox
                                            .openVariableDialogBox();
                                }
                            });
                        }
                    }
                });

    }

    private void removeChannelsContextMenu() {
        Menu removeChannels = new Menu("Remove Channels >");
        contextMenu.getItems().add(removeChannels);

        removeChannel1 = new MenuItem("Channel - 1");
        removeChannels.getItems().add(removeChannel1);

        removeChannel2 = new MenuItem("Channel - 2");
        removeChannels.getItems().add(removeChannel2);

        removeChannel3 = new MenuItem("Channel - 3");
        removeChannels.getItems().add(removeChannel3);

        removeChannel4 = new MenuItem("Channel - 4");
        removeChannels.getItems().add(removeChannel4);

        removeChannel1.setVisible(false);
        removeChannel2.setVisible(false);
        removeChannel3.setVisible(false);
        removeChannel4.setVisible(false);

        lineChart_Ch4.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getButton() == MouseButton.PRIMARY) {
                            contextMenu.hide();
                        }
                        if (t.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(lineChart_Ch4, t.getScreenX(),
                                    t.getScreenY());

                            removeChannel1.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    dragNDropController.removeVariable(0);
                                    channel1.getData().clear();
                                    firstRendering_ch1 = true;
                                    xData_ch1 = 0;
                                    removeChannel1.setVisible(false);
                                }
                            });

                            removeChannel2.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    dragNDropController.removeVariable(1);
                                    channel2.getData().clear();
                                    firstRendering_ch2 = true;
                                    xData_ch2 = 0;
                                    removeChannel2.setVisible(false);
                                    setVisibleOscilloscope(false, 2);
                                }
                            });

                            removeChannel3.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    dragNDropController.removeVariable(2);
                                    channel3.getData().clear();
                                    firstRendering_ch3 = true;
                                    xData_ch3 = 0;
                                    removeChannel3.setVisible(false);
                                    setVisibleOscilloscope(false, 3);
                                }
                            });

                            removeChannel4.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    dragNDropController.removeVariable(3);
                                    channel4.getData().clear();
                                    firstRendering_ch4 = true;
                                    xData_ch4 = 0;
                                    removeChannel4.setVisible(false);
                                    setVisibleOscilloscope(false, 4);
                                }
                            });
                        }
                    }
                });
    }

    private void dragNDropSupport() {

        Map<LineChartKey, Series<Number, Number>> mapCharts = new HashMap<>();
        mapCharts.put(LineChartKey.SERIES1, channel1);
        mapCharts.put(LineChartKey.SERIES2, channel2);
        mapCharts.put(LineChartKey.SERIES3, channel3);
        mapCharts.put(LineChartKey.SERIES4, channel4);

        Map<LineChartKey, Button> mapButton = new HashMap<>();
        mapButton.put(LineChartKey.SERIES1,
                MainWindow.getController().getChannel1_button_tab1());
        mapButton.put(LineChartKey.SERIES2,
                MainWindow.getController().getChannel2_button_tab1());
        mapButton.put(LineChartKey.SERIES3,
                MainWindow.getController().getChannel3_button_tab1());
        mapButton.put(LineChartKey.SERIES4,
                MainWindow.getController().getChannel4_button_tab1());

        Map<LineChartKey, Axis<Number>> mapYAxis = new HashMap<>();
        mapYAxis.put(LineChartKey.SERIES1, lineChart_Ch1.getYAxis());
        mapYAxis.put(LineChartKey.SERIES2, lineChart_Ch2.getYAxis());
        mapYAxis.put(LineChartKey.SERIES3, lineChart_Ch3.getYAxis());
        mapYAxis.put(LineChartKey.SERIES4, lineChart_Ch4.getYAxis());

        dragNDropController = new LineChartDragNDropController();
        dragNDropController.addDragNDropEventHandler(lineChart_Ch4, mapCharts,
                mapButton, mapYAxis);
    }

    private void zoomFunctionality() {
        JFXChartUtil.setupZooming(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY
                        || mouseEvent.isShortcutDown())
                    mouseEvent.consume();
            }
        }, MainWindow.getController().getStackpane1(), lineChart_Ch1,
                lineChart_Ch2, lineChart_Ch3, lineChart_Ch4);
    }

    /**
     * Four Channel Line Chart Display
     */
    private void lineChartDisplay() {

        channel1 = new LineChart.Series<Number, Number>();
        lineChartData_Ch1.add(channel1);

        channel2 = new LineChart.Series<Number, Number>();
        lineChartData_Ch2.add(channel2);

        channel3 = new LineChart.Series<Number, Number>();
        lineChartData_Ch3.add(channel3);

        channel4 = new LineChart.Series<Number, Number>();
        lineChartData_Ch4.add(channel4);

        lineChart_Ch1.setData(lineChartData_Ch1);
        lineChart_Ch2.setData(lineChartData_Ch2);
        lineChart_Ch3.setData(lineChartData_Ch3);
        lineChart_Ch4.setData(lineChartData_Ch4);

        class MyThreadFactory implements ThreadFactory {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setPriority(Thread.MIN_PRIORITY);
                return t;
            }
        }

        executor = Executors.newCachedThreadPool(new MyThreadFactory());
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);

        timer = new AnimationTimer() {
            @Override
            public void handle(long currenttimeNano) {
                addDataToChannels();
            }
        };
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void addDataToChannels() {
        if (dragNDropController == null) {
            return;
        }
        Variable[] variablesList = dragNDropController.getVariableArrayList();
        if (variablesList == null || variablesList.length <= 0) {
            return;
        }

        if (variablesList[3] != null) {
            // Channel 4
            removeChannel4.setVisible(true);
            setVisibleOscilloscope(true, 4);
            while (!(dataQ_Ch4.isEmpty() || xData_ch4 >= MAX_POINTS)) {

                Number point4 = dataQ_Ch4.remove();

                if (firstRendering_ch4) {
                    channel4.getData()
                            .add(new LineChart.Data(xData_ch4, point4));
                } else {

                    try {
                        channel4.getData().get(xData_ch4).setYValue(point4);
                    } catch (Exception e) {
                        System.out.println(xData_ch4);
                        System.out.println(e.toString());
                    }
                }
                xData_ch4++;
            }
            if (xData_ch4 >= MAX_POINTS) {
                xData_ch4 = 0;
                firstRendering_ch4 = false;
            }
        }
        if (variablesList[2] != null) {
            // Channel 3
            removeChannel3.setVisible(true);
            setVisibleOscilloscope(true, 3);
            while (!(dataQ_Ch3.isEmpty() || xData_ch3 >= MAX_POINTS)) {

                Number point3 = dataQ_Ch3.remove();

                if (firstRendering_ch3) {
                    channel3.getData()
                            .add(new LineChart.Data(xData_ch3, point3));
                } else {

                    try {
                        channel3.getData().get(xData_ch3).setYValue(point3);
                    } catch (Exception e) {
                        System.out.println(xData_ch3);
                        System.out.println(e.toString());
                    }
                }
                xData_ch3++;
            }
            if (xData_ch3 >= MAX_POINTS) {
                xData_ch3 = 0;
                firstRendering_ch3 = false;
            }
        }
        if (variablesList[1] != null) {
            // Channel 2
            removeChannel2.setVisible(true);
            setVisibleOscilloscope(true, 2);
            while (!(dataQ_Ch2.isEmpty() || xData_ch2 >= MAX_POINTS)) {

                Number point2 = dataQ_Ch2.remove();

                if (firstRendering_ch2) {
                    channel2.getData()
                            .add(new LineChart.Data(xData_ch2, point2));
                } else {

                    try {
                        channel2.getData().get(xData_ch2).setYValue(point2);
                    } catch (Exception e) {
                        System.out.println(xData_ch2);
                        System.out.println(e.toString());
                    }
                }
                xData_ch2++;
            }
            if (xData_ch2 >= MAX_POINTS) {
                xData_ch2 = 0;
                firstRendering_ch2 = false;
            }
        }
        if (variablesList[0] != null) {
            // Channel 1
            removeChannel1.setVisible(true);
            while (!(dataQ_Ch1.isEmpty() || xData_ch1 >= MAX_POINTS)) {

                Number point1 = dataQ_Ch1.remove();

                if (firstRendering_ch1) {
                    channel1.getData()
                            .add(new LineChart.Data(xData_ch1, point1));
                } else {

                    try {
                        channel1.getData().get(xData_ch1).setYValue(point1);
                    } catch (Exception e) {
                        System.out.println(xData_ch1);
                        System.out.println(e.toString());
                    }
                }
                xData_ch1++;
            }
            if (xData_ch1 >= MAX_POINTS) {
                xData_ch1 = 0;
                firstRendering_ch1 = false;
            }
        }

    }

    private class AddToQueue implements Runnable {

        double PERIOD1 = 301;
        double PERIOD2 = 110;
        double PERIOD3 = 605;
        double PERIOD4 = 901;

        double SCALE1 = 20;
        double SCALE2 = 25;
        double SCALE3 = 30;
        double SCALE4 = 40;

        int pos = 0;

        @Override
        public void run() {
            while (true) {
                if (dragNDropController == null) {
                    continue;
                }
                Variable[] variablesList = dragNDropController
                        .getVariableArrayList();
                if (variablesList == null || variablesList.length <= 0) {
                    continue;
                }

                try {
                    for (int i = 0; i < MAX_POINTS; i++) {
                        ++pos;
                        if (variablesList[3] != null) {
                            dataQ_Ch4.add(((Math
                                    .sin((pos * 2 * Math.PI) / PERIOD4 * 2)
                                    * (SCALE4 / 2)) + (SCALE4 / 2)));
                            /*
                             * MainClass.logger.info("Channel_4:" + (Math
                             * .sin((pos * 2 * Math.PI) / PERIOD4 * 2) (SCALE4 /
                             * 2)) + (SCALE4 / 2));
                             */
                        }

                        if (variablesList[2] != null) {
                            dataQ_Ch3.add(((Math
                                    .sin((pos * 2 * Math.PI) / PERIOD3 * 2)
                                    * (SCALE3 / 2)) + (SCALE3 / 2)));
                            /*
                             * MainClass.logger.info("Channel_3:" + (Math
                             * .sin((pos * 2 * Math.PI) / PERIOD3 * 2) (SCALE3 /
                             * 2)) + (SCALE3 / 2));
                             */
                        }

                        if (variablesList[1] != null) {
                            dataQ_Ch2.add(((Math
                                    .sin((pos * 2 * Math.PI) / PERIOD2 * 2)
                                    * (SCALE2 / 2)) + (SCALE2 / 2)));
                            /*
                             * MainClass.logger.info("Channel_2:" + (Math
                             * .sin((pos * 2 * Math.PI) / PERIOD2 * 2) (SCALE2 /
                             * 2)) + (SCALE2 / 2));
                             */
                        }

                        if (variablesList[0] != null) {
                            dataQ_Ch1.add(((Math
                                    .sin((pos * 2 * Math.PI) / PERIOD1 * 2)
                                    * (SCALE1 / 2)) + (SCALE1 / 2)));
                            /*
                             * MainClass.logger.info("Channel_1:" + (Math
                             * .sin((pos * 2 * Math.PI) / PERIOD1 * 2) (SCALE1 /
                             * 2)) + (SCALE1 / 2));
                             */
                        }

                    }
                    Thread.sleep(WAIT_TIME);
                } catch (final Exception ex) {
                    System.out.println(ex.toString());
                }

            }
        }
    }

    public AnimationTimer getTimerTab1() {
        return timer;
    }

    /**
     * Fit to Screen of LineChart
     */
    private void autoZoomContextMenu() {

        MenuItem autoZoom = new MenuItem("Fit to Scene");
        contextMenu.getItems().add(autoZoom);
        lineChart_Ch4.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getButton() == MouseButton.PRIMARY) {
                            contextMenu.hide();
                        }
                        if (t.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(lineChart_Ch4, t.getScreenX(),
                                    t.getScreenY());

                            autoZoom.setOnAction(
                                    new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    String style1 = channel1.getNode()
                                            .getStyle();
                                    String style2 = channel2.getNode()
                                            .getStyle();
                                    String style3 = channel3.getNode()
                                            .getStyle();
                                    String style4 = channel4.getNode()
                                            .getStyle();
                                    AutoZoomLineChart.fitToScene(lineChart_Ch1,
                                            lineChart_Ch2, lineChart_Ch3,
                                            lineChart_Ch4, MAX_POINTS);
                                    channel1.getNode().setStyle(style1);
                                    channel2.getNode().setStyle(style2);
                                    channel3.getNode().setStyle(style3);
                                    channel4.getNode().setStyle(style4);
                                }
                            });

                        }
                    }
                });
    }

    /**
     * Display the Coordinate of the Chart area in below-right side Label
     */
    private void displayInfoLabel() {

        Axis<Number> xAxis = lineChart_Ch4.getXAxis();

        Axis<Number> yAxis_Ch1 = lineChart_Ch1.getYAxis();
        Axis<Number> yAxis_Ch2 = lineChart_Ch2.getYAxis();
        Axis<Number> yAxis_Ch3 = lineChart_Ch3.getYAxis();
        Axis<Number> yAxis_Ch4 = lineChart_Ch4.getYAxis();

        final Node chartBackground = lineChart_Ch4
                .lookup(".chart-plot-background");
        for (Node n : chartBackground.getParent().getChildrenUnmodifiable()) {
            if (n != chartBackground && n != xAxis && n != yAxis_Ch4) {
                n.setMouseTransparent(true);
            }
        }

        chartBackground.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(true);
            }
        });

        chartBackground.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setText(String.format(
                        "Chart Coordinate : [ %.2f, %.2f ]",
                        xAxis.getValueForDisplay(mouseEvent.getX()),
                        yAxis_Ch4.getValueForDisplay(mouseEvent.getY())));
            }
        });

        chartBackground.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(false);
            }
        });

        xAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(true);
            }
        });

        xAxis.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setText(String.format("X-Axis Coordinate: [ %.2f ]",
                        xAxis.getValueForDisplay(mouseEvent.getX())));
            }
        });

        xAxis.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(false);
            }
        });

        yAxis_Ch1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(true);
            }
        });

        yAxis_Ch1.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setText(String.format("Channel 1 - Y-Axis: [ %.2f ]",
                        yAxis_Ch1.getValueForDisplay(mouseEvent.getY())));
            }
        });

        yAxis_Ch1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(false);
            }
        });

        yAxis_Ch2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(true);
            }
        });

        yAxis_Ch2.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setText(String.format("Channel 2 - Y-Axis: [ %.2f ]",
                        yAxis_Ch2.getValueForDisplay(mouseEvent.getY())));
            }
        });

        yAxis_Ch2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(false);
            }
        });

        yAxis_Ch3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(true);
            }
        });

        yAxis_Ch3.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setText(String.format("Channel 3 - Y-Axis: [ %.2f ]",
                        yAxis_Ch3.getValueForDisplay(mouseEvent.getY())));
            }
        });

        yAxis_Ch3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(false);
            }
        });

        yAxis_Ch4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(true);
            }
        });

        yAxis_Ch4.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setText(String.format("Channel 4 - Y-Axis: [ %.2f ]",
                        yAxis_Ch4.getValueForDisplay(mouseEvent.getY())));
            }
        });

        yAxis_Ch4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                infoLabel.setVisible(false);
            }
        });
    }
}
