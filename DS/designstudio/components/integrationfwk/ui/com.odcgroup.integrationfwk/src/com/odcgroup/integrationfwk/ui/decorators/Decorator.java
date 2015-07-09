package com.odcgroup.integrationfwk.ui.decorators;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.decorators.core.ResourcePropertiesManager;

/**
 * 
 * 
 * Class to decorate the resources.
 * 
 */
public class Decorator extends LabelProvider implements ILabelDecorator {
	/**
	 * Demo Image
	 */
	private static DecoratorImages demoImage_ = new DecoratorImages();

	/**
	 * Boolean indicator to differenciate decoration at the start or not.. Not
	 * used
	 */
	private static boolean newDecorationRequest_ = false;

	/**
   *
   */
	private static List initialDecoratorList_ = new Vector();

	private static boolean decorateTextLabels_ = true;
	private static boolean decorateProject_ = true;

	/**
	 * Get the static instance of DemoDecorator
	 * 
	 * @return Demo decorator object
	 * 
	 */
	public static Decorator getDemoDecorator() {
		IDecoratorManager decoratorManager = IntegrationToolingActivator.getDefault()
				.getWorkbench().getDecoratorManager();

		if (decoratorManager
				.getEnabled("com.odcgroup.integrationfwk.ui.decorators.Decorator")) {
			return (Decorator) decoratorManager
					.getLabelDecorator("com.odcgroup.integrationfwk.ui.decorators.Decorator");
		}
		return null;
	}

	public Decorator() {
		super();
	}

	/**
	 * Check whether the decorator for the resource has been changed
	 * 
	 * @param resource
	 *            IResource object
	 * 
	 * @return true if the resource has been changed false if the resource has
	 *         not been changed
	 */
	public boolean checkForNewDecoratorChange(IResource resource) {
		if (newDecorationRequest_) {
			return DecoratorManager.contains(resource);
		}
		return true;
	}

	/**
	 * Function to decorate the image of the object
	 * 
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(Image,
	 *      Object)
	 * 
	 * @param baseImage
	 *            base image of the object
	 * @param obj
	 *            object to be decorated
	 * 
	 * @return Image for the object calculated from its persistent properties
	 *         null if there is no need of a special decoration for the resource
	 */
	public Image decorateImage(Image baseImage, Object object) {
		IResource objectResource;
		objectResource = (IResource) object;

		Vector decoratorImageKeys = new Vector();
		Image image;

		if (objectResource == null) {
			// Object resource cant be null. Should return the image as null
			return null;
		}
		if (objectResource.getType() == IResource.FOLDER
				|| objectResource.getType() == IResource.PROJECT) {
			// Projects and Folders should not be decorated..
			return null;
		}

		try {
			// Resource properties have been changed.

			// Find the decorator with which the image should be decorated
			decoratorImageKeys = ResourcePropertiesManager
					.findDecorationImageForResource(objectResource);

			if (decoratorImageKeys.size() != 0) {
				image = drawIconImage(baseImage, decoratorImageKeys);
				DecoratorManager.removeResource(objectResource);
				return image;
			} else {
				// The resource need not be decorated
				DecoratorManager.removeResource(objectResource);
				return null;
			}

		} catch (Exception e) {
			// Logger.logError (e);
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(String,
	 *      Object)
	 * 
	 * @param label
	 *            default label of the object
	 * @param obj
	 *            object
	 * 
	 * @return label with which the object should be decorated. If null is
	 *         returned, then the object is decorated with default label
	 * 
	 */
	public String decorateText(String label, Object obj) {
		IResource objectResource;
		objectResource = (IResource) obj;
		String newText = label;

		if (objectResource == null) {
			// Object resource cant be null. So return with null.
			return null;
		}
		if (objectResource.getType() == objectResource.PROJECT) {
			if (decorateProject_) {
				return label + " [ " + "Decorator Demo" + " ]";
			}
			return null;
		}
		// Dont decorate the folder
		if (objectResource.getType() == objectResource.FOLDER) {
			return null;
		}

		if (!decorateTextLabels_) {
			return null;
		}
		String suffixValue = ResourcePropertiesManager
				.getSuffix(objectResource);

		if (suffixValue != null && suffixValue.length() != 0) {
			newText += " [ ";
			newText += suffixValue;
			newText += " ]";
		}
		return newText;

	}

	/**
	 * Function to draw icon image
	 * 
	 * @param baseImage
	 *            base image of the object resource
	 * @param decoratorImageKeys
	 *            vector of image keys
	 * 
	 * @return icon image with which the resource is to be decorated
	 */
	private Image drawIconImage(Image baseImage, Vector decoratorImageKeys) {
		Image image;
		OverlayImageIcon overlayIcon = new OverlayImageIcon(baseImage,
				demoImage_, decoratorImageKeys);
		image = overlayIcon.getImage();
		return image;
	}

	/**
	 * Fire a Label Change event so that the label decorators are automatically
	 * refreshed.
	 * 
	 * @param event
	 *            LabelProviderChangedEvent
	 */
	private void fireLabelEvent(final LabelProviderChangedEvent event) {
		// We need to get the thread of execution to fire the label provider
		// changed event , else WSWB complains of thread exception.
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fireLabelProviderChanged(event);
			}
		});
	}

	/**
	 * Refresh the project. This is used to refresh the label decorators of of
	 * the resources.
	 * 
	 */
	public void refresh() {
		newDecorationRequest_ = true;
		initialDecoratorList_ = null;

		List resourcesToBeUpdated;

		// Get the Demo decorator
		Decorator demoDecorator = getDemoDecorator();
		if (demoDecorator == null) {
			return;
		} else {
			resourcesToBeUpdated = DecoratorManager.getSuccessResources();
			// Fire a label provider changed event to decorate the
			// resources whose image needs to be updated

			demoDecorator.fireLabelEvent(new LabelProviderChangedEvent(
					demoDecorator, resourcesToBeUpdated.toArray()));
		}
	}

	public void refresh(List resourcesToBeUpdated) {
		newDecorationRequest_ = true;
		initialDecoratorList_ = null;

		Decorator demoDecorator = getDemoDecorator();
		if (demoDecorator == null) {
			return;
		} else {
			// Fire a label provider changed event to decorate the
			// resources whose image needs to be updated
			fireLabelEvent(new LabelProviderChangedEvent(demoDecorator,
					resourcesToBeUpdated.toArray()));
		}
	}

	/**
	 * Refresh all the resources in the project
	 */
	public void refreshAll(boolean displayTextLabel, boolean displayProject) {
		decorateTextLabels_ = displayTextLabel;
		decorateProject_ = displayProject;

		Decorator demoDecorator = getDemoDecorator();
		if (demoDecorator == null) {
			return;
		} else {
			demoDecorator.fireLabelEvent(new LabelProviderChangedEvent(
					demoDecorator));

		}
	}

	/**
	 * Function to update the image registry ... Used with Image Caching
	 * 
	 * @param keyOfImage
	 *            key of image icon with which the resource should be decorated
	 * @param image
	 *            Image of the resource
	 */
	/*
	 * 
	 * private void updateImageRegistry (String keyOfImage, Image image) { if
	 * (demoImageRegistry_.getImage(keyOfImage) == null) {
	 * demoImageRegistry_.setImage(keyOfImage, image); }
	 * 
	 * }
	 */

}
