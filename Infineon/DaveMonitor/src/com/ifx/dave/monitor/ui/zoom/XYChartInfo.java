package com.ifx.dave.monitor.ui.zoom;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

public class XYChartInfo {
	private final XYChart<?,?> chart;
	private final Node referenceNode;

	public XYChartInfo( XYChart<?, ?> chart, Node referenceNode ) {
		this.chart = chart;
		this.referenceNode = referenceNode;
	}

	public XYChartInfo( XYChart<?, ?> chart ) {
		this( chart, chart );
	}

	public XYChart<?, ?> getChart() {
		return chart;
	}

	public Node getReferenceNode() {
		return referenceNode;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public Point2D getDataCoordinates( double x, double y ) {
		Axis xAxis = chart.getXAxis();
		Axis yAxis = chart.getYAxis();

		double xStart = getXShift( xAxis, referenceNode );
		double yStart = getYShift( yAxis, referenceNode );

		return new Point2D(
				xAxis.toNumericValue( xAxis.getValueForDisplay( x - xStart ) ),
		    yAxis.toNumericValue( yAxis.getValueForDisplay( y - yStart ) )
		);
	}

	/**
	 * Given graphical coordinates in the reference's coordinate system, returns x and y axis value as
	 * a point via the {@link Axis#getValueForDisplay(double)} and {@link Axis#toNumericValue(Object)}
	 * methods.
	 *
	 * @param minX lower X value (upper left point)
	 * @param minY lower Y value (upper left point)
	 * @param maxX upper X value (bottom right point)
	 * @param maxY upper Y value (bottom right point)
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public Rectangle2D getDataCoordinates( double minX, double minY, double maxX, double maxY ) {
		if ( minX > maxX || minY > maxY ) {
			throw new IllegalArgumentException( "min > max for X and/or Y" );
		}

		Axis xAxis = chart.getXAxis();
		Axis yAxis = chart.getYAxis();

		double xStart = getXShift( xAxis, referenceNode );
		double yStart = getYShift( yAxis, referenceNode );

		double minDataX = xAxis.toNumericValue( xAxis.getValueForDisplay( minX - xStart ) );
		double maxDataX = xAxis.toNumericValue( xAxis.getValueForDisplay( maxX - xStart ) );

		//The "low" Y data value is actually at the maxY graphical location as Y graphical axis gets
		//larger as you go down on the screen.
		double minDataY = yAxis.toNumericValue( yAxis.getValueForDisplay( maxY - yStart ) );
		double maxDataY = yAxis.toNumericValue( yAxis.getValueForDisplay( minY - yStart ) );

		return new Rectangle2D( minDataX,
		                        minDataY,
		                        maxDataX - minDataX,
		                        maxDataY - minDataY );
	}

	/**
	 * Returns true if the given x and y coordinate in the reference's coordinate system is in the
	 * chart's plot area, based on the xAxis and yAxis locations. This method works regardless of
	 * the started/stopped state.
	 */
	public boolean isInPlotArea( double x, double y ) {
		return getPlotArea().contains( x, y );
	}

	/**
	 * Returns the plot area in the reference's coordinate space.
	 */
	public Rectangle2D getPlotArea() {
		Axis<?> xAxis = chart.getXAxis();
		Axis<?> yAxis = chart.getYAxis();

		double xStart = getXShift( xAxis, referenceNode );
		double yStart = getYShift( yAxis, referenceNode );

		//If the direct method to get the width (which is based on its Node dimensions) is not found to
		//be appropriate, an alternative method is commented.
		//double width = xAxis.getDisplayPosition( xAxis.toRealValue( xAxis.getUpperBound() ) );
		double width = xAxis.getWidth();
		//double height = yAxis.getDisplayPosition( yAxis.toRealValue( yAxis.getLowerBound() ) );
		double height = yAxis.getHeight();

		return new Rectangle2D( xStart, yStart, width, height );
	}

	/**
	 * Returns the X axis area in the reference's coordinate space.
	 */
	public Rectangle2D getXAxisArea() {
		return getComponentArea( chart.getXAxis() );
	}

	/**
	 * Returns the Y axis area in the reference's coordinate space.
	 */
	public Rectangle2D getYAxisArea() {
		return getComponentArea( chart.getYAxis() );
	}

	private Rectangle2D getComponentArea( Region childRegion ) {
		double xStart = getXShift( childRegion, referenceNode );
		double yStart = getYShift( childRegion, referenceNode );

		return new Rectangle2D( xStart, yStart, childRegion.getWidth(), childRegion.getHeight() );
	}

	private double getYShift(Region childRegion, Node referenceNode2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double getXShift(Region childRegion, Node referenceNode2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
