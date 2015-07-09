package com.odcgroup.page.ui.properties.sections;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.TranslationSupport;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * @author atr
 */
public class TranslationSectionFilter implements IFilter {

	/**
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object object) {
		if (object instanceof EditPart) {
			Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
			Widget root= w.getRootWidget();
			Property pro=root.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
			    if(pro!=null && pro.getValue().equals(PropertyTypeConstants.BOX_TYPE_XTOOLTIP)) {
				if( w.getType().getTranslationSupport().equals(TranslationSupport.TEXT)){
				          return false;
				}
			    }	
			return w.getType().translationSupported();
		}
		return false;
	}

}
