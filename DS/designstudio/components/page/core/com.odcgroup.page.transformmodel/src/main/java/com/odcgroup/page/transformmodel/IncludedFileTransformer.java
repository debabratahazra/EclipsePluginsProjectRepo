package com.odcgroup.page.transformmodel;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;

/**
 * Tranformer implementation for the includedFile property of the FilterCriteria widget
 * 
 * @author pkk
 *
 */
public class IncludedFileTransformer extends BasePropertyTransformer {

	/**
	 * @param type
	 */
	public IncludedFileTransformer(PropertyType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.PropertyTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Property)
	 */
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
		String attributeName = TransformerConstants.C_INCLUDE_ATTRIBUTE_NAME;
		String attributeValue = property.getValue();
		if (!StringUtils.isEmpty(attributeValue)) {
			URI uri = URI.createURI(attributeValue);
			attributeValue = getPath(uri);
		}
		attributeValue = TransformerConstants.COCOON_PROTOCOL + attributeValue;

		Attr a = context.getDocument().createAttribute(attributeName);		 
		a.setValue(attributeValue);	
		context.getParentElement().setAttributeNode(a);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.BasePropertyTransformer#isTransformer(com.odcgroup.page.model.Property)
	 */
	public boolean isTransformer(Property property) {
		return getPropertyType().equals(property.getType());
	}	
	
	/**
	 * @param uri
	 * @return String
	 */
	private String getPath(URI uri) {
		String value = null;
		String mFolder = WidgetTypeConstants.MODULE.toLowerCase();
		uri = uri.trimFileExtension();
		value = uri.path();
		if (value.contains("/"+mFolder+"/")) {
			int index = value.indexOf("/"+mFolder)+mFolder.length()+1;
			value = value.substring(index);
		}
		value = mFolder+value;
		return value;
	}

}
