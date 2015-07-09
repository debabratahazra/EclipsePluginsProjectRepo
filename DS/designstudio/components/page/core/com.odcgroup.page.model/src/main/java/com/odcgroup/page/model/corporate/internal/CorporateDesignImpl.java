package com.odcgroup.page.model.corporate.internal;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;

import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.corporate.CorporateDesignConstants;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * An implementation of the CorporateDesign which uses the 
 * Eclipse Preferences mechanism.
 * 
 * @author atr
 */
class CorporateDesignImpl extends AbstractCorporateDesign {
	
	/**
	 * Binds MDF Business Type name to a field type.
	 */
	private HashMap <String, String> widgetPrimitiveTypes = null;
	{
		widgetPrimitiveTypes = new HashMap<String, String>();
		
		// check box
		widgetPrimitiveTypes.put("BOOLEAN", WidgetTypeConstants.CHECKBOX);
		
		// text field or text are
		widgetPrimitiveTypes.put("BYTE", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("INTEGER", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("DECIMAL", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("PERCENT", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("PERCENTAGE", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("CHAR", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("STRING", WidgetTypeConstants.TEXTFIELD);
		widgetPrimitiveTypes.put("TEXT", WidgetTypeConstants.TEXTAREA);
		widgetPrimitiveTypes.put("TIME", WidgetTypeConstants.TEXTFIELD);

		// calendar
		widgetPrimitiveTypes.put("DATE", WidgetTypeConstants.CALDATE_FIELD);
		widgetPrimitiveTypes.put("DATETIME", WidgetTypeConstants.CALDATE_FIELD);
		
		// hyperlink
		widgetPrimitiveTypes.put("URI", WidgetTypeConstants.TEXTFIELD);
		
		// file chooser
		widgetPrimitiveTypes.put("MEDIA", WidgetTypeConstants.TEXTFIELD);
	}		
	
	/**
	 * @see com.odcgroup.page.model.corporate.internal.AbstractCorporateDesign#dispose()
	 */
	/*package*/ void dispose() {
	}	
		
	/**
	 * Gets the horizontal alignment of the label.
	 * 
	 * @return String The horizontal alignment of the label
	 */
	public String getLabelHorizontalAlignment() {
		return getPropertyStore().get(CorporateDesignConstants.PROPERTY_LABEL_HORIZONTAL_ALIGNMENT, null);
	}	
	
	/**
	 * Gets the vertical alignment of the label.
	 * 
	 * @return String The vertical alignment of the label
	 */
	public String getLabelVerticalAlignment() {
		return CorporateDesignConstants.ALIGN_CENTER_VALUE;
	}	

	/**
	 * Gets the horizontal alignment of the field.
	 * 
	 * @return String The horizontal alignment of the field
	 */
	public String getFieldHorizontalAlignment() {
		return getPropertyStore().get(CorporateDesignConstants.PROPERTY_FIELD_HORIZONTAL_ALIGNMENT, null);
	}	

	/**
	 * Gets the vertical alignment of the field.
	 * 
	 * @return String The vertical alignment of the field
	 */
	public String getFieldVerticalAlignment() {
		return CorporateDesignConstants.ALIGN_CENTER_VALUE;
	}
	
	/**
	 * This methods returns the FieldType given a business primitive type name.
	 * 
	 * @param primitiveType - the  primitive type
	 * @return the name of the corresponding field type
	 */
	public String getFieldType(MdfEntity primitiveType) {
		Assert.isNotNull(primitiveType);
		
		if (primitiveType instanceof MdfBusinessType) {
			MdfBusinessType bizType = (MdfBusinessType)primitiveType;
			String widgetType = GUIAspect.getDisplayTypeValue(bizType);
			if (StringUtils.isNotEmpty(widgetType)) {
				return widgetType;
			} else {
				primitiveType = bizType.getType();
			}
		}
		
		if (primitiveType instanceof MdfEnumeration) {
			return WidgetTypeConstants.COMBOBOX;
		}
	
		String primitiveTypeName = primitiveType.getName();	
		
		String name = (String) widgetPrimitiveTypes.get(primitiveTypeName.toUpperCase());
		if (name == null) {
			// default
			name = WidgetTypeConstants.TEXTFIELD;
		}
		return name;
	}	
	
	/**
	 * @see com.odcgroup.page.model.corporate.CorporateDesign#getTablePageSize()
	 */
	public String getTablePageSize() {
		return ""+getPropertyStore().getInt(CorporateDesignConstants.PROPERTY_TABLE_PAGE_SIZE, 0);
	}

	/**
	 * @param preferences
	 */
	public CorporateDesignImpl(ProjectPreferences preferences) {
		super(preferences);
	}
	
}