/**
 * 
 */
package com.zealcore.se.ui.graphics;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.Transform;

/**
 * Utility class to wrap a GC to implement an interface. This makes drawing on
 * interface possible, which in turn makes mocking easier.
 * 
 * The original GC is final, which means it cannot be subclassed (or mocked).
 * 
 * @author stch
 * 
 */
public final class Graphics implements IGraphics {
    //private static final String ILLEGAL_CALL_STR = "Internal call error. All calls to methods that uses \"arc\" is illegal. The application crashes if it is run on Solaris 10.";

    private GC context;

    private Graphics() {}

    public GC getSWT() {
        return context;
    }

    public static IGraphics valueOf(final GC context) {
        final Graphics gc = new Graphics();
        gc.setContext(context);
        return gc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#copyArea(org.eclipse.swt.graphics.Image,
     *      int, int)
     */
    public void copyArea(final Image image, final int x, final int y) {
        getContext().copyArea(image, x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#copyArea(int, int, int, int, int,
     *      int, boolean)
     */
    public void copyArea(final int srcX, final int srcY, final int width,
            final int height, final int destX, final int destY,
            final boolean paint) {
        getContext().copyArea(srcX, srcY, width, height, destX, destY, paint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#copyArea(int, int, int, int, int,
     *      int)
     */
    public void copyArea(final int srcX, final int srcY, final int width,
            final int height, final int destX, final int destY) {
        getContext().copyArea(srcX, srcY, width, height, destX, destY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#dispose()
     */
    public void dispose() {
        getContext().dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawArc(int, int, int, int, int,
     *      int)
     */
    public void drawArc(final int x, final int y, final int width,
            final int height, final int startAngle, final int arcAngle) {
    // Solaris 10 crashes when using drawArc
    // getContext().drawArc(x, y, width, height, startAngle, arcAngle);
    // throw new IllegalAccessError(ILLEGAL_CALL_STR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawFocus(int, int, int, int)
     */
    public void drawFocus(final int x, final int y, final int width,
            final int height) {
        getContext().drawFocus(x, y, width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawImage(org.eclipse.swt.graphics.Image,
     *      int, int, int, int, int, int, int, int)
     */
    public void drawImage(final Image image, final int srcX, final int srcY,
            final int srcWidth, final int srcHeight, final int destX,
            final int destY, final int destWidth, final int destHeight) {
        getContext().drawImage(image, srcX, srcY, srcWidth, srcHeight, destX,
                destY, destWidth, destHeight);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawImage(org.eclipse.swt.graphics.Image,
     *      int, int)
     */
    public void drawImage(final Image image, final int x, final int y) {
        getContext().drawImage(image, x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawLine(int, int, int, int)
     */
    public void drawLine(final int x1, final int y1, final int x2, final int y2) {
        getContext().drawLine(x1, y1, x2, y2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawOval(int, int, int, int)
     */
    public void drawOval(final int x, final int y, final int width,
            final int height) {
        // Do not use "drawOvan" since Solaris 10 do not seem to support it. It
        // crashes sometimes.
        // getContext().drawOval(x + 1, y + 1, width, height);
        int radius = (width + height) / 4;
        rasterCircle(x + radius, y + radius, radius);
    }

    void rasterCircle(final int x0, final int y0, final int radius) {
        int f = 1 - radius;
        int ddFx = 1;
        int ddFy = -2 * radius;
        int x = 0;
        int y = radius;

        getContext().drawPoint(x0, y0 + radius);
        getContext().drawPoint(x0, y0 - radius);
        getContext().drawPoint(x0 + radius, y0);
        getContext().drawPoint(x0 - radius, y0);

        while (x < y) {
            assert (ddFx == 2 * x + 1);
            assert (ddFy == -2 * y);
            assert (f == x * x + y * y - radius * radius + 2 * x - y + 1);
            if (f >= 0) {
                y--;
                ddFy += 2;
                f += ddFy;
            }
            x++;
            ddFx += 2;
            f += ddFx;
            getContext().drawPoint(x0 + x, y0 + y);
            getContext().drawPoint(x0 - x, y0 + y);
            getContext().drawPoint(x0 + x, y0 - y);
            getContext().drawPoint(x0 - x, y0 - y);
            getContext().drawPoint(x0 + y, y0 + x);
            getContext().drawPoint(x0 - y, y0 + x);
            getContext().drawPoint(x0 + y, y0 - x);
            getContext().drawPoint(x0 - y, y0 - x);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawPath(org.eclipse.swt.graphics.Path)
     */
    public void drawPath(final Path path) {
        getContext().drawPath(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawPoint(int, int)
     */
    public void drawPoint(final int x, final int y) {
        getContext().drawPoint(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawPolygon(int[])
     */
    public void drawPolygon(final int[] pointArray) {
        getContext().drawPolygon(pointArray);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawPolyline(int[])
     */
    public void drawPolyline(final int[] pointArray) {
        getContext().drawPolyline(pointArray);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawRectangle(int, int, int, int)
     */
    public void drawRectangle(final int x, final int y, final int width,
            final int height) {
        getContext().drawRectangle(x, y, width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawRectangle(org.eclipse.swt.graphics.Rectangle)
     */
    public void drawRectangle(final Rectangle rect) {
        getContext().drawRectangle(rect);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawRoundRectangle(int, int, int,
     *      int, int, int)
     */
    public void drawRoundRectangle(final int x, final int y, final int width,
            final int height, final int arcWidth, final int arcHeight) {
    // Solaris 10 crashes when using drawArc
    // getContext().drawRoundRectangle(x, y, width, height, arcWidth,
    // arcHeight);
    // throw new IllegalAccessError(ILLEGAL_CALL_STR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawString(java.lang.String, int,
     *      int, boolean)
     */
    public void drawString(final String string, final int x, final int y,
            final boolean isTransparent) {
        getContext().drawString(string, x, y, isTransparent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawString(java.lang.String, int,
     *      int)
     */
    public void drawString(final String string, final int x, final int y) {
        getContext().drawString(string, x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawText(java.lang.String, int,
     *      int, boolean)
     */
    public void drawText(final String string, final int x, final int y,
            final boolean isTransparent) {
        getContext().drawText(string, x, y, isTransparent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawText(java.lang.String, int,
     *      int, int)
     */
    public void drawText(final String string, final int x, final int y,
            final int flags) {
        getContext().drawText(string, x, y, flags);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#drawText(java.lang.String, int,
     *      int)
     */
    public void drawText(final String string, final int x, final int y) {
        getContext().drawText(string, x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        return getContext().equals(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillArc(int, int, int, int, int,
     *      int)
     */
    public void fillArc(final int x, final int y, final int width,
            final int height, final int startAngle, final int arcAngle) {
    // Solaris 10 crashes when using drawArc
    // getContext().fillArc(x, y, width, height, startAngle, arcAngle);
    // throw new IllegalAccessError(ILLEGAL_CALL_STR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillGradientRectangle(int, int,
     *      int, int, boolean)
     */
    public void fillGradientRectangle(final int x, final int y,
            final int width, final int height, final boolean vertical) {
        getContext().fillGradientRectangle(x, y, width, height, vertical);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillOval(int, int, int, int)
     */
    public void fillOval(final int x, final int y, final int width,
            final int height) {
        getContext().fillOval(x, y, width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillPath(org.eclipse.swt.graphics.Path)
     */
    public void fillPath(final Path path) {
        getContext().fillPath(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillPolygon(int[])
     */
    public void fillPolygon(final int[] pointArray) {
        getContext().fillPolygon(pointArray);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillRectangle(int, int, int, int)
     */
    public void fillRectangle(final int x, final int y, final int width,
            final int height) {
        getContext().fillRectangle(x, y, width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillRectangle(org.eclipse.swt.graphics.Rectangle)
     */
    public void fillRectangle(final Rectangle rect) {
        getContext().fillRectangle(rect);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#fillRoundRectangle(int, int, int,
     *      int, int, int)
     */
    public void fillRoundRectangle(final int x, final int y, final int width,
            final int height, final int arcWidth, final int arcHeight) {
        // Solaris 10 crashes when using drawArc
    // getContext().fillRoundRectangle(x, y, width, height, arcWidth,
    // arcHeight);
    // throw new IllegalAccessError(ILLEGAL_CALL_STR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getAdvanced()
     */
    public boolean getAdvanced() {
        return getContext().getAdvanced();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getAdvanceWidth(char)
     */
    public int getAdvanceWidth(final char ch) {
        return getContext().getAdvanceWidth(ch);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getAlpha()
     */
    public int getAlpha() {
        return getContext().getAlpha();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getAntialias()
     */
    public int getAntialias() {
        return getContext().getAntialias();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getBackground()
     */
    public Color getBackground() {
        return getContext().getBackground();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getBackgroundPattern()
     */
    public Pattern getBackgroundPattern() {
        return getContext().getBackgroundPattern();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getCharWidth(char)
     */
    public int getCharWidth(final char ch) {
        return getContext().getCharWidth(ch);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getClipping()
     */
    public Rectangle getClipping() {
        return getContext().getClipping();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getClipping(org.eclipse.swt.graphics.Region)
     */
    public void getClipping(final Region region) {
        getContext().getClipping(region);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getDevice()
     */
    public Device getDevice() {
        return getContext().getDevice();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getFillRule()
     */
    public int getFillRule() {
        return getContext().getFillRule();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getFont()
     */
    public Font getFont() {
        return getContext().getFont();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getFontMetrics()
     */
    public FontMetrics getFontMetrics() {
        return getContext().getFontMetrics();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getForeground()
     */
    public Color getForeground() {
        return getContext().getForeground();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getForegroundPattern()
     */
    public Pattern getForegroundPattern() {
        return getContext().getForegroundPattern();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getGCData()
     */
    public GCData getGCData() {
        return getContext().getGCData();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getInterpolation()
     */
    public int getInterpolation() {
        return getContext().getInterpolation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getLineCap()
     */
    public int getLineCap() {
        return getContext().getLineCap();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getLineDash()
     */
    public int[] getLineDash() {
        return getContext().getLineDash();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getLineJoin()
     */
    public int getLineJoin() {
        return getContext().getLineJoin();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getLineStyle()
     */
    public int getLineStyle() {
        return getContext().getLineStyle();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getLineWidth()
     */
    public int getLineWidth() {
        return getContext().getLineWidth();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getStyle()
     */
    public int getStyle() {
        return getContext().getStyle();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getTextAntialias()
     */
    public int getTextAntialias() {
        return getContext().getTextAntialias();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getTransform(org.eclipse.swt.graphics.Transform)
     */
    public void getTransform(final Transform transform) {
        getContext().getTransform(transform);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#getXORMode()
     */
    public boolean getXORMode() {
        return getContext().getXORMode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#hashCode()
     */
    @Override
    public int hashCode() {
        return getContext().hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#isClipped()
     */
    public boolean isClipped() {
        return getContext().isClipped();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#isDisposed()
     */
    public boolean isDisposed() {
        return getContext().isDisposed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setAdvanced(boolean)
     */
    public void setAdvanced(final boolean advanced) {
        getContext().setAdvanced(advanced);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setAlpha(int)
     */
    public void setAlpha(final int alpha) {
        getContext().setAlpha(alpha);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setAntialias(int)
     */
    public void setAntialias(final int antialias) {
        getContext().setAntialias(antialias);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setBackground(org.eclipse.swt.graphics.Color)
     */
    public void setBackground(final Color color) {
        getContext().setBackground(color);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setBackgroundPattern(org.eclipse.swt.graphics.Pattern)
     */
    public void setBackgroundPattern(final Pattern pattern) {
        getContext().setBackgroundPattern(pattern);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setClipping(int, int, int, int)
     */
    public void setClipping(final int x, final int y, final int width,
            final int height) {
        getContext().setClipping(x, y, width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setClipping(org.eclipse.swt.graphics.Path)
     */
    public void setClipping(final Path path) {
        getContext().setClipping(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setClipping(org.eclipse.swt.graphics.Rectangle)
     */
    public void setClipping(final Rectangle rect) {
        getContext().setClipping(rect);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setClipping(org.eclipse.swt.graphics.Region)
     */
    public void setClipping(final Region region) {
        getContext().setClipping(region);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setFillRule(int)
     */
    public void setFillRule(final int rule) {
        getContext().setFillRule(rule);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setFont(org.eclipse.swt.graphics.Font)
     */
    public void setFont(final Font font) {
        getContext().setFont(font);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setForeground(org.eclipse.swt.graphics.Color)
     */
    public void setForeground(final Color color) {
        getContext().setForeground(color);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setForegroundPattern(org.eclipse.swt.graphics.Pattern)
     */
    public void setForegroundPattern(final Pattern pattern) {
        getContext().setForegroundPattern(pattern);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setInterpolation(int)
     */
    public void setInterpolation(final int interpolation) {
        getContext().setInterpolation(interpolation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setLineCap(int)
     */
    public void setLineCap(final int cap) {
        getContext().setLineCap(cap);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setLineDash(int[])
     */
    public void setLineDash(final int[] dashes) {
        getContext().setLineDash(dashes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setLineJoin(int)
     */
    public void setLineJoin(final int join) {
        getContext().setLineJoin(join);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setLineStyle(int)
     */
    public void setLineStyle(final int lineStyle) {
        getContext().setLineStyle(lineStyle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setLineWidth(int)
     */
    public void setLineWidth(final int lineWidth) {
        getContext().setLineWidth(lineWidth);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setTextAntialias(int)
     */
    public void setTextAntialias(final int antialias) {
        getContext().setTextAntialias(antialias);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setTransform(org.eclipse.swt.graphics.Transform)
     */
    public void setTransform(final Transform transform) {
        getContext().setTransform(transform);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#setXORMode(boolean)
     */
    @SuppressWarnings("deprecation")
    public void setXORMode(final boolean xor) {
        getContext().setXORMode(xor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#stringExtent(java.lang.String)
     */
    public Point stringExtent(final String string) {
        return getContext().stringExtent(string);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#textExtent(java.lang.String, int)
     */
    public Point textExtent(final String string, final int flags) {
        return getContext().textExtent(string, flags);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#textExtent(java.lang.String)
     */
    public Point textExtent(final String string) {
        return getContext().textExtent(string);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.views.IGraphics#toString()
     */
    @Override
    public String toString() {
        return getContext().toString();
    }

    /**
     * @param context
     *                the context to set
     */
    void setContext(final GC context) {
        this.context = context;
    }

    /**
     * @return the context
     */
    GC getContext() {
        return context;
    }

    public static IGraphics valueOf(final Image image) {
        return Graphics.valueOf(new GC(image));
    }

}
