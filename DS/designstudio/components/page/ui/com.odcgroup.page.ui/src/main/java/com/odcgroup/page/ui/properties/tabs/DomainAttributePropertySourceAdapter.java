package com.odcgroup.page.ui.properties.tabs;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter;

/**
 * Specialized PropertySourceAdapter for Domain Attributes in
 * the context of dynamic tabbed pane
 * 
 * @author atr
 */
public class DomainAttributePropertySourceAdapter extends AbstractPropertySourceAdapter {

    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public DomainAttributePropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
    /**
     * Gets the value of the property.
     * 
     * @return Object The value of the property
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public Object getPropertyValue() {
        String s = getProperty().getValue();
        return s == null ? "" : s;
    }
    
    /**
     * Sets the value of the property.
     * 
     * @param newValue
     *            The value of the property
     *            
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public void setPropertyValue(Object newValue) {
    	
		String oldValue = getProperty().getValue();
		if (!StringUtils.equals(oldValue, (String)newValue)) {
			// the filtered attribute has been changed.
			// Traverse all dynamic tab to invalid the filter value.
			
			CompoundCommand cc = new CompoundCommand("Update filtered attribute");

			// update the filtered-attribute
	        cc.add(new UpdatePropertyCommand(getProperty(), (String)newValue));
			
			Widget tabbedPane = getProperty().getWidget();
			for (Widget tab : tabbedPane.getContents()) {
				Property p = tab.findProperty("hide-empty-tab");
				boolean hide = p != null && p.getBooleanValue();
				if (hide) {
					Property filter = tab.findProperty("tab-filter");
					if (filter != null) {
						cc.add(new UpdatePropertyCommand(filter, ""));
					}
				}
			}
			
	        getCommandStack().execute(cc);
		}
    }

}