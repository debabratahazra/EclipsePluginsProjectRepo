package com.odcgroup.mdf.editor.ui.dialogs.mdf;


import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
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

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfPagesFactory implements DialogPagesFactory {

	public void addPages(MdfModelElement model, List pages) {
		if (model instanceof MdfAssociation) {
			pages.add(new MdfAssociationPage((MdfAssociation) model));
		} else if (model instanceof MdfReverseAssociation) {
			pages.add(new MdfReverseAssociationPage((MdfReverseAssociation) model));
		} else if (model instanceof MdfAttribute) {
			pages.add(new MdfAttributePage((MdfAttribute) model));
		} else if (model instanceof MdfClass) {
			pages.add(new MdfClassPage((MdfClass) model));
		} else if (model instanceof MdfEnumeration) {
			pages.add(new MdfEnumerationPage((MdfEnumeration) model));
		} else if (model instanceof MdfEnumValue) {
			pages.add(new MdfEnumValuePage((MdfEnumValue) model));
		} else if (model instanceof MdfDomain) {
			pages.add(new MdfDomainPage((MdfDomain) model));
        } else if (model instanceof MdfDataset) {
            pages.add(new MdfDatasetPage((MdfDataset) model));
        } else if (model instanceof MdfDatasetMappedProperty) {
            pages.add(new MdfDatasetMappedPropertyPage((MdfDatasetMappedProperty) model));
	    } else if (model instanceof MdfDatasetDerivedProperty) {
	        pages.add(new MdfDatasetDerivedPropertyPage((MdfDatasetDerivedProperty) model));
		} else if (model instanceof MdfBusinessType) {
			pages.add(new MdfBusinessTypePage((MdfBusinessType) model));
		}
	}
}
