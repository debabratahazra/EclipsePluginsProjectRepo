package com.odcgroup.mdf.compare.viewer.structure;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.compare.diff.metamodel.AbstractDiffExtension;
import org.eclipse.emf.compare.diff.metamodel.ReferenceOrderChange;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.workbench.compare.viewer.content.BaseModelStructureMergeViewer;

/**
 *
 * @author pkk
 *
 */
public class MdfStructureMergeViewer extends BaseModelStructureMergeViewer {

	/**
	 * @param parent
	 * @param compareConfiguration
	 */
	public MdfStructureMergeViewer(Composite parent, CompareConfiguration compareConfiguration) {
		super(parent, compareConfiguration);
		setLabelProvider(new MdfModelStructureLabelProvider());
	}
	
	/**
	 */
	private class MdfModelStructureLabelProvider extends LabelProvider {
		/**
		 * We use this generic label provider, but we want to customize some aspects that's why we choose to
		 * aggregate it.
		 */
		/* package */AdapterFactoryLabelProvider adapterProvider;

		/**
		 * Default constructor.
		 */
		public MdfModelStructureLabelProvider() {
			adapterProvider = new AdapterFactoryLabelProvider(AdapterUtils.getAdapterFactory());

		}

		/**
		 * Returns the platform icon for a given {@link IFile}. If not an {@link IFile}, delegates to the
		 * {@link AdapterFactoryLabelProvider} to get the {@link Image}.
		 * 
		 * @param object
		 *            Object to get the {@link Image} for.
		 * @return The platform icon for the given object.
		 * @see AdapterFactoryLabelProvider#getImage(Object)
		 */
		@Override
		public Image getImage(Object object) {
			Image image = null;
			if (object instanceof AbstractDiffExtension) {
				image = (Image)((AbstractDiffExtension)object).getImage();
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
		 * {@link AdapterFactoryLabelProvider#getText(Object)} if not an {@link IFile}.
		 * 
		 * @param object
		 *            Object we seek the name for.
		 * @return The name of the given object.
		 * @see AdapterFactoryLabelProvider#getText(Object)
		 */
		@Override
		public String getText(Object object) {
			String text = null;
			if (object instanceof ReferenceOrderChange) {
				ReferenceOrderChange refOrd = (ReferenceOrderChange) object;
				if (refOrd.getLeftElement() instanceof MdfEnumeration) {
					text = "The order of the enumeration values has changed";
				} else {
					text = adapterProvider.getText(object);
				}
			} else if (object instanceof AbstractDiffExtension) {
				text = ((AbstractDiffExtension)object).getText();
			} else {
				if (object instanceof IFile) {
					text = ((IFile)object).getName();
				} else if (object instanceof Resource) {
					text = ((Resource)object).getURI().lastSegment();
				} else {
					text = adapterProvider.getText(object);
				}
			}
			return text;
		}
	}

}
