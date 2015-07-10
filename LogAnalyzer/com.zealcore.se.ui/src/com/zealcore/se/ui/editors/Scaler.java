/*
 * 
 */
package com.zealcore.se.ui.editors;

import org.eclipse.swt.graphics.Rectangle;

/**
 * The Scaler is a class to convert value coordinates (long values) to screen
 * coordinates.
 */
class Scaler {

    private Rectangle2L bounds;

    private Rectangle2L section;

    private double scaleX = 1;

    private double scaleY = 1;

    protected Scaler() {}

    public Scaler(final Rectangle bounds, final Rectangle values) {
        this.bounds = new Rectangle2L(bounds);
        section = new Rectangle2L(values);
        calculateScaling();
    }

    public Scaler(final Rectangle2L bounds, final Rectangle2L values) {
        this.bounds = bounds;
        section = values;
        calculateScaling();
    }

    /**
     * From value to X coordinate.
     * 
     * @param value
     *                the value
     * 
     * @return the x coordinate
     */
    public double fromValueToX(final long value) {
        final long steps = value - section.x;
        final double stepValues = steps * scaleX;
        return stepValues + bounds.x;
    }

    /**
     * From value to Y coordinate.
     * 
     * @param value
     *                the value
     * 
     * @return the y coordinate
     */
    public double fromValueToY(final long value) {
        final long steps = value - section.y;
        final double stepValues = steps * scaleY;
        return stepValues + bounds.y;
    }

    /**
     * Calculates scaling factors.
     */
    private void calculateScaling() {
        scaleX = bounds.width / (double) section.width;
        scaleY = bounds.height / (double) section.height;
    }

    public static class Rectangle2L {
        private final long x;

        private final long y;

        private final long width;

        private final long height;

        public Rectangle2L(final Rectangle normal) {
            this(normal.x, normal.y, normal.width, normal.height);
        }

        public Rectangle2L(final long x, final long y, final long width,
                final long height) {

            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public long getHeight() {
            return height;
        }

        public long getWidth() {
            return width;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        /**
         * Returns a new Rectangle2L representing the specified Rectangle value.
         * 
         * @param screen
         *                the screen
         * 
         * @return the rectangle2 L
         */
        public static Rectangle2L valueOf(final Rectangle screen) {
            return new Rectangle2L(screen.x, screen.y, screen.width,
                    screen.height);

        }

    }

}
