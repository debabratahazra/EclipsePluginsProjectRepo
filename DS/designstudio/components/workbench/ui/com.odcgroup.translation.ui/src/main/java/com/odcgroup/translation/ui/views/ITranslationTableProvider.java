package com.odcgroup.translation.ui.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.translation.ui.editor.controls.ITranslationFilterControl;
import com.odcgroup.translation.ui.editor.model.IMultiTranslationTable;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public interface ITranslationTableProvider {
	
	/**
	 * @return the unique identifier of this provider
	 */
	String getId();

	/**
	 * @return the name of this provider
	 */
	String getName();

	/**
	 * @return the nature or an empty string 
	 */
	String getNature();

	/**
	 * @return the priority value in the range 0..int'max
	 */
	int getPriority();	
	
	/**
	 * @param parent
	 * @return
	 */
	ITranslationTable getTranslationTable(IProject project, Composite parent);
	
	/**
	 * @param parent
	 * @return
	 */
	IMultiTranslationTable getMultiTranslationTable();
	
	/**
	 * @return
	 */
	ITranslationFilterControl getFilterControl();
	
	ITranslationText getTranslationText(IProject project, Composite parent);

}
