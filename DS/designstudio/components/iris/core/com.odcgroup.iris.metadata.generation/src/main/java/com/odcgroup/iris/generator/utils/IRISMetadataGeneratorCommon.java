package com.odcgroup.iris.generator.utils;

import com.odcgroup.t24.enquiry.util.EMProperty;

/**
 * This class will provide common functionalities required by Metadata cartridge
 *
 * @author sjunejo
 *
 */
public class IRISMetadataGeneratorCommon {

	/**
	 * This method will add two additional properties which will be used 
	 * at runtime. This be used for following reason;
	 * <p>
	 * We represent each Mv/Sv group as Complex Type. To enable client
	 * Delete or Add an individual Mv/Sv group we need to keep track of
	 * their original position so that T24 can perform as expected
	 * Adding two additional properties which will be used at runtime
	 * </p>
	 * 
	 * @param property EMProperty to be amended
	 */
	public static void addOriginalMvSvPropertiesInGroup(EMProperty property) {
		if (property != null && property.getName() != null && 
				(property.getName().endsWith(GeneratorConstants.MVGROUP_SUFFIX) || 
				property.getName().endsWith(GeneratorConstants.SVGROUP_SUFFIX) )) {
			property.addSubProperty(new EMProperty(GeneratorConstants.VALUEPOSITION_PROP_NAME));
			property.addSubProperty(new EMProperty(GeneratorConstants.SUBVALUEPOSITION_PROP_NAME));
		}
	}
	
}
