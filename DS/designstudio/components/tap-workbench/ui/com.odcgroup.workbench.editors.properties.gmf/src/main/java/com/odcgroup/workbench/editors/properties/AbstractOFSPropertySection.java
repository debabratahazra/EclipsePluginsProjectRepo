package com.odcgroup.workbench.editors.properties;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.workbench.editors.properties.controls.PropertyMessageWidget;


/**
 * @author pkk
 *
 */
public abstract class AbstractOFSPropertySection extends OFSBasePropertySection {
	
	private PropertyMessageWidget messageWidget;
	
	/* ________________________________________________ overriden methods */
	
	
	public final void createInputControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		Composite body = factory.createComposite(parent);
		body.setLayout(new GridLayout(1, false)); 		    
		messageWidget = new PropertyMessageWidget(body, factory);
		createPropertyControls(body);
	}	
	
	@Override
	protected void validateModel() {
		super.validateModel();
		messageWidget.getParent().layout(true);
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
	
	
	/*___________________________________________________ abstract methods */	
	

	


	/**
	 * abstract method to be implemented by child sections to create input controls required
	 * 
	 * @param parent
	 * @param propertySheetPage
	 */
	protected abstract void createPropertyControls(Composite parent);


	public PropertyMessageWidget getMessageWidget() {
		return messageWidget;
	}	
	
}
