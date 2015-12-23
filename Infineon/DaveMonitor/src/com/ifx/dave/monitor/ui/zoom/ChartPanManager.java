package com.ifx.dave.monitor.ui.zoom;

import javafx.event.EventHandler;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;

public class ChartPanManager {

	public static final EventHandler<MouseEvent> DEFAULT_FILTER = ChartZoomManager.DEFAULT_FILTER;

	private final EventHandlerManager handlerManager;

	private final ValueAxis<?> xAxis;
	private final ValueAxis<?> yAxis;

	private EventHandler<? super MouseEvent> mouseFilter = DEFAULT_FILTER;

	private boolean dragging = false;

	private boolean wasXAnimated;
	private boolean wasYAnimated;

	private double lastX;
	private double lastY;

	public ChartPanManager( XYChart<?, ?> chart ) {
		handlerManager = new EventHandlerManager( chart );
		xAxis = (ValueAxis<?>) chart.getXAxis();
		yAxis = (ValueAxis<?>) chart.getYAxis();

		handlerManager.addEventHandler( false, MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( passesFilter( mouseEvent ) )
					startDrag( mouseEvent );
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				drag( mouseEvent );
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				release();
			}
		} );
	}

	public EventHandler<? super MouseEvent> getMouseFilter() {
		return mouseFilter;
	}

	public void setMouseFilter( EventHandler<? super MouseEvent> mouseFilter ) {
		this.mouseFilter = mouseFilter;
	}

	public void start() {
		handlerManager.addAllHandlers();
	}

	public void stop() {
		handlerManager.removeAllHandlers();
		release();
	}

	private boolean passesFilter( MouseEvent event ) {
		if ( mouseFilter != null ) {
			MouseEvent cloned = (MouseEvent) event.clone();
			mouseFilter.handle( cloned );
			if ( cloned.isConsumed() )
				return false;
		}

		return true;
	}

	private void startDrag( MouseEvent event ) {
		lastX = event.getX();
		lastY = event.getY();

		wasXAnimated = xAxis.getAnimated();
		wasYAnimated = yAxis.getAnimated();

		xAxis.setAnimated( false );
		xAxis.setAutoRanging( false );
		yAxis.setAnimated( false );
		yAxis.setAutoRanging( false );

		dragging = true;
	}

	private void drag( MouseEvent event ) {
		if ( !dragging )
			return;

		double dX = ( event.getX() - lastX ) / -xAxis.getScale();
		double dY = ( event.getY() - lastY ) / -yAxis.getScale();
		lastX = event.getX();
		lastY = event.getY();

		xAxis.setAutoRanging( false );
		xAxis.setLowerBound( xAxis.getLowerBound() + dX );
		xAxis.setUpperBound( xAxis.getUpperBound() + dX );

		yAxis.setAutoRanging( false );
		yAxis.setLowerBound( yAxis.getLowerBound() + dY );
		yAxis.setUpperBound( yAxis.getUpperBound() + dY );
	}

	private void release() {
		if ( !dragging )
			return;

		dragging = false;

		xAxis.setAnimated( wasXAnimated );
		yAxis.setAnimated( wasYAnimated );
	}
}
