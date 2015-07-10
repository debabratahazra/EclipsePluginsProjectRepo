package com.zealcore.se.core.model.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProperty;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;

/**
 * A generic type is used when a new log event type must be created.
 * 
 * @author cafa
 * 
 */
public class GenericType implements IType, Comparable<GenericType> {

    private String id;

    private static SortedMap<String, IType> typeByNameMap = new TreeMap<String, IType>();

    private static class Property implements IProperty {
        private final String name;

        private final Class<? extends Object> type;

        Property(final String key, final Class<? extends Object> type) {
            this.name = key;
            this.type = type;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.zealcore.se.core.model.IProperty#getDescription()
         */
        public String getDescription() {
            return "";
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.zealcore.se.core.model.IProperty#getName()
         */
        public String getName() {
            return this.name;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.zealcore.se.core.model.IProperty#getReturnType()
         */
        @SuppressWarnings("unchecked")
        public Class getReturnType() {
            return this.type;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.zealcore.se.core.model.IProperty#getValue(com.zealcore.se.core
         * .model.IObject)
         */
        public Object getValue(final IObject object) {
            if (object instanceof IGenericLogItem) {
                final IGenericLogItem event = (IGenericLogItem) object;
                return event.getProperty(getName());
            }
            throw new IllegalArgumentException(object + " is not a "
                    + IGenericLogItem.class.getSimpleName());
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.zealcore.se.core.model.IProperty#isPlotable()
         */
        public boolean isPlotable() {
            boolean retValue;
            if ("nTick".equalsIgnoreCase(this.name)) {
                retValue = false;
            } else if ("Tick".equalsIgnoreCase(this.name)) {
                retValue = false;
            } else if ("Entry".equalsIgnoreCase(this.name)) {
                retValue = false;
            } else {
                retValue = Number.class.isAssignableFrom(this.type);
            }
            return retValue;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.zealcore.se.core.model.IProperty#isSearchable()
         */
        public boolean isSearchable() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.zealcore.se.core.model.IProperty#isGeneric()
         */
        public boolean isGeneric() {
            return true;
        }
    }

    private final List<IProperty> properties = new ArrayList<IProperty>();

    private final String name;

    private final Class<? extends IGenericLogItem> baseClass;

    /**
     * @param event
     */
    public GenericType(final IGenericLogItem event) {
        this.name = event.getTypeName();
        for (final Entry<String, Object> entry : event.properties().entrySet()) {
        	String key = entry.getKey();
        	Object object = entry.getValue();
        	try {
        		if (object != null) {
        			Property property = new Property(key, object.getClass());
        			this.properties.add(property);
        		}
        	} catch(Exception e) {
        		throw new ImportException(e);
        	}
        }

        this.baseClass = event.getClass();
        this.properties.addAll(ReflectiveType.valueOf(this.baseClass)
                .getProperties());
    }

    static IType valueOf(final IGenericLogItem logItem) {

        IType type = GenericType.typeByNameMap.get(logItem.getTypeName());
        if (type == null) {
            type = new GenericType(logItem);
            GenericType.typeByNameMap.put(logItem.getTypeName(), type);
        }
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IType#getId()
     */
    public String getId() {
        if (this.id == null) {
            this.id = "com.zealcore.se.core.model.generic." + getName();
        }
        return this.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IType#getName()
     */
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IType#getProperties()
     */
    public Collection<IProperty> getProperties() {
        return this.properties;
    }

    public boolean isA(final IType type) {
        // TODO Need test if this actually works as expected
        if (ReflectiveType.valueOf(this.baseClass).isA(type)) {
            return true;
        }
        return type.getId().equals(getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IType#isSearchable()
     */
    public boolean isSearchable() {
        return true;
    }

    static Collection<IType> getTypes() {
        return GenericType.typeByNameMap.values();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.IType#isPlottable()
     */
    public boolean isPlottable() {
        // if (!ITimed.class.isAssignableFrom(baseClass)) {
        // return false;
        // }
        for (IProperty p : getProperties()) {
            if (p.isPlotable()) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final GenericType arg) {
        return this.getName().compareTo(arg.getName());
    }

    /**
     * @return the types base class
     */
    public Class<? extends IGenericLogItem> getBaseClass() {
        return this.baseClass;

    }
}
