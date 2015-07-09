package com.odcgroup.mdf.editor.ui.providers.properties;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfPropertySourceProvider implements IPropertySourceProvider {

    /**
     * Constructor for MdfPropertySourceProvider
     */
    public MdfPropertySourceProvider() {
        super();
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySourceProvider#getPropertySource(java.lang.Object)
     */
    public IPropertySource getPropertySource(Object obj) {
		if (obj instanceof MdfModelElement) {
			return new MdfPropertySource((MdfModelElement) obj); 
		}

        return null;
    }
}
