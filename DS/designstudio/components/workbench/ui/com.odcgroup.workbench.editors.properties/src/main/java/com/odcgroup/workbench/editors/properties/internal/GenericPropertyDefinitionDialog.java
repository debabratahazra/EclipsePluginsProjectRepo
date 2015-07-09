package com.odcgroup.workbench.editors.properties.internal;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.editors.properties.item.AbstractPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

/**
 *
 * @author pkk
 *
 */
public class GenericPropertyDefinitionDialog extends AbstractPropertyDefinitionDialog {	

	/**
	 * @param parentShell
	 * @param property
	 * @param update
	 */
	public GenericPropertyDefinitionDialog(Shell parentShell, EObject property, EObject parent, boolean update) {
		super(parentShell, property, parent, update);
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyDefinitionDialog#configureProperties()
	 */
	@Override
	protected void configureProperties() {
		SimpleGroupWidget group = new SimpleGroupWidget(getProperty().eClass().getName());		
		List<EAttribute> attributes = getProperty().eClass().getEAllAttributes();
		SimpleTextWidget textWidget = null;
		for (final EAttribute attribute : attributes) {			
			textWidget = new SimpleTextWidget(attribute, firstLetterUpper(attribute.getName()));
			textWidget.setFillHorizontal(true);
			group.addPropertyFeature(textWidget);
		}		
		addPropertyFeature(group);			
	}	
	
	/**
	 * @param value
	 * @return
	 */
	private String firstLetterUpper(String value) {
		if (value != null && value.trim().length() > 0) {
			return value.substring(0,1).toUpperCase()+value.substring(1);
		}
		return "";
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog#validate(org.eclipse.emf.ecore.EObject)
	 */
	public boolean validate(EObject element) {
		return true;
	}

}
