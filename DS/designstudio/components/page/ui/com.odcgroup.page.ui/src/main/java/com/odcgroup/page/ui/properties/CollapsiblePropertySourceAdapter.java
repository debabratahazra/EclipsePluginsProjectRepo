package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Specialized PropertySourceAdapter for widget's COLLAPSIBLE property.
 * Maintains the dependency with the COLLAPSE property
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class CollapsiblePropertySourceAdapter extends DataValuePropertySourceAdapter {
	
    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public CollapsiblePropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
        String s = property.getValue();
        boolean collapsible = ! "false".equalsIgnoreCase(s);
    	Widget w = getProperty().getWidget();
    	Property p = w.findProperty("collapsed");
    	if (p != null) {
    		// no more dependency - always pass false
            p.setReadonly(false/*!collapsible*/);  
    	}
    }
    
    /**
     * Sets the value of the property.
     * 
     * @param newValue The value of the property
     * 
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public void setPropertyValue(Object newValue) {
        DataValue dv = (DataValue) newValue;
        String s = dv.getValue();
        CompoundCommand cc = new CompoundCommand();
        cc.add(new UpdatePropertyCommand(getProperty(), s));
        boolean collapsible = ! "false".equalsIgnoreCase(s);
    	Widget w = getProperty().getWidget();
    	Property p = w.findProperty("collapsed");
    	if (p != null) {
    		// no more dependency - always pass false
            p.setReadonly(false/*!collapsible*/);  
//            if (p.getBooleanValue()) {
//            	// force to false
//            	cc.add(new UpdatePropertyCommand(p, "false"));
//            }
    	}
        getCommandStack().execute(cc);
    }
    
}