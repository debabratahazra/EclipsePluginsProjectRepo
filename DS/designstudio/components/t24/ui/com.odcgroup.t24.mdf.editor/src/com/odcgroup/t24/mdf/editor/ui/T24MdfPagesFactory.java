package com.odcgroup.t24.mdf.editor.ui;

import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfAssociationPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfBusinessTypePage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfDatasetMappedPropertyPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfDatasetPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfDomainPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfEnumValuePage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfEnumerationPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfReverseAssociationPage;
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
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.t24.applicationimport.T24Aspect;

public class T24MdfPagesFactory implements DialogPagesFactory {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addPages(MdfModelElement model, List pages) {
		if (model instanceof MdfAssociation) {
			pages.add(new MdfAssociationPage((MdfAssociation) model));
		} else if (model instanceof MdfReverseAssociation) {
			pages.add(new MdfReverseAssociationPage((MdfReverseAssociation) model));
		} else if (model instanceof MdfAttribute) {
			pages.add(new T24MdfAttributePage((MdfAttribute) model));
		} else if (model instanceof MdfClass) {
			pages.add(new T24MdfClassPage((MdfClass) model));
		}else if (model instanceof MdfEnumeration) {
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
	        pages.add(new T24MdfDatasetDerivedPropertyPage((MdfDatasetDerivedProperty)model));
		} else if (model instanceof MdfBusinessType) {
			pages.add(new MdfBusinessTypePage((MdfBusinessType) model));
		}
		if(model instanceof MdfProperty){
			if(T24Aspect.getLocalRefDefinition(((MdfProperty) model).getParentClass().getParentDomain())){
				pages.add(new T24LocalFieldPage((MdfProperty) model));
			}
		}
	}
}
