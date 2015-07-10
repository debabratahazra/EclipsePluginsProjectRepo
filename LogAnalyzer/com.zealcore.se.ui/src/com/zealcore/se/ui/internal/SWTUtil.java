/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.zealcore.se.ui.graphics.IGraphics;

/**
 * Utilitty class for SWT related tasks.
 */
public final class SWTUtil {

    private static final int THREE = 3;

    private static final int BYTE_FF = 0xFF;

    private static final int TWO = 2;

    private static final int GLOSS_ALPHA = 175;

    private static final int COORD_SIZE = 10;

    private static final int INTENSITY_LEVEL = 200;

    private SWTUtil() {}

    /**
     * Converts a {@link PathIterator} to a path.
     * 
     * @param convertOut
     *                out-variable
     * 
     * @param pathIterator
     *                the path iterator to convert
     */
    public static void convertPath(final Path convertOut,
            final PathIterator pathIterator) {
        float cx = 0;
        float cy = 0;
        float mx = 0;
        float my = 0;

        while (!pathIterator.isDone()) {
            final double[] coords = new double[SWTUtil.COORD_SIZE];
            if (pathIterator.isDone()) {
                break;
            }
            final int action = pathIterator.currentSegment(coords);
            switch (action) {
            case PathIterator.SEG_LINETO:

                cx = (float) coords[0];
                cy = (float) coords[1];
                convertOut.lineTo(cx, cy);
                break;
            case PathIterator.SEG_MOVETO:
                cx = (float) coords[0];
                cy = (float) coords[1];
                mx = cx;
                my = cy;
                convertOut.moveTo(mx, my);
                break;
            case PathIterator.SEG_CLOSE:
                convertOut.close();
                break;
            default:
            }
            pathIterator.next();
        }
    }

    /**
     * Retrieves the length of a text given a graphic context.
     * 
     * @param gc
     *                the gc
     * @param s
     *                the string
     * 
     * @return the text length
     */
    public static int textLength(final GC gc, final String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            length += gc.getAdvanceWidth(s.charAt(i));
        }
        return length;
    }

    /**
     * Retrieves the length of a text given a graphic context.
     * 
     * @param gc
     *                the gc
     * @param s
     *                the string
     * 
     * @return the text length
     */
    public static int textLength(final IGraphics gc, final String s) {
        return gc.textExtent(s).x;
    }

    /**
     * Transforms a rectangle with the specified transform.
     * 
     * @param current
     *                the current transform
     * @param rect
     *                the rect to transform
     * 
     * @return the transformed rectangle
     */
    public static Rectangle transform(final Rectangle rect,
            final Transform current) {
        final float[] points = new float[] { rect.x, rect.y,
                rect.x + rect.width, rect.y + rect.height, };

        current.transform(points);

        return new Rectangle((int) points[0], (int) points[1],
                (int) (points[1 + 1] - points[0]),
                (int) (points[1 + 1 + 1] - points[1]));

    }

    /**
     * Fills a glossy round rect.
     * 
     * @param gc
     *                the gc
     * @param rect
     *                the rect
     * @param arc
     *                the arc
     */
    public static void fillGlossRoundRect(final IGraphics gc,
            final Rectangle rect, final int arc) {
        SWTUtil.fillGlossRoundRect(gc, rect, arc, true);
    }

    /**
     * Fills a glossy round rect.
     * 
     * @param gc
     *                the gc
     * @param rect
     *                the rect
     * @param vertical
     *                true if the gloss effect is vertical
     * @param arc
     *                the arc
     */
    public static void fillGlossRoundRect(final IGraphics gc,
            final Rectangle rect, final int arc, final boolean vertical) {

        final Color foreground = gc.getForeground();
        final Color background = gc.getBackground();

        gc.setAdvanced(true);
        final int alpha = gc.getAlpha();

        gc.fillRectangle(rect);
        // Glossy top or left
        gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
        gc.setAlpha(SWTUtil.GLOSS_ALPHA);
        if (vertical) {
            gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height
                    - arc, vertical);
        } else {
            gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height,
                    vertical);
        }

        // Darker bottom or right
        gc.setForeground(background);
        gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
        gc.setAlpha(SWTUtil.GLOSS_ALPHA / (SWTUtil.TWO + 1));
        if (vertical) {
            final int half = rect.height / SWTUtil.TWO + 1;
            gc.fillGradientRectangle(rect.x, rect.y + half, rect.width,
                    half - 1, vertical);
        } else {
            final int half = rect.width / SWTUtil.TWO + 1;
            gc.fillGradientRectangle(rect.x + half, rect.y, half - 1,
                    rect.height, vertical);
        }

        gc.setAlpha(alpha);
        gc.setForeground(foreground);
        gc.setBackground(background);
    }

    /**
     * Fills a glossy round rect.
     * 
     * @param gc
     *                the gc
     * @param rect
     *                the rect
     * @param vertical
     *                true if the gloss effect is vertical
     */
    public static void fillGlossRect(final IGraphics gc, final Rectangle rect,
            final boolean vertical) {

        final Color foreground = gc.getForeground();
        final Color background = gc.getBackground();

        final int alpha = gc.getAlpha();

        gc.fillRectangle(rect);
        // Glossy top or left
        gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
        gc.setAlpha(SWTUtil.GLOSS_ALPHA);
        if (vertical) {
            gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height,
                    vertical);
        } else {
            gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height,
                    vertical);
        }

        // Darker bottom or right
        gc.setForeground(background);
        gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
        gc.setAlpha(SWTUtil.GLOSS_ALPHA / (SWTUtil.TWO + 1));
        if (vertical) {
            final int half = rect.height / SWTUtil.TWO + 1;
            gc.fillGradientRectangle(rect.x, rect.y + half, rect.width,
                    half - 1, vertical);
        } else {
            final int half = rect.width / SWTUtil.TWO + 1;
            gc.fillGradientRectangle(rect.x + half, rect.y, half - 1,
                    rect.height, vertical);
        }

        gc.setAlpha(alpha);
        gc.setForeground(foreground);
        gc.setBackground(background);
    }

    public static ImageData convertToSWT(final BufferedImage bufferedImage) {
        if (bufferedImage.getColorModel() instanceof DirectColorModel) {
            final DirectColorModel colorModel = (DirectColorModel) bufferedImage
                    .getColorModel();
            final PaletteData palette = new PaletteData(
                    colorModel.getRedMask(), colorModel.getGreenMask(),
                    colorModel.getBlueMask());
            final ImageData data = new ImageData(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), colorModel.getPixelSize(),
                    palette);
            final WritableRaster raster = bufferedImage.getRaster();
            final int[] pixelArray = new int[SWTUtil.THREE];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    final int pixel = palette.getPixel(new RGB(pixelArray[0],
                            pixelArray[1], pixelArray[SWTUtil.THREE - 1]));
                    data.setPixel(x, y, pixel);
                }
            }
            return data;
        } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
            final IndexColorModel colorModel = (IndexColorModel) bufferedImage
                    .getColorModel();
            final int size = colorModel.getMapSize();
            final byte[] reds = new byte[size];
            final byte[] greens = new byte[size];
            final byte[] blues = new byte[size];
            colorModel.getReds(reds);
            colorModel.getGreens(greens);
            colorModel.getBlues(blues);
            final RGB[] rgbs = new RGB[size];
            for (int i = 0; i < rgbs.length; i++) {
                rgbs[i] = new RGB(reds[i] & SWTUtil.BYTE_FF, greens[i]
                        & SWTUtil.BYTE_FF, blues[i] & SWTUtil.BYTE_FF);
            }
            final PaletteData palette = new PaletteData(rgbs);
            final ImageData data = new ImageData(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), colorModel.getPixelSize(),
                    palette);
            data.transparentPixel = colorModel.getTransparentPixel();
            final WritableRaster raster = bufferedImage.getRaster();
            final int[] pixelArray = new int[1];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    data.setPixel(x, y, pixelArray[0]);
                }
            }
            return data;
        }
        return null;
    }

    public static Color getTextColor(final Color background) {
        final int i = background.getRed() + background.getGreen()
                + background.getBlue();
        if (i > SWTUtil.INTENSITY_LEVEL) {
            return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
        }
        return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
    }

    public static void fillGlossRect(final IGraphics gc, final int x,
            final int y, final int width, final int height,
            final boolean vertical) {
        SWTUtil.fillGlossRect(gc, new Rectangle(x, y, width, height), vertical);
    }

    public static IEditorPart getActiveEditor() {
        if (!PlatformUI.isWorkbenchRunning()) {
            return null;
        }
        final IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (window == null) {
            return null;
        }
        final IWorkbenchPage page = window.getActivePage();
        if (page == null) {
            return null;
        }
        return page.getActiveEditor();
    }

    public static org.eclipse.draw2d.geometry.Rectangle convert(
            final Rectangle clipping) {
        return new org.eclipse.draw2d.geometry.Rectangle(clipping);
    }
}
