/*
 * 
 */
package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.zealcore.se.core.ifw.assertions.IAssertionResult;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.internal.LogMark;

public class PropertySourceAdapterFactory implements IAdapterFactory {

    private static final String DESCRIPTION = "Description";

    private static final String NAME = "Name";

    private static final String UNKNOWN = "???";
    
    private static final String LOGFILE = "logFile";
    
    private static final String EVENTTYPE = "eventType";
    
    private static final String CREATED = "created";
    
    private static final String NOTE = "note";

    private static PropertySourceAdapterFactory instance;

    private static final Class<?>[] SUPPORTED_TYPES = { IPropertySource.class };

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Object adaptableObject,
            final Class adapterType) {
    	
    	if (adaptableObject instanceof ITimeCluster) {
    		final ITimeCluster cluster = (ITimeCluster) adaptableObject;
    		return toPropertySource(cluster);
    	}

    	if (adaptableObject instanceof ILogMark){
        	final ILogMark logmark = (ILogMark)adaptableObject;
            	return new LogMarkPropertySource((LogMark)logmark);
        }

        if (adaptableObject instanceof IObject) {
            final IObject named = (IObject) adaptableObject;
            return new PropertySource(named);
        }

        if (adaptableObject instanceof SEProperty) {
            final SEProperty property = (SEProperty) adaptableObject;
            final Object data = property.getData();
            if (data instanceof IObject) {
                final IObject named = (IObject) data;
                return new PropertySource(named);
            }
            final StaticPropertySource propertySource = new StaticPropertySource();

            final String name = property.getName() == null ? PropertySourceAdapterFactory.UNKNOWN
                    : property.getName();
            final String toString = property.getData() == null ? PropertySourceAdapterFactory.UNKNOWN
                    : property.getData().toString();
            propertySource.descriptorMap.put(name, toString);
        }

        if (adaptableObject instanceof IAssertionSetResult) {
            final IAssertionSetResult result = (IAssertionSetResult) adaptableObject;
            final StaticPropertySource propertySource = new StaticPropertySource();
            propertySource.category = "AssertionSet";
            propertySource.descriptorMap.put(PropertySourceAdapterFactory.NAME,
                    result.getAssertionSet().getName());
            propertySource.descriptorMap.put(
                    PropertySourceAdapterFactory.DESCRIPTION, result
                            .getAssertionSet().getDescription());
            return propertySource;
        }

        if (adaptableObject instanceof IAssertionResult) {
            final IAssertionResult result = (IAssertionResult) adaptableObject;
            final StaticPropertySource propertySource = new StaticPropertySource();
            propertySource.category = "Assertion";
            propertySource.descriptorMap.put(PropertySourceAdapterFactory.NAME,
                    result.getAssertion().getName());
            propertySource.descriptorMap.put(
                    PropertySourceAdapterFactory.DESCRIPTION, result
                            .getAssertion().getDescription());
            propertySource.descriptorMap.put("Severity", result.getAssertion()
                    .getSeverity());
            propertySource.descriptorMap.put("Condition", result.getAssertion()
                    .getCondition());
            return propertySource;

        }

        // TODO More adaptions
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    public Class<?>[] getAdapterList() {
        return PropertySourceAdapterFactory.SUPPORTED_TYPES.clone();
    }

    /**
     * Gets the singleton instance.
     * 
     * @return the instance
     */
    public static PropertySourceAdapterFactory getInstance() {
        if (PropertySourceAdapterFactory.instance == null) {
            PropertySourceAdapterFactory.instance = new PropertySourceAdapterFactory();
        }
        return PropertySourceAdapterFactory.instance;
    }

    private IPropertySource toPropertySource(final ITimeCluster cluster) {
        final StaticPropertySource propertySource = new StaticPropertySource();
        propertySource.category = cluster.toString();
        propertySource.descriptorMap.put("Max", cluster.getMax());
        propertySource.descriptorMap.put("Min", cluster.getMin());
        propertySource.descriptorMap.put("Length", cluster.getMax()
                - cluster.getMin());
        propertySource.descriptorMap.put("Current", cluster.getCurrentTime());
        propertySource.descriptorMap.put("Chained", cluster.isChained());
        return propertySource;
    }

    /**
     * Non editable PropertySource
     * 
     * @author stch
     * 
     */
    private static class StaticPropertySource implements IPropertySource {

        private String category = "";

        private final Map<Object, Object> descriptorMap = new LinkedHashMap<Object, Object>();

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
         */
        public Object getEditableValue() {
            // Not editable
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
         */
        public IPropertyDescriptor[] getPropertyDescriptors() {
            final List<IPropertyDescriptor> descs = new ArrayList<IPropertyDescriptor>();
            for (final Entry<Object, Object> e : descriptorMap.entrySet()) {
                final TextPropertyDescriptor textPropertyDescriptor = new TextPropertyDescriptor(
                        e.getKey(), e.getKey().toString());
                textPropertyDescriptor.setCategory(category);
                // Object, display name
                descs.add(textPropertyDescriptor);
            }
            return descs.toArray(new IPropertyDescriptor[0]);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
         */
        public Object getPropertyValue(final Object arg0) {
            final Object value = descriptorMap.get(arg0);
            // the key in descritorMap is the value -> use to string
            return value == null ? "null" : value.toString();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
         */
        public boolean isPropertySet(final Object arg0) {
            // 
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
         */
        public void resetPropertyValue(final Object arg0) {
        // nada

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
         *      java.lang.Object)
         */
        public void setPropertyValue(final Object arg0, final Object arg1) {}
    }
    
    /*
     * Provides customized PropertySource for log mark selection
     */
   private static class LogMarkPropertySource implements IPropertySource {
    	LogMark logMark;
    	
    	public LogMarkPropertySource(final LogMark logMark) {
    		this.logMark = logMark;
		}

		public Object getEditableValue() {
			return null;
		}

		public IPropertyDescriptor[] getPropertyDescriptors() {
			return new IPropertyDescriptor[] {
					new PropertyDescriptor(LOGFILE, "Logfile"),
					new PropertyDescriptor(EVENTTYPE, "Type"),
					new PropertyDescriptor(CREATED, "Time"),
					new PropertyDescriptor(NOTE, "Note")
			};
		}
		
		/*
		 * Get the three fields of log mark.
		 */
		public Object getPropertyValue(Object id) {
			if (LOGFILE.equals(id))
				return logMark.getLogfile();
			else if (EVENTTYPE.equals(id))
				return logMark.getEventType();
			else if (CREATED.equals(id))
				return TimeFormat.format(
                        "h:m:s ns", logMark
                        .getLogmark());
			else if (NOTE.equals(id))
				return logMark.getNote();
			return null;
		}

		public boolean isPropertySet(Object id) {
			return false;
		}

		public void resetPropertyValue(Object id) {
			// Not supported.
		}

		public void setPropertyValue(Object id, Object value) {
			// Not supported.
		}
    }
}
