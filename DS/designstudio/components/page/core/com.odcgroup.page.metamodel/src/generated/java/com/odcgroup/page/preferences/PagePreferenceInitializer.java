package com.odcgroup.page.preferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.odcgroup.page.metamodel.PageMetamodelActivator;
import com.odcgroup.page.metamodel.display.DisplayFormatConstants;

/**
 * @author kkr
 *
 */
public class PagePreferenceInitializer extends AbstractPreferenceInitializer {
	
	/** The default human properties file location	 */
	private static final String HUMANPATTERNS_DEFAULT_LOCATION = "src/main/resources/HumanPatterns.PROPERTIES";

	/**
	 * 
	 */
	public PagePreferenceInitializer() {
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(PageMetamodelActivator.PLUGIN_ID);
		
		node.put(DisplayFormatConstants.PROPERTY_IMPORTED_DATA_FORMATS,	loadDefaultDataFormats());
		
	}

	/**
	 * @return default data formats (comma separated)
	 */
	protected String loadDefaultDataFormats() {
		String values = "";
		URL url = FileLocator.find(PageMetamodelActivator.getDefault().getBundle(), new Path(HUMANPATTERNS_DEFAULT_LOCATION), null);
		try { 
			List<String> formats = fetchHumanProperties(url.openStream());
			values = StringUtils.join(formats, ',');
		} catch (IOException ex) {
			String message = "Unable to load default human expressions";
			IStatus status = new Status(IStatus.ERROR, PageMetamodelActivator.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, message, ex);
			PageMetamodelActivator.getDefault().getLog().log(status);
		}
		return values;
	}

	/**
	 * @param inputStream 
	 * @return String a comma delimited string of data formats
	 * @throws IOException 
	 */
	public List<String> fetchHumanProperties(InputStream inputStream) throws IOException {
		List<String> formats =  new ArrayList<String>();
		Properties properties = new Properties();
		properties.load(inputStream);		
		Set<Object> keys = properties.keySet();
		for (Object object : keys) {
			String key = (String) object;
			if (!(key.contains("alignment") || key.contains("xls"))) {
				formats.add(key);
			}
		}
		return formats;
	}	

}
