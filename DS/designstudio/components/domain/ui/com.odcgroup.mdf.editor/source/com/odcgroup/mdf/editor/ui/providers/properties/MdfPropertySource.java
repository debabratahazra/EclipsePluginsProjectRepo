package com.odcgroup.mdf.editor.ui.providers.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.mdf.editor.ui.providers.MdfBaseProvider;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfPropertySource extends MdfBaseProvider 
	implements IPropertySource {	
	private MdfModelElement element = null;

    /**
     * Constructor for MdfPropertySource
     */
    public MdfPropertySource(MdfModelElement element) {
        super();
		this.element = element;
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    public Object getEditableValue() {
        return element;
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
		return getPropertyDescriptors(element.getClass());
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(Object id) {
		return getPropertyValue(element, id.toString());
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(Object id) {        
        return (getPropertyValue(id) != null);
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(Object id) {
		setPropertyValue(id, null);
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
     */
    public void setPropertyValue(Object id, Object value) {
		setPropertyValue(element, id.toString(), value);
	}
}
