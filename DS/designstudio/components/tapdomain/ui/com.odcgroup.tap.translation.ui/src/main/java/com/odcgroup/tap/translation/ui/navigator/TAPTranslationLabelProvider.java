package com.odcgroup.tap.translation.ui.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.tap.translation.ui.TAPTranslationUICore;
import com.odcgroup.translation.ui.navigator.TranslationNode;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * Replaces the translations file name with the text "Translations", so that it
 * appears just like a model folder. 
 *
 * @author atr
 *
 */
public class TAPTranslationLabelProvider implements ILabelProvider {

	private Image translationImage;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if(element instanceof TranslationNode) {
			if(translationImage==null) {
				ImageDescriptor imgDesc = OfsUICore.imageDescriptorFromPlugin(
					TAPTranslationUICore.PLUGIN_ID, "/icons/translations.png");
				translationImage = imgDesc.createImage();
			}
			return translationImage;
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if(element instanceof TranslationNode) {
			return ((TranslationNode)element).getName();
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		if (translationImage != null) {
			translationImage.dispose();
			translationImage = null;
		}
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
	}
}
