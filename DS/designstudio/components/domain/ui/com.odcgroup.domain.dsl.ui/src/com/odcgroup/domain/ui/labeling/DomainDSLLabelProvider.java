package com.odcgroup.domain.ui.labeling;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

public class DomainDSLLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof IStructuredSelection) {
			MdfModelElement mdfElement = (MdfModelElement)((IStructuredSelection)element).getFirstElement();
			if(mdfElement instanceof MdfDomain) {
				return DomainDSLConstants.DOMAIN;
			} else if(mdfElement instanceof MdfClass) {
				return DomainDSLConstants.CLASS;
			} else if(mdfElement instanceof MdfDataset) {
				return DomainDSLConstants.DATASET;
			} else if(mdfElement instanceof MdfBusinessType) {
				return DomainDSLConstants.BUSINESS_TYPE;
			} else if(mdfElement instanceof MdfEnumeration) {
				return DomainDSLConstants.ENUMERATION;
			} else if(mdfElement instanceof MdfEnumValue) {
				return DomainDSLConstants.ENUM_VALUE;
			} else if(mdfElement instanceof MdfAttribute) {
				return DomainDSLConstants.ATTRIBUTE;
			} else if(mdfElement instanceof MdfAssociation) {
				return DomainDSLConstants.ASSOCIATION;
			} else if(mdfElement instanceof MdfReverseAssociation) {
				return DomainDSLConstants.REVERSE_ASSOCIATION;
			} else if(mdfElement instanceof MdfDatasetMappedProperty) {
				return DomainDSLConstants.DS_CLASS_ATTIRBUTE;
			} else if(mdfElement instanceof MdfDatasetDerivedProperty) {
				return DomainDSLConstants.DS_CALCULATED_ATTIRBUTE;
			}
			return mdfElement.getName();
		}
		return null;
	}

}
