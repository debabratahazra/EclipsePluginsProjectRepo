package com.odcgroup.workbench.editors.properties.model;

import java.util.List;


/**
 *
 * @author pkk
 *
 */
public interface IPropertyTableWidget extends IPropertyFeatureWidget {

	public List<IPropertyWidgetAction> getMenuActions();
	
	public void addMenuAction(IPropertyWidgetAction action);
	
	public void hookContextualMenu();
	
}
