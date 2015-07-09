package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;

public class UrlTypePropertyTransformer extends BasePropertyTransformer {

	public UrlTypePropertyTransformer(PropertyType type) {
		super(type);
	}

	/**
	 * need to transfer urlType to type with the value of type
	 */
	@Override
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
		String urlTypeValue = property.getValue();
		
		if (urlTypeValue != null && urlTypeValue.length() != 0) {
			if(urlTypeValue.equalsIgnoreCase("image")) {
				urlTypeValue = "img";
				addAttribute(context, "type", urlTypeValue);
			}
		}
	}
}
