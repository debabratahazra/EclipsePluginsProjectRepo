package com.odcgroup.t24.mdf.editor.ui;

import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfDatasetDerivedPropertyPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;


public class T24MdfDatasetDerivedPropertyPage extends MdfDatasetDerivedPropertyPage {

	@Override
    protected IContentAssistProvider getContentAssistProvider(MdfDatasetDerivedProperty property) {
    	return new T24MdfDatasetDerivedPropertyTypeProvider(property);
    }

    public T24MdfDatasetDerivedPropertyPage(MdfDatasetDerivedProperty property) {
        super(property);
    }

}
