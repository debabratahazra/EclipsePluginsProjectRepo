package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class GatewayFigureHelper {

	/**
	 * @param graphics
	 * @param bounds
	 * @param markerColor
	 */
	public static void drawParallelMarker(Graphics graphics, Rectangle bounds,
			Color markerColor) {
		updateBounds(bounds);
		graphics.setBackgroundColor(markerColor);
		int width = Math.max(1, bounds.width / 10);
		int points[] = new int[8];
		points[0] = (bounds.getTop().x - width) + 1;
		points[1] = bounds.getTop().y - 1;
		points[2] = bounds.getTop().x + width + 1;
		points[3] = bounds.getTop().y - 1;
		points[4] = points[2];
		points[5] = bounds.getBottom().y;
		points[6] = points[0];
		points[7] = bounds.getBottom().y;
		graphics.fillPolygon(points);
		points[0] = bounds.getLeft().x;
		points[1] = bounds.getLeft().y - width;
		points[2] = bounds.getLeft().x;
		points[3] = bounds.getLeft().y + width;
		points[4] = bounds.getRight().x + 1;
		points[5] = bounds.getRight().y + width;
		points[6] = bounds.getRight().x + 1;
		points[7] = bounds.getRight().y - width;
		graphics.fillPolygon(points);
	}

	/**
	 * @param graphics
	 * @param bounds
	 * @param markerColor
	 */
	public static void drawEventBasedXORMarker(Graphics graphics,
			Rectangle bounds, Color markerColor) {
		updateBounds(bounds);
		Color bg = graphics.getBackgroundColor();
		graphics.setBackgroundColor(markerColor);
		graphics.fillOval(bounds);
		graphics.setBackgroundColor(bg);
		int distance = bounds.width / 20 / 2;
		if (distance == 0)
			distance = 1;
		bounds.shrink(distance, distance);
		graphics.fillOval(bounds);
		bounds.shrink(distance * 2, distance * 2);
		graphics.setBackgroundColor(markerColor);
		graphics.fillOval(bounds);
		bounds.shrink(distance, distance);
		graphics.setBackgroundColor(bg);
		graphics.fillOval(bounds);
		bounds.expand((bounds.width / 20) * 3, (bounds.width / 20) * 3);
		drawMultipleMarker(graphics, bounds, markerColor);
	}

	/**
	 * @param graphics
	 * @param bounds
	 * @param markerColor
	 */
	public static void drawMultipleMarker(Graphics graphics, Rectangle bounds,
			Color markerColor) {
		graphics.setBackgroundColor(markerColor);
		int distance = bounds.width / 8 + bounds.width / 20;
		bounds.shrink(distance, distance);
		int points[] = new int[6];
		double radius = bounds.width / 2;
		int a = (int) (radius / 2D);
		int b = (int) ((radius * Math.sqrt(3D)) / 2D);
		points[0] = bounds.getCenter().x;
		points[1] = bounds.getCenter().y - (int) radius;
		points[2] = bounds.getCenter().x - b;
		points[3] = bounds.getCenter().y + a;
		points[4] = bounds.getCenter().x + b;
		points[5] = bounds.getCenter().y + a;
		graphics.fillPolygon(points);
		points[0] = bounds.getCenter().x;
		points[1] = bounds.getCenter().y + (int) radius;
		points[2] = bounds.getCenter().x - b;
		points[3] = bounds.getCenter().y - a;
		points[4] = bounds.getCenter().x + b;
		points[5] = bounds.getCenter().y - a;
		graphics.fillPolygon(points);
	}

	public static void drawInclusiveMarker(Graphics graphics, Rectangle bounds,
			Color markerColor) {
		updateBounds(bounds);
		Color bg = graphics.getBackgroundColor();
		graphics.setBackgroundColor(markerColor);
		graphics.fillOval(bounds);
		graphics.setBackgroundColor(bg);
		int distance = bounds.width / 8;
		bounds.shrink(distance, distance);
		graphics.fillOval(bounds);
	}

	public static void drawComplexMarker(Graphics graphics, Rectangle bounds) {
		drawComplexMarker(graphics, bounds, graphics.getForegroundColor());
	}

	public static void drawComplexMarker(Graphics graphics, Rectangle bounds,
			Color markerColor) {
		updateBounds(bounds);
		graphics.setBackgroundColor(markerColor);
		int width = bounds.width / 10;
		int points[] = new int[8];
		double radius = bounds.width / 2;
		int a = (int) (radius / Math.sqrt(2D));
		int b = (int) ((double) width / Math.sqrt(2D));
		points[0] = (bounds.getCenter().x + a) - b;
		points[1] = bounds.getCenter().y - a - b;
		points[2] = bounds.getCenter().x + a + b;
		points[3] = (bounds.getCenter().y - a) + b;
		points[4] = (bounds.getCenter().x - a) + b;
		points[5] = bounds.getCenter().y + a + b;
		points[6] = bounds.getCenter().x - a - b;
		points[7] = (bounds.getCenter().y + a) - b;
		graphics.fillPolygon(points);
		points[0] = bounds.getCenter().x - a - b;
		points[1] = (bounds.getCenter().y - a) + b;
		points[2] = (bounds.getCenter().x - a) + b;
		points[3] = bounds.getCenter().y - a - b;
		points[4] = bounds.getCenter().x + a + b;
		points[5] = (bounds.getCenter().y + a) - b;
		points[6] = (bounds.getCenter().x + a) - b;
		points[7] = bounds.getCenter().y + a + b;
		graphics.fillPolygon(points);
		points[0] = bounds.getTop().x - width;
		points[1] = bounds.getTop().y;
		points[2] = bounds.getTop().x + width;
		points[3] = bounds.getTop().y;
		points[4] = bounds.getBottom().x + width;
		points[5] = bounds.getBottom().y;
		points[6] = bounds.getBottom().x - width;
		points[7] = bounds.getBottom().y;
		graphics.fillPolygon(points);
		points[0] = bounds.getLeft().x;
		points[1] = bounds.getLeft().y - width;
		points[2] = bounds.getLeft().x;
		points[3] = bounds.getLeft().y + width;
		points[4] = bounds.getRight().x;
		points[5] = bounds.getRight().y + width;
		points[6] = bounds.getRight().x;
		points[7] = bounds.getRight().y - width;
		graphics.fillPolygon(points);
	}

	protected static void updateBounds(Rectangle bounds) {
		double distance = bounds.width / 20;
		bounds.shrink((int) Math.round((double) bounds.width / 4D + distance),
				(int) Math.round((double) bounds.height / 4D + distance));
		double expands = ((double) bounds.width * (Math.sqrt(2D) - 1.0D)) / 2D;
		bounds.expand((int) Math.round(expands), (int) Math.round(expands));
	}

}
