package com.odcgroup.page.external.mdf;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;

/**
 * A Request sent when we drop Mdf Model Elements onto a page / module.
 * This is a sub-class of the MultipleWidgetCreateRequest because in some
 * circumstances we don't really want to create Widgets. Instead we need to 
 * modify the properties of existing Widgets.
 * 
 * @author Gary Hayes
 */
public class MdfRequest extends MultipleWidgetCreateRequest {
	
	/** The list of properties. */
	private List<Property> properties;	
	
	/**
	 * Creates a new MdfRequest.
	 */
	public MdfRequest() {
	}	
	
	/**
	 * Gets the properties to update.
	 * 
	 * @return List of Property Objects
	 */
	public List<Property> getProperties() {
		return properties;
	}
	
	/**
	 * Sets the properties to update.
	 * 
	 * @param properties List of Property Objects
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}	
	
	/**
	 * Sets the type of request to REQ_MODIFY or REQ_MULTIPLE_CREATE.
	 * 
	 * @param modify True if the Request should modify the existing Widgets
	 * instead of creating new ones
	 */
	public void setModify(boolean modify) {
		if (modify) {
			setType(PageUIRequestConstants.REQ_MODIFY);
		} else {
			setType(PageUIRequestConstants.REQ_MULTIPLE_CREATE);
		}
	}
}