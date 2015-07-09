package com.odcgroup.page.ui.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.uimodel.Action;

/**
 * an <code>ActionParameters</code> instance defines the fundamental 
 * parameters for defining an action.<p>
 * 
 * An action is basically characterized by a unique identifier, a display name, 
 * a brief description to display a tooltip.<p>
 * 
 * A second set of parameters may be used by the concrete action do do 
 * its job. For instance, the property name and a property value, are generally
 * used used to define a predicate in order check a particular property of the
 * selected widget(s) before running the action.<p>
 * 
 * Another parameter designates the name of an existing widget template. This value
 * can be used by the action to create a new widget. See for example, the actions
 * that either adds a tab to a tabbed-pane, or a column to a table.
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public class ActionParameters {

	/**
	 * The unique action identifier. 
	 * This parameter is required. 
	 */ 
	private String id;
	
	/** 
	 * The name of the action.  This name will be displayed 
	 * appear in the (contextual) menu. 
	 * This parameter is required.
	 */
	private String displayName;
	
	/** The tooltip text */
	private String tooltip;
	
	/** The string representation of an accelerator key code */
	private String accelerator;
	
	/**
	 * The name of a widget property.
	 * This parameter is optional
	 */
	private String propertyName;

	/** 
	 * The reference value of the property
	 * This parameter is optional.
	 */
	private String propertyValue;
	
	/** 
	 * The name of an existing widget template.
	 * This parameter is optional.
     */
	private String widgetTemplateName;

	/**
	 * This is a list of selected widget on which a concrete action
	 * can perform
	 */
	private List<Widget> widgetList;
	
	/** The command stack */
	private CommandStack commandStack;

	/**
	 * @return the action identifier
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @return the display name of the action
	 */
	public final String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the string representation of the accelerator key code.
	 */
	public final String getAccelerator() {
		return accelerator;
	}

	/**
	 * @return the tooltip of the action
	 */
	public final String getTooltip() {
		return tooltip;
	}

	/**
	 * @return the property name
	 */
	public final String getPropertyName() {
		return propertyName;
	}

	/**
	 * @return the property value
	 */
	public final String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @return the widget template name
	 */
	public final String getWidgetTemplateName() {
		return widgetTemplateName;
	}

	/**
	 * @return the widgetList
	 */
	public final List<Widget> getWidgetList() {
		return widgetList;
	}

	/**
	 * @param widgetList
	 *            the list of selected widgets 
	 */
	public final void setWidgetList(List<Widget> widgetList) {
		this.widgetList = widgetList;
	}

	/**
	 * @param commandStack
	 *            the commandStack to set
	 */
	public final void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	/**
	 * @return the commandStack
	 */
	public final CommandStack getCommandStack() {
		return commandStack;
	}
	
	/**
	 * Constructs the parameters set based on an action model. The action 
	 * must as least have an identifier and a name.
	 * 
	 * @param action the model of an action
	 */
	public ActionParameters(Action action) {
		Assert.isLegal(StringUtils.isNotEmpty(action.getId()));
		Assert.isLegal(StringUtils.isNotEmpty(action.getName()));
		
		this.id = action.getId();
		this.displayName = action.getName();
		this.tooltip = action.getDescription();
		
		PropertyType pt = action.getProperty();
		if (pt != null) {
			this.propertyName = pt.getName();
		    this.propertyValue = action.getPropertyValue();
		}
		this.widgetTemplateName = action.getTemplateName();
		
		this.accelerator = action.getAccelerator();
	}
}
