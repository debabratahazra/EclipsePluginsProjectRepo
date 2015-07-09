package com.odcgroup.iris.rim.generation.mappers;

import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

/**
 * A <code>PatternCOSPropertiesWriter</code> instance knows how to persist a {@link PatternCOSProperties} object to the appropriate folder under the hothouse-models-gen project.
 *
 * @author Simon Hayes
 */
public class PatternCOSPropertiesWriter extends ModelsGenEdgeFileWriter<PatternCOSProperties> {
	public PatternCOSPropertiesWriter(SimplerEclipseResourceFileSystemAccess2 p_hhouseModelsFSA) {
	    super(p_hhouseModelsFSA, "data/COS");
	}
}
