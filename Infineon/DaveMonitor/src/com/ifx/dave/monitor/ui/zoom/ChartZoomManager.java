package com.ifx.dave.monitor.ui.zoom;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ChartZoomManager {

	public static final EventHandler<MouseEvent> DEFAULT_FILTER = new EventHandler<MouseEvent>() {
		@Override
		public void handle( MouseEvent mouseEvent ) {
			//The ChartPanManager uses this reference, so if behavior changes, copy to users first.
			if ( mouseEvent.getButton() != MouseButton.PRIMARY )
				mouseEvent.consume();
		}
	};

	private final SimpleDoubleProperty rectX = new SimpleDoubleProperty();
	private final SimpleDoubleProperty rectY = new SimpleDoubleProperty();
	private final SimpleBooleanProperty selecting = new SimpleBooleanProperty( false );

	private final DoubleProperty zoomDurationMillis = new SimpleDoubleProperty( 750.0 );
	private final BooleanProperty zoomAnimated = new SimpleBooleanProperty( true );
	private final BooleanProperty mouseWheelZoomAllowed = new SimpleBooleanProperty( true );

	private static enum ZoomMode { Horizontal, Vertical, Both }

	private ZoomMode zoomMode;

	private EventHandler<? super MouseEvent> mouseFilter = DEFAULT_FILTER;

	private final EventHandlerManager handlerManager;

	private final Rectangle selectRect;
	private final ValueAxis<?> xAxis;
	private final ValueAxis<?> yAxis;
	private final XYChartInfo chartInfo;

	private final Timeline zoomAnimation = new Timeline();

	public ChartZoomManager( Pane chartPane, Rectangle selectRect, XYChart<?,?> chart ) {
		this.selectRect = selectRect;
		this.xAxis = (ValueAxis<?>) chart.getXAxis();
		this.yAxis = (ValueAxis<?>) chart.getYAxis();
		chartInfo = new XYChartInfo( chart, chartPane );

		handlerManager = new EventHandlerManager( chartPane );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( passesFilter( mouseEvent ) )
					onMousePressed( mouseEvent );
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( passesFilter( mouseEvent ) )
					onDragStart();
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				//Don't check filter here, we're either already started, or not
				onMouseDragged( mouseEvent );
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				//Don't check filter here, we're either already started, or not
				onMouseReleased();
			}
		} );

		handlerManager.addEventHandler( false, ScrollEvent.ANY, new MouseWheelZoomHandler() );
	}

	/**
	 * If true, animates the zoom.
	 */
	public boolean isZoomAnimated() {
		return zoomAnimated.get();
	}

	/**
	 * If true, animates the zoom.
	 */
	public BooleanProperty zoomAnimatedProperty() {
		return zoomAnimated;
	}

	/**
	 * If true, animates the zoom.
	 */
	public void setZoomAnimated( boolean zoomAnimated ) {
		this.zoomAnimated.set( zoomAnimated );
	}

	/**
	 * Returns the number of milliseconds the zoom animation takes.
	 */
	public double getZoomDurationMillis() {
		return zoomDurationMillis.get();
	}

	/**
	 * Returns the number of milliseconds the zoom animation takes.
	 */
	public DoubleProperty zoomDurationMillisProperty() {
		return zoomDurationMillis;
	}

	/**
	 * Sets the number of milliseconds the zoom animation takes.
	 */
	public void setZoomDurationMillis( double zoomDurationMillis ) {
		this.zoomDurationMillis.set( zoomDurationMillis );
	}

	/**
	 * If true, allow zooming via mouse wheel.
	 */
	public boolean isMouseWheelZoomAllowed() {
		return mouseWheelZoomAllowed.get();
	}

	/**
	 * If true, allow zooming via mouse wheel.
	 */
	public BooleanProperty mouseWheelZoomAllowedProperty() {
		return mouseWheelZoomAllowed;
	}

	/**
	 * If true, allow zooming via mouse wheel.
	 */
	public void setMouseWheelZoomAllowed( boolean allowed ) {
		mouseWheelZoomAllowed.set( allowed );
	}

	/**
	 * Returns the mouse filter.
	 *
	 * @see #setMouseFilter(EventHandler)
	 */
	public EventHandler<? super MouseEvent> getMouseFilter() {
		return mouseFilter;
	}

	public void setMouseFilter( EventHandler<? super MouseEvent> mouseFilter ) {
		this.mouseFilter = mouseFilter;
	}

	/**
	 * Start managing zoom management by adding event handlers and bindings as appropriate.
	 */
	public void start() {
		handlerManager.addAllHandlers();

		selectRect.widthProperty().bind( rectX.subtract( selectRect.translateXProperty() ) );
		selectRect.heightProperty().bind( rectY.subtract( selectRect.translateYProperty() ) );
		selectRect.visibleProperty().bind( selecting );
	}

	/**
	 * Stop managing zoom management by removing all event handlers and bindings, and hiding the
	 * rectangle.
	 */
	public void stop() {
		handlerManager.removeAllHandlers();
		selecting.set( false );
		selectRect.widthProperty().unbind();
		selectRect.heightProperty().unbind();
		selectRect.visibleProperty().unbind();
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

	private void onMousePressed( MouseEvent mouseEvent ) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		Rectangle2D plotArea = chartInfo.getPlotArea();

		if ( plotArea.contains( x, y ) ) {
			selectRect.setTranslateX( x );
			selectRect.setTranslateY( y );
			rectX.set( x );
			rectY.set( y );
			zoomMode = ZoomMode.Both;

		} else if ( chartInfo.getXAxisArea().contains( x, y ) ) {
			selectRect.setTranslateX( x );
			selectRect.setTranslateY( plotArea.getMinY() );
			rectX.set( x );
			rectY.set( plotArea.getMaxY() );
			zoomMode = ZoomMode.Horizontal;

		} else if ( chartInfo.getYAxisArea().contains( x, y ) ) {
			selectRect.setTranslateX( plotArea.getMinX() );
			selectRect.setTranslateY( y );
			rectX.set( plotArea.getMaxX() );
			rectY.set( y );
			zoomMode = ZoomMode.Vertical;
		}
	}

	private void onDragStart() {
		//Don't actually start the selecting process until it's officially a drag
		//But, we saved the original coordinates from where we started.
		selecting.set( true );
	}

	private void onMouseDragged( MouseEvent mouseEvent ) {
		if ( !selecting.get() )
			return;

		Rectangle2D plotArea = chartInfo.getPlotArea();

		if ( zoomMode == ZoomMode.Both || zoomMode == ZoomMode.Horizontal ) {
			double x = mouseEvent.getX();
			//Clamp to the selection start
			x = Math.max( x, selectRect.getTranslateX() );
			//Clamp to plot area
			x = Math.min( x, plotArea.getMaxX() );
			rectX.set( x );
		}

		if ( zoomMode == ZoomMode.Both || zoomMode == ZoomMode.Vertical ) {
			double y = mouseEvent.getY();
			//Clamp to the selection start
			y = Math.max( y, selectRect.getTranslateY() );
			//Clamp to plot area
			y = Math.min( y, plotArea.getMaxY() );
			rectY.set( y );
		}
	}

	private void onMouseReleased() {
		if ( !selecting.get() )
			return;

		//Prevent a silly zoom... I'm still undecided about && vs ||
		if ( selectRect.getWidth() == 0.0 ||
				 selectRect.getHeight() == 0.0 ) {
			selecting.set( false );
			return;
		}

		Rectangle2D zoomWindow = chartInfo.getDataCoordinates(
				selectRect.getTranslateX(), selectRect.getTranslateY(),
				rectX.get(), rectY.get()
		);

		xAxis.setAutoRanging( false );
		yAxis.setAutoRanging( false );
		if ( zoomAnimated.get() ) {
			zoomAnimation.stop();
			zoomAnimation.getKeyFrames().setAll(
					new KeyFrame( Duration.ZERO,
					              new KeyValue( xAxis.lowerBoundProperty(), xAxis.getLowerBound() ),
					              new KeyValue( xAxis.upperBoundProperty(), xAxis.getUpperBound() ),
					              new KeyValue( yAxis.lowerBoundProperty(), yAxis.getLowerBound() ),
					              new KeyValue( yAxis.upperBoundProperty(), yAxis.getUpperBound() )
					),
			    new KeyFrame( Duration.millis( zoomDurationMillis.get() ),
			                  new KeyValue( xAxis.lowerBoundProperty(), zoomWindow.getMinX() ),
			                  new KeyValue( xAxis.upperBoundProperty(), zoomWindow.getMaxX() ),
			                  new KeyValue( yAxis.lowerBoundProperty(), zoomWindow.getMinY() ),
			                  new KeyValue( yAxis.upperBoundProperty(), zoomWindow.getMaxY() )
			    )
			);
			zoomAnimation.play();
		} else {
			zoomAnimation.stop();
			xAxis.setLowerBound( zoomWindow.getMinX() );
			xAxis.setUpperBound( zoomWindow.getMaxX() );
			yAxis.setLowerBound( zoomWindow.getMinY() );
			yAxis.setUpperBound( zoomWindow.getMaxY() );
		}

		selecting.set( false );
	}

	private static double getBalance( double val, double min, double max ) {
		if ( val <= min )
			return 0.0;
		else if ( val >= max )
			return 1.0;

		return (val - min) / (max - min);
	}

	private class MouseWheelZoomHandler implements EventHandler<ScrollEvent> {
		private boolean ignoring = false;

		@Override
		public void handle( ScrollEvent event ) {
			EventType<? extends Event> eventType = event.getEventType();
			if ( eventType == ScrollEvent.SCROLL_STARTED ) {
				//mouse wheel events never send SCROLL_STARTED
				ignoring = true;
			} else if ( eventType == ScrollEvent.SCROLL_FINISHED ) {
				//end non-mouse wheel event
				ignoring = false;

			} else if ( eventType == ScrollEvent.SCROLL &&
			            //If we are allowing mouse wheel zooming
			            mouseWheelZoomAllowed.get() &&
			            //If we aren't between SCROLL_STARTED and SCROLL_FINISHED
			            !ignoring &&
			            //inertia from non-wheel gestures might have touch count of 0
			            !event.isInertia() &&
			            //Only care about vertical wheel events
			            event.getDeltaY() != 0 &&
			            //mouse wheel always has touch count of 0
			            event.getTouchCount() == 0 ) {

				//If we are are doing a zoom animation, stop it. Also of note is that we don't zoom the
				//mouse wheel zooming. Because the mouse wheel can "fly" and generate a lot of events,
				//animation doesn't work well. Plus, as the mouse wheel changes the view a small amount in
				//a predictable way, it "looks like" an animation when you roll it.
				//We might experiment with mouse wheel zoom animation in the future, though.
				zoomAnimation.stop();

				//If we wheel zoom on either axis, we restrict zooming to that axis only, else if anywhere
				//else, including the plot area, zoom both axes.
				ZoomMode zoomMode;
				double eventX = event.getX();
				double eventY = event.getY();
				if ( chartInfo.getXAxisArea().contains( eventX, eventY ) ) {
					zoomMode = ZoomMode.Horizontal;
				} else if ( chartInfo.getYAxisArea().contains( eventX, eventY ) ) {
					zoomMode = ZoomMode.Vertical;
				} else {
					zoomMode = ZoomMode.Both;
				}

				//At this point we are a mouse wheel event, based on everything I've read
				Point2D dataCoords = chartInfo.getDataCoordinates( eventX, eventY );

				//Determine the proportion of change to the lower and upper bounds based on how far the
				//cursor is along the axis.
				double xZoomBalance = getBalance( dataCoords.getX(),
				                                  xAxis.getLowerBound(), xAxis.getUpperBound() );
				double yZoomBalance = getBalance( dataCoords.getY(),
				                                  yAxis.getLowerBound(), yAxis.getUpperBound() );

				//Are we zooming in or out, based on the direction of the roll
				double direction = -Math.signum( event.getDeltaY() );

				//TODO: Do we need to handle "continuous" scroll wheels that don't work based on ticks?
				//If so, the 0.2 needs to be modified
				double zoomAmount = 0.2 * direction;

				if ( zoomMode == ZoomMode.Both || zoomMode == ZoomMode.Horizontal ) {
					double xZoomDelta = ( xAxis.getUpperBound() - xAxis.getLowerBound() ) * zoomAmount;
					xAxis.setAutoRanging( false );
					xAxis.setLowerBound( xAxis.getLowerBound() - xZoomDelta * xZoomBalance );
					xAxis.setUpperBound( xAxis.getUpperBound() + xZoomDelta * ( 1 - xZoomBalance ) );
				}

				if ( zoomMode == ZoomMode.Both || zoomMode == ZoomMode.Vertical ) {
					double yZoomDelta = ( yAxis.getUpperBound() - yAxis.getLowerBound() ) * zoomAmount;
					yAxis.setAutoRanging( false );
					yAxis.setLowerBound( yAxis.getLowerBound() - yZoomDelta * yZoomBalance );
					yAxis.setUpperBound( yAxis.getUpperBound() + yZoomDelta * ( 1 - yZoomBalance ) );
				}
			}
		}
	}
}
