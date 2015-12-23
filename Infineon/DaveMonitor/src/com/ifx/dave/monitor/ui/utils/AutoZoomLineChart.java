package com.ifx.dave.monitor.ui.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class AutoZoomLineChart {

    public static void fitToScene(final LineChart<Number, Number> lineChart_Ch1,
            final LineChart<Number, Number> lineChart_Ch2,
            final LineChart<Number, Number> lineChart_Ch3,
            final LineChart<Number, Number> lineChart_Ch4,
            int MAX_POINTS) {

        ((NumberAxis) lineChart_Ch1.getXAxis()).setLowerBound(0);
        ((NumberAxis) lineChart_Ch1.getXAxis()).setUpperBound(MAX_POINTS);
        lineChart_Ch1.getYAxis().setAutoRanging(true);
        ObservableList<XYChart.Series<Number, Number>> data1 = lineChart_Ch1
                .getData();
        lineChart_Ch1.setData(FXCollections.<XYChart
                .Series<Number, Number>> emptyObservableList());
        lineChart_Ch1.setData(data1);

        ((NumberAxis) lineChart_Ch2.getXAxis()).setLowerBound(0);
        ((NumberAxis) lineChart_Ch2.getXAxis()).setUpperBound(MAX_POINTS);
        lineChart_Ch2.getYAxis().setAutoRanging(true);
        ObservableList<XYChart.Series<Number, Number>> data2 = lineChart_Ch2
                .getData();
        lineChart_Ch2.setData(FXCollections.<XYChart
                .Series<Number, Number>> emptyObservableList());
        lineChart_Ch2.setData(data2);

        ((NumberAxis) lineChart_Ch3.getXAxis()).setLowerBound(0);
        ((NumberAxis) lineChart_Ch3.getXAxis()).setUpperBound(MAX_POINTS);
        lineChart_Ch3.getYAxis().setAutoRanging(true);
        ObservableList<XYChart.Series<Number, Number>> data3 = lineChart_Ch3
                .getData();
        lineChart_Ch3.setData(FXCollections.<XYChart
                .Series<Number, Number>> emptyObservableList());
        lineChart_Ch3.setData(data3);

        ((NumberAxis) lineChart_Ch4.getXAxis()).setLowerBound(0);
        ((NumberAxis) lineChart_Ch4.getXAxis()).setUpperBound(MAX_POINTS);
        lineChart_Ch4.getYAxis().setAutoRanging(true);
        ObservableList<XYChart.Series<Number, Number>> data4 = lineChart_Ch4
                .getData();
        lineChart_Ch4.setData(FXCollections.<XYChart
                .Series<Number, Number>> emptyObservableList());
        lineChart_Ch4.setData(data4);

    }
}
