package com.odcgroup.mdf.editor.ui.providers.decorators;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class CardinalityDecorator implements
        ILightweightLabelDecorator {

    /**
     * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
     *      org.eclipse.jface.viewers.IDecoration)
     */
    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof MdfProperty) {
            MdfProperty p = (MdfProperty) element;
            
            StringBuffer buffer = new StringBuffer();
            buffer.append(p.isRequired() ? "[1-" : "[0-");

            if (p.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
                buffer.append("1] ");
            } else {
                buffer.append("n] ");
            }

            decoration.addPrefix(buffer.toString());
        } else if (element instanceof MdfDatasetProperty) {
            MdfDatasetProperty p = (MdfDatasetProperty) element;

            StringBuffer buffer = new StringBuffer();
            buffer.append("[0-");

            if (p.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
                buffer.append("1] ");
            } else {
                buffer.append("n] ");
            }

            decoration.addPrefix(buffer.toString());
        }
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }
}
