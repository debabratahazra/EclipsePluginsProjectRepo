package com.odcgroup.page.metamodel.display.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.framework.Bundle;

import com.odcgroup.page.metamodel.PageMetamodelActivator;
import com.odcgroup.page.metamodel.display.DisplayFormatConstants;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * An implementation of the DisplayFormat which uses the 
 * Eclipse Preferences mechanism.
 * 
 * @author atr
 * @since DS 1.40.0
 */
class DisplayFormatImpl extends AbstractDisplayFormat {
	
    /**
	 * @see com.odcgroup.page.metamodel.display.internal.AbstractDisplayFormat#dispose()
	 */
	/*package*/ void dispose() {
	}	
			
	/**
	 * @see com.odcgroup.page.metamodel.display.DisplayFormat#setDataFormats(java.util.List)
	 */
	public void setDataFormats(List<String> formats) {
		String values = StringUtils.join(formats, ',');
		getPreferenceStore().put(DisplayFormatConstants.PROPERTY_IMPORTED_DATA_FORMATS, values);
		getPreferenceStore().flush();
	}

	/**
	 * @see com.odcgroup.page.metamodel.display.DisplayFormat#getDataFormats()
	 */
	public List<String> getDataFormats() {
		String formats = getPreferenceStore().get(DisplayFormatConstants.PROPERTY_IMPORTED_DATA_FORMATS, "");
		List<String> strList = Arrays.asList(formats.split(","));
		Collections.sort(strList);
		return strList;
	}
	
	/**
	 * @see com.odcgroup.page.metamodel.display.DisplayFormat#getDefaultDataFormats()
	 */
	public List<String> getDefaultDataFormats() {
		IEclipsePreferences preferences = new DefaultScope().getNode(PageMetamodelActivator.PLUGIN_ID);
		String formats = preferences.get(DisplayFormatConstants.PROPERTY_IMPORTED_DATA_FORMATS, "");
		List<String> strList = Arrays.asList(formats.split(","));
		Collections.sort(strList);
		return strList;	}

	/**
	 * @param bundle
	 * @param projectPreferences
	 */
	public DisplayFormatImpl(Bundle bundle, ProjectPreferences projectPreferences) {
		super(bundle, projectPreferences);
	}

}