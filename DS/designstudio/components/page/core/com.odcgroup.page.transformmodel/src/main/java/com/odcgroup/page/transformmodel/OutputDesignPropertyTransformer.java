package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.widgets.ISearchField;

/**
 * @author pkk
 *
 */
public class OutputDesignPropertyTransformer extends BasePropertyTransformer {
	
	/**
	 * 
	 */
	private static final String URI_PREFIX = "/wui/ajax-xml/autocompleteoutput/";

	/**
	 * @param type
	 */
	public OutputDesignPropertyTransformer(PropertyType type) {
		super(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Property property)
			throws CoreException {
		Widget widget = property.getWidget();
		ISearchField searchField = WidgetHelper.getSearchField(widget);
		if (searchField == null) {
			return;
		}			
		URI uri = searchField.getOutputDesignURI();
		if (uri == null) {
			return;
		}
		String modelName = getModelName(uri.trimFragment().toString(), uri.fileExtension());
		String value = URI_PREFIX+modelName;
		
		Attr a = context.getDocument().createAttribute("uri");		 
		a.setValue(value);	
		context.getParentElement().setAttributeNode(a);
	}
	
	/**
	 * @param resourceURL
	 * @return string
	 */
	private static String getModelName(String resourceURL, String extn) {
		String outputfldr = "autocompleteoutput/";
		if (resourceURL.contains(outputfldr)) {
			int lind = resourceURL.lastIndexOf(outputfldr);
			resourceURL = resourceURL.substring(lind);
			int index = resourceURL.indexOf("/");
			return resourceURL.substring(index+1, resourceURL.length() - extn.length()-1);
		} else {
			int index = resourceURL.lastIndexOf("/");
			return resourceURL.substring(index + 1, resourceURL.length() - extn.length()-1);
		}
	}

}
