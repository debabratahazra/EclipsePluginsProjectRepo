package com.odcgroup.page.ui.properties.table.controls;

import org.eclipse.gef.commands.CommandStack;


/**
 * TODO: Document me!
 *
 * @author pkk
 * @param <T> 
 *
 */
public interface SpecialControl<T> {
	
	/**
	 * @param input
	 */
	public void setInput(T input);
	
	/**
	 * @param commandStack
	 */
	public void setCommandStack(CommandStack commandStack);
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled);

}
