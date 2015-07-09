package com.odcgroup.workbench.editors.properties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.workbench.editors.properties.controls.PropertyMessageWidget;


public abstract class AbstractOFSTabbedPropertySection extends OFSBasePropertySection {

	private Map<String, PropertyMessageWidget> messageWidgets = new HashMap<String, PropertyMessageWidget>();
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.OFSBasePropertySection#createInputControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	protected void createInputControls(Composite parent, TabbedPropertySheetPage propertySheetPage) {
		configureProperties();
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		for(int i =0;i < getTabItems().length;i++){
			TabItem item = new TabItem(tabFolder, SWT.NONE);
			String tabName = getTabItems()[i];
			item.setText(tabName);	     
			Composite body = factory.createFlatFormComposite(tabFolder);
			body.setLayout(new GridLayout(1, false));
			Composite mbody = factory.createFlatFormComposite(body);
			mbody.setLayout(new GridLayout(1, false));
			PropertyMessageWidget messageWidget = new PropertyMessageWidget(mbody, factory);	
			messageWidgets.put(tabName, messageWidget);
			createPropertyControls(body, tabName);
			item.setControl(body);
		}
		tabFolder.setSelection(0);
	}
	
	
	@Override
	protected void validateModel() {
		super.validateModel();
		Set keys = messageWidgets.keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()){
			String tab = (String) iter.next();
			PropertyMessageWidget messageWidget = messageWidgets.get(tab);
			if (getErrorMsg().toString().length()>3){
				messageWidget.setErrorMessage(getErrorMsg().toString());
			} else {
				messageWidget.resetErrorLabel();
			}
			
			if (getWarnMsg().toString().length()>3){
				messageWidget.setWarnMessage(getWarnMsg().toString());
			} else {
				messageWidget.resetWarnLabel();			
			}
		}
	}

	/*___________________________________________________ abstract methods */	


	public abstract String[] getTabItems();
	
	/**
	 * @param body
	 * @param tabName
	 * @return
	 */
	protected abstract void createPropertyControls(Composite body, String tabName);
	
	/**
	 * abstract method to be implemented by child classes
	 */
	protected abstract void configureProperties();

}
