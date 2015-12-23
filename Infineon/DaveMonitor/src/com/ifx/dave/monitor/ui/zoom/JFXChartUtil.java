package com.ifx.dave.monitor.ui.zoom;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class JFXChartUtil {

	public static Region setupZooming(EventHandler<? super MouseEvent> mouseFilter, StackPane stackPane,
					XYChart<?, ?> ...chart) {

		Rectangle selectRect = new Rectangle( 0, 0, 0, 0 );
		selectRect.setFill( Color.DODGERBLUE );
		selectRect.setMouseTransparent( true );
		selectRect.setOpacity( 0.3 );
		selectRect.setStroke( Color.rgb( 0, 0x29, 0x66 ) );
		selectRect.setStrokeType( StrokeType.INSIDE );
		selectRect.setStrokeWidth( 3.0 );
		StackPane.setAlignment( selectRect, Pos.TOP_LEFT );

		stackPane.getChildren().add( selectRect );

		for (int i = 0; i < chart.length; i++) {
			XYChart<?, ?> xyChart = chart[i];
			ChartZoomManager zoomManager = new ChartZoomManager( stackPane, selectRect, xyChart );
			zoomManager.setMouseFilter( mouseFilter );
			zoomManager.start();
		}
		return stackPane;
	}
}
