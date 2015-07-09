/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model.provider;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.odcgroup.page.common.PageConstants;

/**
 * This is the central singleton for the PageModel edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class PageModelEditPlugin extends EMFPlugin {
	
	/** The id of this plugin. */
	private static final String PLUGIN_ID = "com.odcgroup.page.edit";
	
	/** The page image. */
	private static final Image PAGE_IMAGE = PageModelEditPlugin.getImageDescriptor("/icons/obj16/page.png").createImage();

	/** The module image. */
	private static final Image MODULE_IMAGE = PageModelEditPlugin.getImageDescriptor("/icons/obj16/module.png").createImage();
	
	/** The fragment image. */
	private static final Image FRAGMENT_IMAGE = PageModelEditPlugin.getImageDescriptor("/icons/obj16/fragment.png").createImage();	
	
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final PageModelEditPlugin INSTANCE = new PageModelEditPlugin();

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageModelEditPlugin() {
		super
		  (new ResourceLocator [] {
		   });
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}	
	
	/**
	 * Get image from file extension.
	 * 
	 * @param fileExtension The file extension
	 * @return Image The image or null if the file extension is unknown
	 */
	public static Image getImageFromFileExtension(String fileExtension) {
        if (PageConstants.PAGE_FILE_EXTENSION.equals(fileExtension)) {
        	return PAGE_IMAGE;
        } else if (PageConstants.MODULE_FILE_EXTENSION.equals(fileExtension)) {
        	return MODULE_IMAGE;
        } else if (PageConstants.FRAGMENT_FILE_EXTENSION.equals(fileExtension)) {
        	return FRAGMENT_IMAGE;
        }
        
        return null;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
	}

}
