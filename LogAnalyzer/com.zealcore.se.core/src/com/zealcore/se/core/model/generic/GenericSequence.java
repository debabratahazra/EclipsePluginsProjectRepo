package com.zealcore.se.core.model.generic;

import java.util.List;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractSequence;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

/**
 * A sequence is used to keep track of sequences that are used in the Sequence
 * Diagram. There must exist at least one sequence in the import context to be
 * able to open the Sequence Diagram. If more than one sequence exists, several
 * different Sequence Diagram views can be opened.
 * 
 * @author cafa
 * 
 */
public class GenericSequence extends AbstractSequence implements
        IGenericLogItem {

    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "Sequence";

    /**
     * Constructor setting the log event type name to "Sequence".
     */
    public GenericSequence() {
        adapter.setTypeName(TYPE_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#addProperty(java.lang
     * .String, java.lang.Object)
     */
    public void addProperty(final String name, final Object value) {
        adapter.addProperty(name, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#getZPropertyAnnotations()
     */
    @Override
    public List<SEProperty> getZPropertyAnnotations() {
        List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(adapter.toSEProperties());

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#setTypeName(java.lang
     * .String)
     */
    public void setTypeName(final String typeName) {
        adapter.setTypeName(typeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#getProperty(java.lang
     * .String)
     */
    public Object getProperty(final String key) {
        return adapter.getProperty(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.generic.IGenericLogItem#getTypeName()
     */
    @ZCProperty(name = "Type Name", description = "Type Name")
    public String getTypeName() {
        return adapter.getTypeName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.generic.IGenericLogItem#properties()
     */
    public Map<String, Object> properties() {
        return adapter.properties();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#getType()
     */
    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }
}
