package com.odcgroup.page.model.corporate;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author atr
 */
public class ImageFileDescriptor {
	
	/** directory that contains the file */
	private String dir;

	/** The name of the file, including extension */
	private String filename;
	
	/** type of this file */
	private ImageSourceType type;
	
	/** The number of images contained in the file */
	private int nbImages = 1;

	/** The number of columns in the file */
	private int nbColumns = 1;

	/** The width in pixel of each image in the file */
	private int imageWidth = 0;

	/** The height in pixel of each image in the file */
	private int imageHeight = 0;

	/**
	 * @return the directory that contains the file
	 */
	public final String getDirectory() {
		return dir;
	}

	/**
	 * @return the the filename
	 */
	public final String getFilename() {
		return filename;
	}
	
	/**
	 * @return ImageSourceType
	 */
	public final ImageSourceType getType() {
		return this.type;
	}

	/**
	 * @return {@code true} for a regular image, otherwise returns {@code false}
	 */
	public final boolean isRegular() {
		return ImageSourceType.REGULAR.equals(getType());
	}

	/**
	 * @return {@code true} for a sprite image, otherwise returns {@code false}
	 */
	public final boolean isSprite() {
		return ImageSourceType.SPRITE.equals(getType());
	}
	
	/**
	 * @return the nbImages, default is 1
	 */
	public final int getNbImages() {
		return nbImages;
	}

	/**
	 * @return the nbColumns, default is 1
	 */
	public final int getNbColumns() {
		return nbColumns;
	}

	/**
	 * @return the width, default is 0
	 */
	public final int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @return the height, default is 0
	 */
	public final int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @return URL
	 * @throws MalformedURLException
	 */
	public URL toURL() throws MalformedURLException {
		URL url = null;
		String dir = getDirectory();

		String fullpath = null;
		if (dir.endsWith(File.separator)) {
			fullpath = dir+getFilename(); 
		} else {
			fullpath = dir+File.separator+getFilename();
		}

		if (CorporateImagesUtils.isBuiltIn(fullpath)) {
			url = CorporateImagesUtils.getAbsolutBuiltInUrl(fullpath);
		} else {
			url = new URL("file:"+fullpath);
		}
		return url;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.filename);
		builder.append(",");
		builder.append(this.dir);
		builder.append(",");
		builder.append(this.type);
		if (isSprite()) {
			builder.append(",");
			builder.append(this.nbImages);
			builder.append(",");
			builder.append(this.nbColumns);
			builder.append(",");
			builder.append(this.imageWidth);
			builder.append(",");
			builder.append(this.imageHeight);
		}
		return builder.toString();
	}
	
	/**
	 * @param value
	 * @return ImageFileDescriptor
	 */
	public static ImageFileDescriptor valueOf(String value) {
		ImageFileDescriptor descriptor = null;
		value = StringUtils.replace(value, ",,", ", ,");
		StringTokenizer st = new StringTokenizer(value, ",");
		try {
			String filename = st.nextToken();
			String dir = st.nextToken();
			if (StringUtils.isBlank(dir)) {
				dir = "";
			}
			ImageSourceType type = ImageSourceType.valueOf(st.nextToken());
			if (ImageSourceType.SPRITE.equals(type)) {
				int nbImages = Integer.parseInt(st.nextToken());
				int nbColumns = Integer.parseInt(st.nextToken());
				int width = Integer.parseInt(st.nextToken());
				int height = Integer.parseInt(st.nextToken());
				descriptor = new ImageFileDescriptor(dir, filename, nbImages, nbColumns, width, height);
			} else {
				descriptor = new ImageFileDescriptor(dir, filename);
			}
		} catch (Exception ex){
			throw new IllegalArgumentException(value);
		}
		return descriptor;
	}

	/** 
	 * @param dir
	 * @param filename
	 */
	public ImageFileDescriptor(String dir, String filename) {
		this.dir = dir;
		this.filename = filename;
		this.type = ImageSourceType.REGULAR;
	}
	
	/**
	 * @param dir
	 * @param filename
	 * @param nbImages
	 * @param nbColumns
	 * @param width
	 * @param height
	 */
	public ImageFileDescriptor(String dir, String filename, int nbImages, int nbColumns, int width, int height) {
		this(dir, filename);
		this.type = ImageSourceType.SPRITE;
		this.nbImages = nbImages;
		this.nbColumns = nbColumns;
		this.imageWidth = width;
		this.imageHeight = height;
	}

}
