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

public interface IGraphics {

    void copyArea(Image image, int x, int y);

    void copyArea(int srcX, int srcY, int width, int height, int destX,
            int destY, boolean paint);

    void copyArea(int srcX, int srcY, int width, int height, int destX,
            int destY);

    void dispose();

    void drawArc(int x, int y, int width, int height, int startAngle,
            int arcAngle);

    void drawFocus(int x, int y, int width, int height);

    void drawImage(Image image, int srcX, int srcY, int srcWidth,
            int srcHeight, int destX, int destY, int destWidth, int destHeight);

    void drawImage(Image image, int x, int y);

    void drawLine(int x1, int y1, int x2, int y2);

    void drawOval(int x, int y, int width, int height);

    void drawPath(Path path);

    void drawPoint(int x, int y);

    void drawPolygon(int[] pointArray);

    void drawPolyline(int[] pointArray);

    void drawRectangle(int x, int y, int width, int height);

    void drawRectangle(Rectangle rect);

    void drawRoundRectangle(int x, int y, int width, int height, int arcWidth,
            int arcHeight);

    void drawString(String string, int x, int y, boolean isTransparent);

    void drawString(String string, int x, int y);

    void drawText(String string, int x, int y, boolean isTransparent);

    void drawText(String string, int x, int y, int flags);

    void drawText(String string, int x, int y);

    boolean equals(Object object);

    void fillArc(int x, int y, int width, int height, int startAngle,
            int arcAngle);

    void fillGradientRectangle(int x, int y, int width, int height,
            boolean vertical);

    void fillOval(int x, int y, int width, int height);

    void fillPath(Path path);

    void fillPolygon(int[] pointArray);

    void fillRectangle(int x, int y, int width, int height);

    void fillRectangle(Rectangle rect);

    void fillRoundRectangle(int x, int y, int width, int height, int arcWidth,
            int arcHeight);

    boolean getAdvanced();

    int getAdvanceWidth(char ch);

    int getAlpha();

    int getAntialias();

    Color getBackground();

    Pattern getBackgroundPattern();

    int getCharWidth(char ch);

    Rectangle getClipping();

    void getClipping(Region region);

    Device getDevice();

    int getFillRule();

    Font getFont();

    FontMetrics getFontMetrics();

    Color getForeground();

    Pattern getForegroundPattern();

    GCData getGCData();

    int getInterpolation();

    int getLineCap();

    int[] getLineDash();

    int getLineJoin();

    int getLineStyle();

    int getLineWidth();

    int getStyle();

    int getTextAntialias();

    void getTransform(Transform transform);

    boolean getXORMode();

    int hashCode();

    boolean isClipped();

    boolean isDisposed();

    void setAdvanced(boolean advanced);

    void setAlpha(int alpha);

    void setAntialias(int antialias);

    void setBackground(Color color);

    void setBackgroundPattern(Pattern pattern);

    void setClipping(int x, int y, int width, int height);

    void setClipping(Path path);

    void setClipping(Rectangle rect);

    void setClipping(Region region);

    void setFillRule(int rule);

    void setFont(Font font);

    void setForeground(Color color);

    void setForegroundPattern(Pattern pattern);

    void setInterpolation(int interpolation);

    void setLineCap(int cap);

    void setLineDash(int[] dashes);

    void setLineJoin(int join);

    void setLineStyle(int lineStyle);

    void setLineWidth(int lineWidth);

    void setTextAntialias(int antialias);

    void setTransform(Transform transform);

    void setXORMode(boolean xor);

    Point stringExtent(String string);

    Point textExtent(String string, int flags);

    Point textExtent(String string);

    String toString();

    GC getSWT();

}
