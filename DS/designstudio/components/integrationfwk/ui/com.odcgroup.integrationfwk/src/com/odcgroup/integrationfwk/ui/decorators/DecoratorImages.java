package com.odcgroup.integrationfwk.ui.decorators;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;

/**
 * Set of images that are used for decorating resources are maintained here.
 * This acts as a image registry and hence there is a single copy of the image
 * files floating around the project.
 * 
 */
public class DecoratorImages {
	/**
	 * Lock Image Descriptor
	 */
	private static final ImageDescriptor lockDescriptor = ImageDescriptor.createFromFile(Decorator.class, "error.JPG");

	/**
	 * Constructor for DemoImages.
	 */
	public DecoratorImages() {
		super();
	}

	/**
	 * Get the image data depending on the key
	 * 
	 * @return image data
	 * 
	 */
	public ImageData getImageData(String imageKey) {
		if (imageKey.equals("Lock")) {
			return getLockImageData();
		}
		if (imageKey.equals("Owner")) {
			return getLockImageData();
		}
		return null;
	}

	/**
	 * Get the lock image data
	 * 
	 * @return image data for the lock flag
	 */
	public ImageData getLockImageData() {
		return lockDescriptor.getImageData();
	}

	// public ImageDescriptor

}
