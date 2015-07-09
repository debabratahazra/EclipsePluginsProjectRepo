package com.odcgroup.page.model.corporate;

import java.util.List;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

/**
 * CorporateImages provides services to manages a collection of
 * images used by the page designer
 *
 * @author atr
 *
 */
public interface CorporateImages {
	
	/**
	 * @param listener
	 */
	void addPreferenceChangeListener(IPreferenceChangeListener listener);

	/**
	 * @param listener
	 */
	void removePreferenceChangeListener(IPreferenceChangeListener listener);	

	/**
	 * @return ProjectScope
	 */
	ProjectScope getProjectScope();
	
	/**
	 * @param name
	 * @return ImageDescriptor
	 */
	ImageDescriptor getImageDescriptor(String name);

	/**
	 * @return a list of all images descriptors
	 */
	List<ImageDescriptor> getAllImageDescriptors();
	
	/**
	 * @return a list of enabled images descriptors
	 */
	List<ImageDescriptor> getEnabledImageDescriptors();

	/**
	 * @param name the name of the image
	 * @return {@code} if the image exists in the file system, otherwise it returns {@false}
	 */
	boolean imageExists(String name);

	/**
	 * @param id the image descriptor
	 * @return {@code} if the image exists in the file system, otherwise it returns {@false}
	 */
	boolean imageExists(ImageDescriptor id);

	/**
	 * @param descriptors
	 */
	void saveImagesDescriptors(List<ImageDescriptor> descriptors);
	
	/**
	 * Adds or replaces image descriptors.
	 * @param descriptors
	 */
	void addImageDescriptor(List<ImageDescriptor> descriptors);

}
