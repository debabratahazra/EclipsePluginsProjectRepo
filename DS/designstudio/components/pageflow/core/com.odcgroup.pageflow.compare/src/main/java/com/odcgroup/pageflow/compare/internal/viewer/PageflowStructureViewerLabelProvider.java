package com.odcgroup.pageflow.compare.internal.viewer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.compare.diff.metamodel.AbstractDiffExtension;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateOrSelectElementCommand.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.pageflow.compare.provider.PageflowCompareItemProviderAdapterFactory;

/**
 * @author pkk
 * 
 */
public class PageflowStructureViewerLabelProvider extends LabelProvider {

	AdapterFactoryLabelProvider adapterProvider;

	/**
	 * Default constructor.
	 */
	public PageflowStructureViewerLabelProvider() {
		adapterProvider = new AdapterFactoryLabelProvider(AdapterUtils.getAdapterFactory());
		if (adapterProvider.getAdapterFactory() instanceof ComposedAdapterFactory) {
			ComposedAdapterFactory cFactory = (ComposedAdapterFactory) adapterProvider.getAdapterFactory();
			cFactory.insertAdapterFactory(new PageflowCompareItemProviderAdapterFactory());
		}
	}

	/**
	 * Returns the platform icon for a given {@link IFile}. If not an
	 * {@link IFile}, delegates to the {@link AdapterFactoryLabelProvider} to
	 * get the {@link Image}.
	 * 
	 * @param object
	 *            Object to get the {@link Image} for.
	 * @return The platform icon for the given object.
	 * @see AdapterFactoryLabelProvider#getImage(Object)
	 */
	public Image getImage(Object object) {
		Image image = null;
		if (object instanceof AbstractDiffExtension) {
			image = (Image) ((AbstractDiffExtension) object).getImage();
		}
		if (object instanceof IFile) {
			image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		} else {
			if (image == null) {
				image = adapterProvider.getImage(object);
			}
		}
		return image;
	}

	/**
	 * Returns the name of the given {@link IFile}, delegates to
	 * {@link AdapterFactoryLabelProvider#getText(Object)} if not an
	 * {@link IFile}.
	 * 
	 * @param object
	 *            Object we seek the name for.
	 * @return The name of the given object.
	 * @see AdapterFactoryLabelProvider#getText(Object)
	 */
	public String getText(Object object) {
		String text = null;
		if (object instanceof AbstractDiffExtension) {
			text = ((AbstractDiffExtension) object).getText();
		} else {
			if (object instanceof IFile) {
				text = ((IFile) object).getName();
			} else if (object instanceof Resource) {
				text = ((Resource) object).getURI().lastSegment();
			} else {
				text = adapterProvider.getText(object);
			}
		}
		return text;
	}

}
