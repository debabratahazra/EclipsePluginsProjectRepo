package com.odcgroup.page.ui.dialog.image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.model.corporate.ImageFileDescriptor;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public class ImageUtils {
	
	
	/**
	 * @param descriptor the image descriptor
	 * @return error message
	 */
	public static String getErrorMessage(ImageDescriptor descriptor) {
		StringBuilder builder = new StringBuilder();
		builder.append("This image cannot be loaded in memory : name=[");
		builder.append(descriptor.getName());
		builder.append("] type=[");
		builder.append(descriptor.getType());
		builder.append("] dir=[");
		builder.append(descriptor.getFileDescriptor().getDirectory());
		builder.append("] filename=[");
		builder.append(descriptor.getFileDescriptor().getFilename());
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * Gets an Image given an ImageDescriptor.
	 * @param display
	 * @param cache
	 * @param descriptor
	 * @return an Image
	 */
	public static Image getImageFromDescriptor(Display display, Map<String, Image> cache, ImageDescriptor descriptor) {
		
		Image result = null;
		ImageFileDescriptor fd = descriptor.getFileDescriptor();
		URL url;
		try {
			url = fd.toURL();
		} catch (MalformedURLException ex) {
			PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
			return null;
		}
		if (fd.isSprite()) {
			String key = url.toString();
			result = cache.get(key);
			if (result == null) {
				InputStream is = null;
				try {
					is = url.openStream();
					result = new Image(display, is);
					cache.put(key, result);
				} catch (FileNotFoundException ex) {
					PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
				} catch (IOException ex) {
					PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
				} catch (SWTException ex) {
					PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
				}
			}
			if ((result != null)) {
				key += descriptor.getName();
				Image icon = cache.get(key);
				if (icon == null) {
					int imageHeight = fd.getImageHeight();
					int imageWidth = fd.getImageWidth();
					// extract the icon from the sprite that contains all icons
					int px = (descriptor.getColumn() - 1) * imageWidth;
					int py = (descriptor.getRow() - 1) * imageHeight;
					icon = new Image(Display.getCurrent(), imageWidth, imageHeight);
					GC gc = new GC(icon);
					gc.drawImage(result, px, py, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight);
					gc.dispose();
					cache.put(key, icon);
				}
				result = icon;
			}
		} else if (fd.isRegular()) {
			String key = url.toString();
			result = cache.get(key);
			if (result == null) {
				InputStream is = null;
				try {
					is = url.openStream();
					Image tmpImage = new Image(display, is);
					Image icon = new Image(Display.getCurrent(), 16, 16);
					GC gc = new GC(icon);
					gc.drawImage(tmpImage, 0, 0, tmpImage.getBounds().width, tmpImage.getBounds().height, 0, 0, 16, 16);
					gc.dispose();
					tmpImage.dispose();
					cache.put(key, icon);
					result = icon;
				} catch (FileNotFoundException ex) {
					PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
				} catch (IOException ex) {
					PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
				} catch (SWTException ex) {
					PageUIPlugin.getDefault().logWarning(getErrorMessage(descriptor), ex);
				}
			}
		}

//		if (result == null) {
//			result = Display.getCurrent().getSystemImage(SWT.ICON_ERROR);
//		}

		return result;
	}

}
