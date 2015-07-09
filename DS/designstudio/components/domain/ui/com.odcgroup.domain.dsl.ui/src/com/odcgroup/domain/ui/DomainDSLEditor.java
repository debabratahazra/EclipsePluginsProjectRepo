package com.odcgroup.domain.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class DomainDSLEditor extends XtextEditor implements ITabbedPropertySheetPageContributor {
	 
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getSite().setSelectionProvider(getSelectionProvider());
	}
	
	@Override
	public String getContributorId() {
		// TODO Auto-generated method stub
		return DomainUtils.PLUGIN_ID;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class)
            return new DomainDSLTabbedPropertySheetPage(this);
		return super.getAdapter(adapter);
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		
	}
}
