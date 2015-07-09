package com.odcgroup.page.model.util;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ENTITY;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.model.Widget;

/**
 * Utility methods for the CDM domain.
 * 
 * @author Gary Hayes
 */
public class CdmUtils {
	
	/** valid CDM domain names. */
	private static final String[] CDM_DOMAIN_NAME = {"CDMAPP"};	

	/**
	 * Returns true if the domain is CDM. The String should be an MdfName.
	 * 
	 * i.e. CDM:ClientRelationship#name
	 * 
	 * @param attribute The Widget of Type Attribute
	 * @return boolean True if the domain is CDM
	 */
	public static boolean isCdmDomain(Widget attribute) {
		Widget w = attribute;
		while (w != null) {
			String ctx = w.getPropertyValue(DOMAIN_ENTITY);
			if (! StringUtils.isEmpty(ctx)) {
				MdfName qName = MdfNameFactory.createMdfName(ctx);
				if (isValidCdmDomain(qName)) {
					return true;
				}
			}
			
			w = w.getParent();
		}
		
		return false;
	}
	
	/**
	 * returns true if the domain is CDM specific
	 * 
	 * @param qName qualified name
	 * @return boolean true if the domain is cdm specific
	 */
	public static boolean isValidCdmDomain(MdfName qName) {
		for (String dom : CDM_DOMAIN_NAME) {
			if (dom.equals(qName.getDomain())){
				return true;
			}
		}
		return false;
	}
}
