package com.ifx.dave.monitor.ui.controller;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.ifx.dave.monitor.ui.MainWindow;
import com.ifx.dave.monitor.ui.zoom.JFXChartUtil;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class LineChartControllerTab6 {

	private static final int MAX_POINTS = 900;
	private static final int WAIT_TIME = 100;

	private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch1 = FXCollections
			.observableArrayList();
	private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch2 = FXCollections
			.observableArrayList();
	private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch3 = FXCollections
			.observableArrayList();
	private ObservableList<XYChart.Series<Number, Number>> lineChartData_Ch4 = FXCollections
			.observableArrayList();

	private int xData = 0;
	private boolean firstRendering = true;

	private final ConcurrentLinkedQueue<Number> dataQ_Ch1 = new ConcurrentLinkedQueue<Number>();
	private final ConcurrentLinkedQueue<Number> dataQ_Ch2 = new ConcurrentLinkedQueue<Number>();
	private final ConcurrentLinkedQueue<Number> dataQ_Ch3 = new ConcurrentLinkedQueue<Number>();
	private final ConcurrentLinkedQueue<Number> dataQ_Ch4 = new ConcurrentLinkedQueue<Number>();
	private ExecutorService executor;
	private AddToQueue addToQueue;

	private AnimationTimer timer;

	private LineChart<Number, Number> lineChart_Ch1;
	private LineChart<Number, Number> lineChart_Ch2;
	private LineChart<Number, Number> lineChart_Ch3;
	private LineChart<Number, Number> lineChart_Ch4;

	private Series<Number, Number> channel1;
	private Series<Number, Number> channel2;
	private Series<Number, Number> channel3;
	private Series<Number, Number> channel4;

	private Label infoLabel;

	/** Constructor */
	public LineChartControllerTab6() throws Exception {
		lineChart_Ch1 = MainWindow.getController().getLinechart61();
		lineChart_Ch2 = MainWindow.getController().getLinechart62();
		lineChart_Ch3 = MainWindow.getController().getLinechart63();
		lineChart_Ch4 = MainWindow.getController().getLinechart64();
		infoLabel = MainWindow.getController().getInfolabel();
		lineChartDisplay();
		displayInfoLabel();
		zoomFunctionality();
	}

	private void zoomFunctionality() {
		JFXChartUtil.setupZooming( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		}, MainWindow.getController().getStackpane6(), lineChart_Ch1, lineChart_Ch2, lineChart_Ch3, lineChart_Ch4 );
	}

	/**
	 * Display the Coordinate of the Chart area in
	 * below-right side Label
	 */
	private void displayInfoLabel() {

		Axis<Number> xAxis = lineChart_Ch4.getXAxis();

		Axis<Number> yAxis_Ch1 = lineChart_Ch1.getYAxis();
		Axis<Number> yAxis_Ch2 = lineChart_Ch2.getYAxis();
		Axis<Number> yAxis_Ch3 = lineChart_Ch3.getYAxis();
		Axis<Number> yAxis_Ch4 = lineChart_Ch4.getYAxis();

		final Node chartBackground = lineChart_Ch4.lookup(".chart-plot-background");
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
				infoLabel.setText(String.format("Chart Coordinate : [ %.2f, %.2f ]", xAxis.getValueForDisplay(mouseEvent.getX()),
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
				infoLabel.setText(String.format("X-Axis Coordinate: [ %.2f ]", xAxis.getValueForDisplay(mouseEvent.getX())));
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
				infoLabel.setText(String.format("Channel 1 - Y-Axis: [ %.2f ]", yAxis_Ch1.getValueForDisplay(mouseEvent.getY())));
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
				infoLabel.setText(String.format("Channel 2 - Y-Axis: [ %.2f ]", yAxis_Ch2.getValueForDisplay(mouseEvent.getY())));
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
				infoLabel.setText(String.format("Channel 3 - Y-Axis: [ %.2f ]", yAxis_Ch3.getValueForDisplay(mouseEvent.getY())));
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
				infoLabel.setText(String.format("Channel 4 - Y-Axis: [ %.2f ]", yAxis_Ch4.getValueForDisplay(mouseEvent.getY())));
			}
		});

		yAxis_Ch4.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				infoLabel.setVisible(false);
			}
		});
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
			long lasttimeFPS = 0;
			int frameCnt = 0;

			@Override
			public void handle(long currenttimeNano) {

				addDataToChannels();
				// check if a second has passed
				frameCnt++;
				// long currenttimeNano = System.nanoTime();
				if (currenttimeNano > lasttimeFPS + 1000000000) {
					// print out each FPS on stdout
					System.out.println(frameCnt + " fps");

					// reset frame count and time
					frameCnt = 0;
					lasttimeFPS = currenttimeNano;
				}
			}
		};
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addDataToChannels() {
		if (dataQ_Ch1.size() > 10) {

			while (!(dataQ_Ch1.isEmpty() || dataQ_Ch2.isEmpty() || dataQ_Ch3.isEmpty()
					|| dataQ_Ch4.isEmpty() || xData >= MAX_POINTS)) {

				Number point1 = dataQ_Ch1.remove();
				Number point2 = dataQ_Ch2.remove();
				Number point3 = dataQ_Ch3.remove();
				Number point4 = dataQ_Ch4.remove();

				if (firstRendering) {
					channel1.getData().add(new LineChart.Data(xData, point1));
					channel2.getData().add(new LineChart.Data(xData, point2));
					channel3.getData().add(new LineChart.Data(xData, point3));
					channel4.getData().add(new LineChart.Data(xData, point4));

				} else {

					try {
						channel1.getData().get(xData).setYValue(point1);
						channel2.getData().get(xData).setYValue(point2);
						channel3.getData().get(xData).setYValue(point3);
						channel4.getData().get(xData).setYValue(point4);

					} catch (Exception e) {
						System.out.println(xData);
						System.out.println(e.toString());
						System.exit(-1);
					}
				}
				xData++;
			}
			if (xData >= MAX_POINTS) {
				xData = 0;
				firstRendering = false;
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
				if (dataQ_Ch1.size() <= 10) {
					try {
						for (int i = 0; i < MAX_POINTS; i++) {
							++pos;
							dataQ_Ch1.add(((Math.sin((pos * 2 * Math.PI) / PERIOD1 * 2) * (SCALE1 / 2)) + (SCALE1 / 2)));
							dataQ_Ch2.add(((Math.sin((pos * 2 * Math.PI) / PERIOD2 * 2) * (SCALE2 / 2)) + (SCALE2 / 2)));
							dataQ_Ch3.add(((Math.sin((pos * 2 * Math.PI) / PERIOD3 * 2) * (SCALE3 / 2)) + (SCALE3 / 2)));
							dataQ_Ch4.add(((Math.sin((pos * 2 * Math.PI) / PERIOD4 * 2) * (SCALE4 / 2)) + (SCALE4 / 2)));
						}
						Thread.sleep(WAIT_TIME);
					} catch (final Exception ex) {
						System.out.println(ex.toString());
					}
				}
			}
		}
	}

	public AnimationTimer getTimer_tab6() {
		return timer;

	}
}
