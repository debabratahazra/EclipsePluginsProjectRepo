package com.ifx.dave.monitor.ui.controller;

import com.ifx.dave.monitor.ui.MainWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;

/**
 * All the action items are coming here
 * which are coming above the LineChart
 * @author Hazra
 *
 */
public class ChannelsControllerTab4 {

	private static final int DELTA_OFFSET = 5;

	public ChannelsControllerTab4() {

	    offsetActionChannel1(MainWindow.getController().getLinechart41(),
	            MainWindow.getController().getChannel1_up_tab4(),
	            MainWindow.getController().getChannel1_down_tab4());

	    offsetActionChannel1(MainWindow.getController().getLinechart42(),
                MainWindow.getController().getChannel2_up_tab4(),
                MainWindow.getController().getChannel2_down_tab4());

	    offsetActionChannel1(MainWindow.getController().getLinechart43(),
                MainWindow.getController().getChannel3_up_tab4(),
                MainWindow.getController().getChannel3_down_tab4());

	    offsetActionChannel1(MainWindow.getController().getLinechart44(),
                MainWindow.getController().getChannel4_up_tab4(),
                MainWindow.getController().getChannel4_down_tab4());
	}

	/**
	 * Set Offset - Up and Down
	 * for LineChart
	 * @param lineChart
	 * @param up
	 * @param down
	 */
	private void offsetActionChannel1(LineChart<Number,Number> lineChart, Button up, Button down) {
	    up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                offset(lineChart, true);
            }
        });

	    down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                offset(lineChart, false);
            }
        });
    }


    /**
	 * @param lineChart => selected graph
	 * @param up_down => 'true' for Up offset movement
	 */
	private void offset(LineChart<Number, Number> lineChart, boolean up_down) {

	    lineChart.getYAxis().setAutoRanging(false);

		NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
		double newYlower = yAxis.getLowerBound(), newYupper = yAxis.getUpperBound();

		if(up_down) {
			newYlower = yAxis.getLowerBound() - DELTA_OFFSET;
			newYupper = yAxis.getUpperBound() - DELTA_OFFSET;

		} else {
		newYlower = yAxis.getLowerBound() + DELTA_OFFSET;
		newYupper = yAxis.getUpperBound() + DELTA_OFFSET;
		}

		yAxis.setLowerBound(newYlower);
		yAxis.setUpperBound(newYupper);
	}
}
