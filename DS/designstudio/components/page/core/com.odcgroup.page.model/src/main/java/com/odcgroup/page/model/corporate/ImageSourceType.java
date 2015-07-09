package com.odcgroup.page.model.corporate;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public enum ImageSourceType {

	/**
	 * The image is contained in a single file. The file contains only
	 * one image.
	 */
	REGULAR,
	
	/**
	 * The image is contained in a sprite file, i.e the file contains several
	 * image. All images have the same dimension. image are located either by
	 * its position or by its coordinate (row, column)
	 */
	SPRITE
	
}
