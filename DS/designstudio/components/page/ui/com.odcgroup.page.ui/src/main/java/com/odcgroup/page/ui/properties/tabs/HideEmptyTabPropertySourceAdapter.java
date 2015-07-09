package com.odcgroup.page.ui.properties.tabs;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.DataValuePropertySourceAdapter;

/**
 * @author atr
 */
public class HideEmptyTabPropertySourceAdapter extends DataValuePropertySourceAdapter {
	
	/**
	 * @return the state of the property
	 */
	private final boolean hideEmptyTab() {
		return getProperty().getBooleanValue();
	}
	
	/**
	 * @return the tab-filter property
	 */
	private Property getTabFilter() {
		Widget tab = getProperty().getWidget();
		Property p = tab.findProperty("tab-filter");
		return p;
	}
	
	/**
	 * 
	 */
	private void updateDependentProperties() {
		Property p = getTabFilter();
		if (p != null) {
			p.setReadonly(!hideEmptyTab());
		}
	}

	/**
	 * @see com.odcgroup.page.ui.properties.DataValuePropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object newValue) {
		String newState = ((DataValue)newValue).getValue();
		Boolean oldHideEmptyTab= hideEmptyTab();
		Boolean newHideEmptyTab = Boolean.valueOf(newState); 
		if (oldHideEmptyTab != newHideEmptyTab) {
			CompoundCommand cc = new CompoundCommand();
			if (!newHideEmptyTab) {
				// reset the tab-filter
				cc.add(new UpdatePropertyCommand(getTabFilter(), ""));
			} 
			cc.add(new UpdatePropertyCommand(getProperty(), newState));
			getCommandStack().execute(cc);
			updateDependentProperties();
		}
	}
	
	/**
	 * @see com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter#resetPropertyValue()
	 */
	@Override
	public void resetPropertyValue() {
    	Property p = getProperty();
    	String oldValue = p.getValue();
    	String newValue = p.getType().getDefaultValue();
    	// Update the ID Property
    	if (! oldValue.equals(newValue)) {
    		Boolean hideEmptyTab = Boolean.valueOf(newValue);
			CompoundCommand cc = new CompoundCommand();
			if (!hideEmptyTab) {
				cc.add(new UpdatePropertyCommand(getTabFilter(), ""));
			} 
			cc.add(new UpdatePropertyCommand(getProperty(), newValue));
			getCommandStack().execute(cc);
			updateDependentProperties();
    	}
	}

	/**
	 * @param property
	 * @param commandStack
	 */
	public HideEmptyTabPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
		updateDependentProperties();
	}

}
