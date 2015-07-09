package com.odcgroup.page.model.corporate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Image Descriptor
 * 
 * @author atr
 */
public class ImageDescriptor {
	
	/** */
	private static final String ITEM_SEPARATOR = ",";

	/** The name of the image */
	private String name;

	/** the location of the the file that contains the image */
	private ImageFileDescriptor fileDescriptor;

	/** sprite image only: row number of the image in the file */
	private int row;

	/** sprite image only: column number of the image in the file */
	private int column;

	/** true if this image descriptor is available, */
	private boolean enable;
	
	/**
	 * @return the name of the image
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @return the name of the file that contains the image
	 */
	public final ImageFileDescriptor getFileDescriptor() {
		return this.fileDescriptor;
	}

	/**
	 * @return the type of the image
	 */
	public ImageSourceType getType() {
		return this.fileDescriptor.getType();
	}

	/**
	 * @return the row number of the image within the file
	 */
	public final int getRow() {
		return row;
	}

	/**
	 * @return the column number of the image within the file
	 */
	public final int getColumn() {
		return this.column;
	}
	
	/**
	 * Sets this descriptor unavailable.
	 * @param disable
	 */
	public final void setDisabled(boolean disable) {
		this.enable = ! disable;
	}

	/**
	 * @return {@code true} if this descriptor is unavailable, otherwise it returns
	 *         {@code false}
	 */
	public final boolean isDisabled() {
		return ! this.enable;
	}

	/**
	 * Sets this descriptor available.
	 * @param enable
	 */
	public final void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return {@code true} if this descriptor is available, otherwise it returns
	 *         {@code false}
	 */
	public final boolean isEnabled() {
		return this.enable;
	}
	
	/**
	 * @return the only filename (including extension)
	 */
	public final String getFilename() {
		return getFileDescriptor().getFilename();
	}
	
	/**
	 * @return URL
	 * @throws MalformedURLException 
	 */
	public final URL toURL() throws MalformedURLException {
		return getFileDescriptor().toURL();
	}
	
	/**
	 * Returns a string representation of this descriptor
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getName());
		builder.append(ITEM_SEPARATOR);
		builder.append(isEnabled());
		if (fileDescriptor.isSprite()) {
			builder.append(ITEM_SEPARATOR);
			builder.append(getRow());
			builder.append(ITEM_SEPARATOR);
			builder.append(getColumn());
		}
		return builder.toString();
	}

	/**
	 * Constructs ImageInfo for a regular image. A regular image has no
	 * row/column coordinates. The file is the image.
	 * 
	 * @param name
	 *            name of the image
	 * @param fileDescriptor
	 *            file that contains the image
	 * @param enable
	 *            indicates if the image descriptor is available or not
	 */
	public ImageDescriptor(String name, ImageFileDescriptor fileDescriptor, boolean enable) {
		super();
		this.name = name;
		this.fileDescriptor = fileDescriptor;
		this.enable = enable;
	}

	/**
	 * Constructs ImageInfo for a sprite image. A sprite is identified by
	 * row/column coordinates in the file. The file contains several images, all
	 * with the same dimensions.
	 * 
	 * @param name
	 *            name of the image
	 * @param fileDescriptor
	 *            file that contains the image
	 * @param row
	 *            row coordinate of the sprite in the file
	 * @param column
	 *            column coordinate of the sprite the file
	 * @param enable
	 *            indicates if the image is available or not
	 */
	public ImageDescriptor(String name, ImageFileDescriptor fileDescriptor, int row, int column, boolean enable) {
		super();
		this.name = name;
		this.fileDescriptor = fileDescriptor;
		this.row = row;
		this.column = column;
		this.enable = enable;
	}
	
	/**
	 * Converts a string representation returned by {@code toString} to an ImageDescriptor.
	 * @param fileDescriptor
	 * @param value the string representation
	 * @return ImageDescriptor 
	 */
	public static ImageDescriptor valueOf(ImageFileDescriptor fileDescriptor, String value) {
		ImageDescriptor descriptor = null;
		String[] values = value.split(ITEM_SEPARATOR);
		try {
			String imgname = values[0];
			boolean enable = Boolean.valueOf(values[1]);
			if (fileDescriptor.isSprite()) {
				int row = Integer.parseInt(values[2]);
				int column = Integer.parseInt(values[3]);
				descriptor = new ImageDescriptor(imgname, fileDescriptor, row, column, enable);
			} else {
				descriptor = new ImageDescriptor(imgname, fileDescriptor, enable);
			}
		} catch (Exception ex){
			throw new IllegalArgumentException(value);
		}
		return descriptor;

	}

}
