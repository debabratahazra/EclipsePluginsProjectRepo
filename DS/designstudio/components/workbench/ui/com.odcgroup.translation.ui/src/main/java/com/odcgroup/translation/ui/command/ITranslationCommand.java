package com.odcgroup.translation.ui.command;

import org.eclipse.core.runtime.CoreException;

/**
 * The interface ITranslationCommand represents a command for updating or
 * deleting a translation.
 * 
 * @author atr
 */
public interface ITranslationCommand {

	/**
	 * @return the display name of this command
	 */
	String getName();

	/**
	 * @return the toolTip of this command
	 */
	String getToolTip();

	/**
	 * @return
	 *         <tt>true</true> if the button bound to this command must be visible, otherwise it returns <tt>false</tt>
	 */
	boolean isVisible();

	/**
	 * @return
	 *         <tt>true</true> if this command is enabled, otherwise it returns <tt>false</tt>
	 */
	boolean isEnabled();
	
	/**
	 * Executes the command
	 * 
	 * @throws CoreException
	 */
	void execute(String newText) throws CoreException;
	
	/**
	 * 
	 */
	void release();

}
