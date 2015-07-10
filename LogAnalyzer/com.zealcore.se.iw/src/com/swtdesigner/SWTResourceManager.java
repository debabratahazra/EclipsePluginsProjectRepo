package com.swtdesigner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;

/**
 * Utility class for managing OS resources associated with SWT controls such as
 * colors, fonts, images, etc.
 * 
 * !!! IMPORTANT !!! Application code must explicitly invoke the
 * <code>dispose()</code> method to release the operating system resources
 * managed by cached objects when those objects and OS resources are no longer
 * needed (e.g. on application shutdown)
 * 
 * This class may be freely distributed as part of any application or plugin.
 * <p>
 * Copyright (c) 2003 - 2005, Instantiations, Inc. <br>
 * All Rights Reserved
 * 
 * @author scheglov_ke
 * @author Dan Rubel
 */
public final class SWTResourceManager {

    private static final String PROBABLY_ON_A_NON_WINDOWS_PLATFORM = " (probably on a non-Windows platform). ";

    private static final String UNABLE_TO_SET_UNDERLINE_OR_STRIKEOUT = "Unable to set underline or strikeout";

    private SWTResourceManager() {}

    private static final int TWENTY = 20;

    /**
     * Dispose of cached objects and their underlying OS resources. This should
     * only be called when the cached objects are no longer needed (e.g. on
     * application shutdown)
     */
    public static void dispose() {
        SWTResourceManager.disposeColors();
        SWTResourceManager.disposeFonts();
        SWTResourceManager.disposeImages();
        SWTResourceManager.disposeCursors();
    }

    // ////////////////////////////
    // Color support
    // ////////////////////////////

    /**
     * Maps RGB values to colors
     */
    private static HashMap<RGB, Color> mColorMap = new HashMap<RGB, Color>();

    /**
     * Returns the system color matching the specific ID
     * 
     * @param systemColorID
     *                int The ID value for the color
     * @return Color The system color matching the specific ID
     */
    public static Color getColor(final int systemColorID) {
        final Display display = Display.getCurrent();
        return display.getSystemColor(systemColorID);
    }

    /**
     * Returns a color given its red, green and blue component values
     * 
     * @param r
     *                int The red component of the color
     * @param g
     *                int The green component of the color
     * @param b
     *                int The blue component of the color
     * @return Color The color matching the given red, green and blue componet
     *         values
     */
    public static Color getColor(final int r, final int g, final int b) {
        return SWTResourceManager.getColor(new RGB(r, g, b));
    }

    /**
     * Returns a color given its RGB value
     * 
     * @param rgb
     *                RGB The RGB value of the color
     * @return Color The color matching the RGB value
     */
    public static Color getColor(final RGB rgb) {
        Color color = SWTResourceManager.mColorMap.get(rgb);
        if (color == null) {
            final Display display = Display.getCurrent();
            color = new Color(display, rgb);
            SWTResourceManager.mColorMap.put(rgb, color);
        }
        return color;
    }

    /**
     * Dispose of all the cached colors
     */
    public static void disposeColors() {
        for (final Color color : SWTResourceManager.mColorMap.values()) {
            color.dispose();
        }
        SWTResourceManager.mColorMap.clear();
    }

    // ////////////////////////////
    // Image support
    // ////////////////////////////

    /**
     * Maps image names to images
     */
    private static HashMap<String, Image> mClassImageMap = new HashMap<String, Image>();

    /**
     * Maps images to image decorators
     */
    private static HashMap<Image, HashMap<Image, Image>> mImageToDecoratorMap = new HashMap<Image, HashMap<Image, Image>>();

    /**
     * Returns an image encoded by the specified input stream
     * 
     * @param is
     *                InputStream The input stream encoding the image data
     * @return Image The image encoded by the specified input stream
     */
    protected static Image getImage(final InputStream is) {
        final Display display = Display.getCurrent();
        final ImageData data = new ImageData(is);
        if (data.transparentPixel > 0) {
            return new Image(display, data, data.getTransparencyMask());
        }
        return new Image(display, data);
    }

    /**
     * Returns an image stored in the file at the specified path
     * 
     * @param path
     *                String The path to the image file
     * @return Image The image stored in the file at the specified path
     */
    public static Image getImage(final String path) {
        return SWTResourceManager.getImage("default", path);
        //$NON-NLS-1$
    }

    /**
     * Returns an image stored in the file at the specified path
     * 
     * @param section
     *                The section to which belongs specified image
     * @param path
     *                String The path to the image file
     * @return Image The image stored in the file at the specified path
     */
    public static Image getImage(final String section, final String path) {
        final String key = section + '|' + SWTResourceManager.class.getName()
                + '|' + path;
        Image image = SWTResourceManager.mClassImageMap.get(key);
        if (image == null) {
            try {
                final FileInputStream fis = new FileInputStream(path);
                image = SWTResourceManager.getImage(fis);
                SWTResourceManager.mClassImageMap.put(key, image);
                try {
                    fis.close();
                } catch (final IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (final FileNotFoundException e) {
                image = SWTResourceManager.getMissingImage();
                SWTResourceManager.mClassImageMap.put(key, image);
            }
        }
        return image;
    }

    /**
     * Returns an image stored in the file at the specified path relative to the
     * specified class
     * 
     * @param clazz
     *                Class The class relative to which to find the image
     * @param path
     *                String The path to the image file
     * @return Image The image stored in the file at the specified path
     */
    public static Image getImage(final Class<?> clazz, final String path) {
        final String key = clazz.getName() + '|' + path;
        Image image = SWTResourceManager.mClassImageMap.get(key);
        if (image == null) {
            try {
                if ((path.length() > 0) && (path.charAt(0) == '/')) {
                    final String newPath = path.substring(1, path.length());
                    image = SWTResourceManager
                            .getImage(new BufferedInputStream(clazz
                                    .getClassLoader().getResourceAsStream(
                                            newPath)));
                } else {
                    image = SWTResourceManager.getImage(clazz
                            .getResourceAsStream(path));
                }
                SWTResourceManager.mClassImageMap.put(key, image);
            } catch (final IllegalArgumentException e) {
                image = SWTResourceManager.getMissingImage();
                SWTResourceManager.mClassImageMap.put(key, image);
            }
        }
        return image;
    }

    private static final int MISSING_IMAGE_SIZE = 10;

    private static Image getMissingImage() {
        final Image image = new Image(Display.getCurrent(),
                SWTResourceManager.MISSING_IMAGE_SIZE,
                SWTResourceManager.MISSING_IMAGE_SIZE);
        //
        final GC gc = new GC(image);
        gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
        gc.fillRectangle(0, 0, SWTResourceManager.MISSING_IMAGE_SIZE,
                SWTResourceManager.MISSING_IMAGE_SIZE);
        gc.dispose();
        //
        return image;
    }

    /**
     * Style constant for placing decorator image in top left corner of base
     * image.
     */
    public static final int TOP_LEFT = 1;

    /**
     * Style constant for placing decorator image in top right corner of base
     * image.
     */
    public static final int TOP_RIGHT = 2;

    /**
     * Style constant for placing decorator image in bottom left corner of base
     * image.
     */
    public static final int BOTTOM_LEFT = 3;

    /**
     * Style constant for placing decorator image in bottom right corner of base
     * image.
     */
    public static final int BOTTOM_RIGHT = 4;

    /**
     * Returns an image composed of a base image decorated by another image
     * 
     * @param baseImage
     *                Image The base image that should be decorated
     * @param decorator
     *                Image The image to decorate the base image
     * @return Image The resulting decorated image
     */
    public static Image decorateImage(final Image baseImage,
            final Image decorator) {
        return SWTResourceManager.decorateImage(baseImage, decorator,
                SWTResourceManager.BOTTOM_RIGHT);
    }

    /**
     * Returns an image composed of a base image decorated by another image
     * 
     * @param baseImage
     *                Image The base image that should be decorated
     * @param decorator
     *                Image The image to decorate the base image
     * @param corner
     *                The corner to place decorator image
     * @return Image The resulting decorated image
     */
    public static Image decorateImage(final Image baseImage,
            final Image decorator, final int corner) {
        HashMap<Image, Image> decoratedMap = SWTResourceManager.mImageToDecoratorMap
                .get(baseImage);
        if (decoratedMap == null) {
            decoratedMap = new HashMap<Image, Image>();
            SWTResourceManager.mImageToDecoratorMap
                    .put(baseImage, decoratedMap);
        }
        Image result = decoratedMap.get(decorator);
        if (result == null) {
            final Rectangle bid = baseImage.getBounds();
            final Rectangle did = decorator.getBounds();
            final Point baseImageSize = new Point(bid.width, bid.height);
            final CompositeImageDescriptor compositImageDesc = new CompositeImageDescriptor() {
                @Override
                protected void drawCompositeImage(final int width,
                        final int height) {
                    this.drawImage(baseImage.getImageData(), 0, 0);
                    if (corner == SWTResourceManager.TOP_LEFT) {
                        this.drawImage(decorator.getImageData(), 0, 0);
                    } else if (corner == SWTResourceManager.TOP_RIGHT) {
                        this.drawImage(decorator.getImageData(), bid.width
                                - did.width - 1, 0);
                    } else if (corner == SWTResourceManager.BOTTOM_LEFT) {
                        this.drawImage(decorator.getImageData(), 0, bid.height
                                - did.height - 1);
                    } else if (corner == SWTResourceManager.BOTTOM_RIGHT) {
                        this.drawImage(decorator.getImageData(), bid.width
                                - did.width - 1, bid.height - did.height - 1);
                    }
                }

                @Override
                protected Point getSize() {
                    return baseImageSize;
                }
            };
            result = compositImageDesc.createImage();
            decoratedMap.put(decorator, result);
        }
        return result;
    }

    /**
     * Dispose all of the cached images
     */
    public static void disposeImages() {
        for (final Image image : SWTResourceManager.mClassImageMap.values()) {
            image.dispose();
        }
        SWTResourceManager.mClassImageMap.clear();
        //
        for (final HashMap<Image, Image> decoratedMap : SWTResourceManager.mImageToDecoratorMap
                .values()) {
            for (final Image image : decoratedMap.values()) {
                image.dispose();
            }
        }
    }

    /**
     * Dispose cached images in specified section
     * 
     * @param section
     *                the section do dispose
     */
    public static void disposeImages(final String section) {
        for (final Iterator<String> i = SWTResourceManager.mClassImageMap
                .keySet().iterator(); i.hasNext();) {
            final String key = i.next();
            if (!key.startsWith(section + '|')) {
                continue;
            }
            final Image image = SWTResourceManager.mClassImageMap.get(key);
            image.dispose();
            i.remove();
        }
    }

    // ////////////////////////////
    // Font support
    // ////////////////////////////

    /**
     * Maps font names to fonts
     */
    private static HashMap<String, Font> mFontMap = new HashMap<String, Font>();

    /**
     * Maps fonts to their bold versions
     */
    private static HashMap<Font, Font> mFontToBoldFontMap = new HashMap<Font, Font>();

    /**
     * Returns a font based on its name, height and style
     * 
     * @param name
     *                String The name of the font
     * @param height
     *                int The height of the font
     * @param style
     *                int The style of the font
     * @return Font The font matching the name, height and style
     */
    public static Font getFont(final String name, final int height,
            final int style) {
        return SWTResourceManager.getFont(name, height, style, false, false);
    }

    /**
     * Returns a font based on its name, height and style. Windows-specific
     * strikeout and underline flags are also supported.
     * 
     * @param name
     *                String The name of the font
     * @param size
     *                int The size of the font
     * @param style
     *                int The style of the font
     * @param strikeout
     *                boolean The strikeout flag (warning: Windows only)
     * @param underline
     *                boolean The underline flag (warning: Windows only)
     * @return Font The font matching the name, height, style, strikeout and
     *         underline
     */
    public static Font getFont(final String name, final int size,
            final int style, final boolean strikeout, final boolean underline) {
        final String fontName = name + '|' + size + '|' + style + '|'
                + strikeout + '|' + underline;
        Font font = SWTResourceManager.mFontMap.get(fontName);
        if (font == null) {
            final FontData fontData = new FontData(name, size, style);
            if (strikeout || underline) {
                try {
                    final Class<?> logFontClass = Class
                            .forName("org.eclipse.swt.internal.win32.LOGFONT");
                    //$NON-NLS-1$
                    final Object logFont = FontData.class.getField("data").get(
                            fontData);
                    //$NON-NLS-1$
                    if ((logFont != null) && (logFontClass != null)) {
                        if (strikeout) {
                            logFontClass.getField("lfStrikeOut").set(logFont,
                                    new Byte((byte) 1));
                            //$NON-NLS-1$
                        }
                        if (underline) {
                            logFontClass.getField("lfUnderline").set(logFont,
                                    new Byte((byte) 1));
                            //$NON-NLS-1$
                        }
                    }
                } catch (final ClassNotFoundException e) {
                    System.err
                            .println(SWTResourceManager.UNABLE_TO_SET_UNDERLINE_OR_STRIKEOUT
                                    + SWTResourceManager.PROBABLY_ON_A_NON_WINDOWS_PLATFORM
                                    + e);
                    //$NON-NLS-1$ //$NON-NLS-2$
                } catch (final NoSuchFieldException e) {
                    System.err
                            .println(SWTResourceManager.UNABLE_TO_SET_UNDERLINE_OR_STRIKEOUT
                                    + SWTResourceManager.PROBABLY_ON_A_NON_WINDOWS_PLATFORM
                                    + e);
                    //$NON-NLS-1$ //$NON-NLS-2$
                } catch (final IllegalAccessException e) {
                    System.err
                            .println(SWTResourceManager.UNABLE_TO_SET_UNDERLINE_OR_STRIKEOUT
                                    + SWTResourceManager.PROBABLY_ON_A_NON_WINDOWS_PLATFORM
                                    + e);
                    //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
            font = new Font(Display.getCurrent(), fontData);
            SWTResourceManager.mFontMap.put(fontName, font);
        }
        return font;
    }

    /**
     * Return a bold version of the give font
     * 
     * @param baseFont
     *                Font The font for whoch a bold version is desired
     * @return Font The bold version of the give font
     */
    public static Font getBoldFont(final Font baseFont) {
        Font font = SWTResourceManager.mFontToBoldFontMap.get(baseFont);
        if (font == null) {
            final FontData[] fontDatas = baseFont.getFontData();
            final FontData data = fontDatas[0];
            font = new Font(Display.getCurrent(), data.getName(), data
                    .getHeight(), SWT.BOLD);
            SWTResourceManager.mFontToBoldFontMap.put(baseFont, font);
        }
        return font;
    }

    /**
     * Dispose all of the cached fonts
     */
    public static void disposeFonts() {
        for (final Font font : SWTResourceManager.mFontMap.values()) {
            font.dispose();
        }
        SWTResourceManager.mFontMap.clear();
    }

    // ////////////////////////////
    // CoolBar support
    // ////////////////////////////

    /**
     * Fix the layout of the specified CoolBar
     * 
     * @param bar
     *                CoolBar The CoolBar that shgoud be fixed
     */
    public static void fixCoolBarSize(final CoolBar bar) {
        final CoolItem[] items = bar.getItems();
        // ensure that each item has control (at least empty one)
        for (final CoolItem item : items) {
            if (item.getControl() == null) {
                item.setControl(new Canvas(bar, SWT.NONE) {
                    @Override
                    public Point computeSize(final int wHint, final int hHint,
                            final boolean changed) {
                        return new Point(SWTResourceManager.TWENTY,
                                SWTResourceManager.TWENTY);
                    }
                });
            }
        }
        // compute size for each item
        for (final CoolItem item : items) {
            final Control control = item.getControl();
            control.pack();
            final Point size = control.getSize();
            item.setSize(item.computeSize(size.x, size.y));
        }
    }

    // ////////////////////////////
    // Cursor support
    // ////////////////////////////

    /**
     * Maps IDs to cursors
     */
    private static HashMap<Integer, Cursor> mIdToCursorMap = new HashMap<Integer, Cursor>();

    /**
     * Returns the system cursor matching the specific ID
     * 
     * @param id
     *                int The ID value for the cursor
     * @return Cursor The system cursor matching the specific ID
     */
    public static Cursor getCursor(final int id) {
        final Integer key = new Integer(id);
        Cursor cursor = SWTResourceManager.mIdToCursorMap.get(key);
        if (cursor == null) {
            cursor = new Cursor(Display.getDefault(), id);
            SWTResourceManager.mIdToCursorMap.put(key, cursor);
        }
        return cursor;
    }

    /**
     * Dispose all of the cached cursors
     */
    public static void disposeCursors() {
        for (final Cursor cursor : SWTResourceManager.mIdToCursorMap.values()) {
            cursor.dispose();
        }
        SWTResourceManager.mIdToCursorMap.clear();
    }
}
