package com.odcgroup.mdf.editor.ui.providers.label;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.ui.providers.MdfBaseProvider;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.validation.validator.ValidatorsFactory;

/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 * @deprecated
 */
public class MdfLabelDecorator extends MdfBaseProvider
	implements ILightweightLabelDecorator {
	private static final ImageDescriptor WARNING;
	private static final ImageDescriptor ERROR;

	static {
		ERROR = MdfPlugin.getDefault().getImageDescriptor(MdfCore.ICON_ERROR);
		WARNING = MdfPlugin.getDefault().getImageDescriptor(MdfCore.ICON_WARNING);
	}

	/**
	 * Constructor for MdfLabelDecorator
	 */
	public MdfLabelDecorator() {
		super();
	}

	/**
	 * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object, org.eclipse.jface.viewers.IDecoration)
	 */
	public void decorate(Object element, IDecoration decoration) {
		if ((element instanceof MdfModelElement) &&
			!(element instanceof MdfProject)){
			MdfModelElement model = (MdfModelElement) element;

			StatusAggregator aggregator = new StatusAggregator();
			ModelVisitor validator = ValidatorsFactory.newInstance(aggregator);
            validator.accept(model);
			//new ModelWalker(validator).visit(model);

			IStatus status = aggregator.getStatus();

			if (status != null && !status.isOK()) {
				switch (status.getSeverity()) {
					case IStatus.ERROR:
                        decoration.addOverlay(ERROR, IDecoration.BOTTOM_LEFT);
						break;

					case IStatus.WARNING:
                        decoration.addOverlay(WARNING, IDecoration.BOTTOM_LEFT);
						break;
				}
			}
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
	}

}
