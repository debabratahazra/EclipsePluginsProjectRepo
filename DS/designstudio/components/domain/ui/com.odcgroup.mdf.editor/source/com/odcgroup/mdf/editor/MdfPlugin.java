package com.odcgroup.mdf.editor;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;
import com.odcgroup.mdf.editor.ui.filters.BusinessTypeFilter;
import com.odcgroup.mdf.editor.ui.filters.ClassFilter;
import com.odcgroup.mdf.editor.ui.filters.DatasetFilter;
import com.odcgroup.mdf.editor.ui.filters.EnumFilter;
import com.odcgroup.mdf.editor.ui.filters.MainClassFilter;


/**
 * The main plugin class to be used in the desktop.
 */
public class MdfPlugin extends AbstractUIPlugin {

    private static final Logger LOGGER = Logger.getLogger(MdfPlugin.class);

    
    public static final String SORT_KEY = "AlphaSort";
    
    // The shared instance.
    private static MdfPlugin INSTANCE;    

    protected Collection<Object> clipboard;
    
    // the filters for the viewer
    private Set<ViewerFilter> filters = new HashSet<ViewerFilter>();

    // Resource bundle.
    private Properties resourceBundle = new Properties();
    
    public static final EnumFilter ENUM_FILTER = new EnumFilter();
    public static final ClassFilter CLASS_FILTER = new ClassFilter();
    public static final MainClassFilter MAIN_CLASS_FILTER = new MainClassFilter();
    public static final DatasetFilter DATASET_FILTER = new DatasetFilter();
    public static final BusinessTypeFilter BUSINESSTYPE_FILTER = new BusinessTypeFilter();

    /**
     * The constructor.
     */
    public MdfPlugin() {
        super();
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);
        INSTANCE = this;

        try {
            URL props = FileLocator.find(getBundle(), new Path(
                    "$nl$/plugin.properties"), Collections.EMPTY_MAP);

            if (props != null) {
                resourceBundle.load(props.openStream());
            } else {
                LOGGER.warn("Could not find localized resources");
            }
        } catch (IOException x) {
            LOGGER.error("Could not load localized resources", x);
        }
    }

    public void stop(BundleContext context) throws Exception {
        INSTANCE = null;
        MdfProjectRepository.dispose();
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     */
    public static MdfPlugin getDefault() {
        return INSTANCE;
    }

    /**
     * Returns the workspace instance.
     */
    public static IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        return getResourceString(key, key);
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'default' if not
     * found.
     */
    public static String getResourceString(String key, String deflt) {
        Properties bundle = MdfPlugin.getDefault().getResourceBundle();
        String value = bundle.getProperty(key, key);
        if (value == null) value = deflt;
        return value;
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public Properties getResourceBundle() {
        return resourceBundle;
    }
    
    public void addFilter(ViewerFilter filter) {
    	if (filter != null) {
    		filters.add(filter);
    	}
    }
    
    public void removeFilter(ViewerFilter filter) {
    	if (filters.contains(filter)) {
    		filters.remove(filter);
    	}
    }
    
    /**
     * @return
     */
    public Collection getFilters() {
    	manageFilterByPreference(MdfPreferencePage.P_MDF_CLASS_FILTER, CLASS_FILTER);
    	manageFilterByPreference(MdfPreferencePage.P_MDF_MAIN_CLASS_FILTER, MAIN_CLASS_FILTER);
    	manageFilterByPreference(MdfPreferencePage.P_MDF_DATASET_FILTER, DATASET_FILTER);
    	manageFilterByPreference(MdfPreferencePage.P_MDF_ENUM_FILTER, ENUM_FILTER);
    	return filters;
    }
    
    public boolean getSortStatus() {
    	if(getPreferenceStore().getBoolean(SORT_KEY))
    		return true;
		return false;
    }
    
    /**
     * @param key
     * @param filter
     */
    private void manageFilterByPreference(String key, ViewerFilter filter) {
    	if(getPreferenceStore().getBoolean(key)) {
    		removeFilter(filter);
    	} else {
    		addFilter(filter);
    	}
    }

    public ResourceLocator getResourceLocator() {
        return new ResourceLocator() {

            public URL getBaseURL() {
                return getBundle().getEntry("/");
            }

            public Object getImage(String key) {
                return MdfPlugin.this.getImage(key);
            }

            public String getString(String key) {
                return getResourceString(key);
            }

            public String getString(String key, boolean translate) {
                return getString(key);
            }

            public String getString(String key, Object[] substitutions) {
                String pattern = getResourceString(key);
                return MessageFormat.format(pattern, substitutions);
            }

            public String getString(String key, Object[] substitutions,
                    boolean translate) {
                return getString(key, substitutions);
            }
        };
    }

    public static synchronized Image getImage(String key) {
        ImageRegistry registry = INSTANCE.getImageRegistry();
        Image image = registry.get(key);

        if (image == null) {
            registry.put(key, getImageDescriptor(key));
            image = registry.get(key);
        }

        return image;
    }

    public static ImageDescriptor getImageDescriptor(String key) {
        return getImageDescriptor(key, false);
    }
    
    public static ImageDescriptor getImageDescriptor(String key, boolean keyIncludesExtn) {
    	if (!keyIncludesExtn) {
    		key = key+".gif";
    	} 
        URL url = INSTANCE.getBundle().getEntry("icons/" + key);
        return ImageDescriptor.createFromURL(url);    	
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeDefaultPreferences(org.eclipse.jface.preference.IPreferenceStore)
     */
    protected void initializeDefaultPreferences(IPreferenceStore store) {
        // These settings will show up when Preference dialog
        // opens up for the first time.
        store.setDefault(MdfPreferencePage.P_SHOW_CARDINALITIES, true);
        store.setDefault(MdfPreferencePage.P_SHOW_TYPE, true);
        store.setDefault(MdfPreferencePage.P_DEFAULT_MODELS_FOLDER, "models");
        
		getPreferenceStore().setDefault(MdfPreferencePage.P_MDF_CLASS_FILTER, true);
		getPreferenceStore().setDefault(MdfPreferencePage.P_MDF_MAIN_CLASS_FILTER, true);
		getPreferenceStore().setDefault(MdfPreferencePage.P_MDF_DATASET_FILTER, true);
		getPreferenceStore().setDefault(MdfPreferencePage.P_MDF_ENUM_FILTER, true);
    }

	public Collection<Object> getClipboard() {
		return clipboard;
	}

	public void setClipboard(Collection<Object> clipboard) {
		this.clipboard = clipboard;
	}
	
    public void logError(String msg, Throwable exception) {
    	log(IStatus.ERROR, msg, exception);
    }

    public void logWarning(String msg, Throwable exception) {
    	log(IStatus.WARNING, msg, exception);
    }

    public void logInfo(String msg, Throwable exception) {
    	log(IStatus.INFO, msg, exception);
    }

    protected void log(int severity, String msg, Throwable exception) {
    	getLog().log(new Status(severity, getBundle().getSymbolicName(), IStatus.OK, msg, exception));    	
    }

}
