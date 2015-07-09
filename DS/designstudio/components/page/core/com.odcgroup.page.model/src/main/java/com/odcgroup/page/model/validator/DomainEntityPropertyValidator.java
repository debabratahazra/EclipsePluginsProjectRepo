package com.odcgroup.page.model.validator;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * @author atr
 */
public class DomainEntityPropertyValidator  extends AbstractPropertyValidator implements PropertyValidator {

	/**
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		Property p = getProperty();
		if (p != null) {
			if(!((String)value).isEmpty() && value != null){
			MdfName qName = MdfNameFactory.createMdfName((String)value);
			Widget w = p.getWidget();
			w.setPropertyValue(PropertyTypeConstants.BEAN_NAME, qName.getLocalName());
			w.setPropertyValue(PropertyTypeConstants.BEAN_DEFINE, "DSBean."+qName.getDomain()+"."+qName.getLocalName());
			}
		}
		return null;
	}

}
