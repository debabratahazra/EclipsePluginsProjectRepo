package com.odcgroup.service.model.ui.outline;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

import com.google.inject.Inject;
import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.impl.ConstantImpl;
import com.odcgroup.service.model.component.impl.InterfaceImpl;
import com.odcgroup.service.model.component.impl.MethodImpl;
import com.odcgroup.service.model.component.impl.PropertyImpl;
import com.odcgroup.service.model.component.impl.TableImpl;

/**
 * Filter on Access specifier: Module
 */
public abstract class AbstractFilterAccessContribution extends AbstractFilterOutlineContribution {

	public static final String PLUGIN_ID = "com.odcgroup.service.model.ui";

	protected AccessSpecifier accessSpecifier;
	protected String preferenceKey;

	protected @Inject
	ResourceSet resourceSet;

	@Inject
	protected PluginImageHelper imageHelper;

	public AbstractFilterAccessContribution(String preferenceKey, AccessSpecifier accessSpecifier) {
		this.preferenceKey = preferenceKey;
		this.accessSpecifier = accessSpecifier;
	}

	@Override
	protected boolean apply(IOutlineNode node) {

		if (node instanceof EObjectNode) {
			EObjectNode eObjectNode = (EObjectNode) node;
			EClass eClass = eObjectNode.getEClass();
			EObject eObject = resourceSet.getEObject(eObjectNode.getEObjectURI(), true);
			if ((eClass.equals(ComponentPackage.Literals.INTERFACE) && ((InterfaceImpl) eObject).getAccessSpecifier() == accessSpecifier)
					|| (eClass.equals(ComponentPackage.Literals.METHOD) && ((MethodImpl) eObject).getAccessSpecifier() == accessSpecifier)
					|| (eClass.equals(ComponentPackage.Literals.PROPERTY) && ((PropertyImpl) eObject)
							.getAccessSpecifier() == accessSpecifier)
					|| (eClass.equals(ComponentPackage.Literals.CONSTANT) && ((ConstantImpl) eObject)
							.getAccessSpecifier() == accessSpecifier)
					|| (eClass.equals(ComponentPackage.Literals.TABLE) && ((TableImpl) eObject).getAccessSpecifier() == accessSpecifier)) {
				// Apply filter for the current node
				return false;
			}
		}
		// Make visible other Outline nodes
		return true;
	}

	@Override
	public String getPreferenceKey() {
		return preferenceKey;
	}

	@Override
	protected void configureAction(Action action) {
		action.setText("Hide " + getName() + " Members");
		action.setDescription("This hides '" + getName() + "'ly accessible members");
		action.setToolTipText("Hide " + getName() + " Members");
		action.setImageDescriptor(new OverlayImageDescriptor(getOverlayImageDescriptor(), getImageDescriptor()));
	}

	protected abstract ImageDescriptor getImageDescriptor();

	protected ImageDescriptor getOverlayImageDescriptor() {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "/icons/strike.png");
	}

	private String getName() {
		String name = accessSpecifier.getName().toLowerCase();
		return name.replaceFirst("\\w", String.valueOf(Character.toUpperCase(name.charAt(0))));
	}

	class OverlayImageDescriptor extends CompositeImageDescriptor {
		private ImageDescriptor imageDescriptor;
		private ImageDescriptor overlayImage;
		Point size;
		Point overlaySize;

		public OverlayImageDescriptor(ImageDescriptor imgDescriptor, ImageDescriptor overlayImage) {
			setImageDescriptor(imgDescriptor);
			setOverlayImage(overlayImage);
		}

		protected void drawCompositeImage(int arg0, int arg1) {
			drawImage(getImageDescriptor().getImageData(), 0, 0);
			ImageData overlayImageData = getOverlayImage().getImageData();
			int xValue = size.x - overlaySize.x;
			int yValue = size.y - overlaySize.y;
			;
			drawImage(overlayImageData, xValue, yValue);
		}

		protected Point getSize() {
			return size;
		}

		public void setImageDescriptor(ImageDescriptor imageDescriptor) {
			this.imageDescriptor = imageDescriptor;
			Rectangle bounds = imageDescriptor.createImage().getBounds();
			size = new Point(bounds.width, bounds.height);
		}

		public ImageDescriptor getImageDescriptor() {
			return imageDescriptor;
		}

		public void setOverlayImage(ImageDescriptor overlayImage) {
			this.overlayImage = overlayImage;
			Rectangle bounds = overlayImage.createImage().getBounds();
			overlaySize = new Point(bounds.width, bounds.height);
		}

		public ImageDescriptor getOverlayImage() {
			return overlayImage;
		}

	}

}
