package com.odcgroup.iris.metadata.generation.tests;

import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;


/**
 * The EMEntity object is a bit of a pain to navigate and get things from.
 * This class provides some helper methods so we can say "get me the CUSTOMER field, and I don't care where it is just find it and give it to me" 
 *
 * @author agoulding
 */
public class EMEntityHelper {

	public static EMProperty getProperty(EMEntity entity, String propertyName) {
		for (EMProperty property : entity.getProperties()) {
			if (property.getName().equals(propertyName)) {
				return property;
			}
			if (property.getSubProperties().size() > 0) {
				EMProperty result = getProperty(property, propertyName);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}


	public static EMProperty getProperty(EMProperty parentProperty, String propertyName) {
		for (EMProperty property : parentProperty.getSubProperties()) {
			if (property.getName().equals(propertyName)) {
				return property;
			}
		}
		return null;
	}
	
	public static EMTerm getTerm(EMEntity emEntity, String propertyName, String termName) {
		// First get the property
		EMProperty property = getProperty(emEntity, propertyName);
		if (property == null) {
			return null;
		}
		for (EMTerm emTerm : property.getVocabularyTerms()) {
			if (emTerm.getName().equals(termName)) {
				return emTerm;
			}
		}
		return null;
	}

	public static String getTermValue(EMEntity emEntity, String propertyName, String termName) {
		EMTerm emTerm = getTerm(emEntity, propertyName, termName);
		if (emTerm != null) {
			return emTerm.getValue();
		}
		return null;
	}

	public static String getTermValue(EMEntity emEntity, String propertyName, EMTermType termType) {
		return getTermValue(emEntity, propertyName, termType.getValue());
	}

}
