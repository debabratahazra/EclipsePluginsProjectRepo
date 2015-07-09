package com.odcgroup.page.model.properties.enabled;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.mdf.DomainConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Represents the a simplified or advanced condition for the "Enabled is based on"
 * condition
 * @author yan
 */
public class EnabledIsBaseOnCondition {

	/** Token used to recognized an advanced condition */
	public static final String ADVANCED_VALUE = "advanced";

	/** True if the condition is simplified, false if the condition is advanced */
	private boolean simplified;

	/** Simplified condition */
	private MdfName simplifiedCondition;
	
	/** Advanced condition */
	private String advancedCondition;

	/**
	 * Create a new condition 
	 * @param simplified 
	 * @param simplifiedCondition
	 * @param advancedCondition 
	 */
	public EnabledIsBaseOnCondition(boolean simplified, MdfName simplifiedCondition, String advancedCondition) {
		this.simplified = simplified;
		this.simplifiedCondition = simplifiedCondition;
		this.advancedCondition = advancedCondition;
	}
	
	/**
	 *  New instance of condition read from a widget property
	 * @param widget 
	 */
	public EnabledIsBaseOnCondition(Widget widget) {
		Property enabledIsBasedOn = widget.findProperty(EnabledConstants.ENABLED_IS_BASE_ON_PROPERTY_NAME);
		if (enabledIsBasedOn != null) {
			this.simplified = !ADVANCED_VALUE.equals(enabledIsBasedOn.getValue());
		} else {
			this.simplified = true;
		}

		Property simplifedProperty = widget.findProperty(EnabledConstants.ENABLED_IS_BASE_ON_SIMPLIFED_PROPERTY_NAME);
		if (simplifedProperty != null && StringUtils.isNotBlank(simplifedProperty.getValue())) {
			this.simplifiedCondition = MdfNameFactory.createMdfName(simplifedProperty.getValue());
		} else {
			this.simplifiedCondition = null;
		}

		Property advancedProperty = widget.findProperty(EnabledConstants.ENABLED_IS_BASE_ON_ADVANCED_PROPERTY_NAME);
		if (advancedProperty != null) {
			this.advancedCondition = advancedProperty.getValue();
		} else {
			this.advancedCondition = "";
		}
	}
	
	/**
	 * @return true if the condition is a simplified condition, false
	 *         if it is an advanced condition
	 */
	public boolean isSimplified() {
		return simplified;
	}

	/**
	 * @return boolean True if it is an empty condition
	 */
	public boolean isEmpty() {
		return StringUtils.isEmpty(this.toString());
	}
	
	/**
	 * @return the simplified condition
	 */
	public MdfName getSimplifiedCondition() {
		return simplifiedCondition;
	}

	/**
	 * @return the advanced condition
	 */
	public String getAdvancedCondition() {
		return advancedCondition;
	}
	
	/**
	 * @return the simplified or advanced condition
	 */
	public Object getCondition() {
		if (simplified) {
			return simplifiedCondition;
		} else {
			return advancedCondition;
		}
	}
	
	/**
	 * @return the string view of the condition
	 */
	public String toString() {
		String result = ADVANCED_VALUE;
		if (simplified) {
			result = "";
			if (simplifiedCondition!=null) {
				String localName = simplifiedCondition.getLocalName(); 
				String parts[] = localName.split(DomainConstants.ENTITY_SEPARATOR);
				if (parts.length > 1) {
					result = parts[1]; // property
				}
			}
		}
		return result;
	}
}
