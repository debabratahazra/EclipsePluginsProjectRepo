package com.odcgroup.page.model.corporate;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * This is the corporate design interface.
 * 
 * @author atr
 * @version 1.1
 */
public interface CorporateDesign {
	
	/**
	 * @param listener
	 */
	void addPreferenceChangeListener(IPreferenceChangeListener listener);

	/**
	 * @param listener
	 */
	void removePreferenceChangeListener(IPreferenceChangeListener listener);	
	
	/**
	 * @return IPreferenceStore
	 */
	ProjectPreferences getPropertyStore();

	/**
	 * Gets the horizontal alignment of the label.
	 * 
	 * @return String The horizontal alignment of the label
	 */
	String getLabelHorizontalAlignment();
	
	/**
	 * Gets the vertical alignment of the label.
	 * 
	 * @return String The vertical alignment of the label
	 */
	String getLabelVerticalAlignment();	
	
	/**
	 * Gets the horizontal alignment of the field.
	 * 
	 * @return String The horizontal alignment of the field
	 */
	String getFieldHorizontalAlignment();
	
	/**
	 * Gets the vertical alignment of the field.
	 * 
	 * @return String The vertical alignment of the field
	 */
	String getFieldVerticalAlignment();	

	/**
	 * This methods returns the FieldType given a business primitive type name.
	 * 
	 * @param primitiveType - the  primitive type
	 * @return the name of the corresponding field type
	 */
	String getFieldType(MdfEntity primitiveType);

	/**
	 * @return the default value to set regarding the page size of a Table/Tree widget.
	 */
	String getTablePageSize();

}
