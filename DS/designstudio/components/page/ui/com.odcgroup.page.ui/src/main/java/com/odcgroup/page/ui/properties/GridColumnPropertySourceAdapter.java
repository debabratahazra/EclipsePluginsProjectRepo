package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;

/**
 * Specific property source for managing columns' width in grid
 *
 * @author atr
 * @since DS 1.40.0
 *
 */
public class GridColumnPropertySourceAdapter extends AbstractPropertySourceAdapter {

	/*
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	public void setPropertyValue(Object newValue) {
		// TODO Auto-generated method stub
		
	}
	
    /*
     * @see com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter#isPropertySet()
     */
    public boolean isPropertySet() {
    	boolean isSet = getProperty().getModel() != null 
    				 && getProperty().getModel().eResource() != null; 
        return isSet;
    }

    /* 
     * @see com.odcgroup.page.ui.properties.AbstractPropertySourceAdapter#resetPropertyValue()
     */
    public void resetPropertyValue() {
    	setPropertyValue((Model)null);
    }  	

	/**
	 * @param property
	 * @param commandStack
	 */
	public GridColumnPropertySourceAdapter(Property property, CommandStack commandStack) {
		super(property, commandStack);
		// TODO Auto-generated constructor stub
	}

}
