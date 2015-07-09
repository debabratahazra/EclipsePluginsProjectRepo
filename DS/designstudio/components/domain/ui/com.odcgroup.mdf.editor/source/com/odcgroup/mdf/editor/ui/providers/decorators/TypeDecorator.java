package com.odcgroup.mdf.editor.ui.providers.decorators;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.metamodel.MdfReverseAssociationWrapper;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class TypeDecorator implements ILightweightLabelDecorator {

    /**
     * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
     *      org.eclipse.jface.viewers.IDecoration)
     */
    public void decorate(Object model, IDecoration decoration) {
        StringBuffer buffer = new StringBuffer();

        if (model instanceof MdfClass) {
            MdfClass klass = (MdfClass) model;

            if (klass.getBaseClass() != null && 
            		klass.getBaseClass().getQualifiedName() != null) {
                buffer.append(" (").append(
                		klass.getBaseClass().getQualifiedName().toString()).append(
                        ')');
            }
        } else if (model instanceof MdfProperty) {
            MdfEntity type = ((MdfProperty) model).getType();
            if (type != null && type.getQualifiedName() != null) {
            	buffer.append(" (");
            	if (model instanceof MdfReverseAssociationWrapper) {
            		MdfReverseAssociation rAssoc = (MdfReverseAssociation) model;
        			buffer.append(type.getQualifiedName().toString());
            		buffer.append(" reverse of ");
            		buffer.append(rAssoc.getAssociation().getName());
            	} else {
            		buffer.append(type.getQualifiedName().toString());
            	}
                buffer.append(')');
            }
        } else if (model instanceof MdfEnumValue) {
            String value = ((MdfEnumValue) model).getValue();
            buffer.append(" = ").append(value);
        } else if (model instanceof MdfDomain) {
            buffer.append(" (").append(((MdfDomain) model).getNamespace()).append(
                    ')');
        } else if (model instanceof MdfDataset) {
            MdfDataset dataset = (MdfDataset) model;

            if (dataset.getBaseClass() != null 
            		&& dataset.getBaseClass().getQualifiedName() != null) {
                buffer.append(" (").append(
                        dataset.getBaseClass().getQualifiedName().toString()).append(
                        ')');
            }
        } else if (model instanceof MdfDatasetProperty) {
            MdfEntity type = ((MdfDatasetProperty) model).getType();

            if (type != null && type.getQualifiedName() != null) {
                buffer.append(" (").append(type.getQualifiedName().toString()).append(
                        ')');
            }
        } else {
            return;
        }

        decoration.addSuffix(buffer.toString());
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
