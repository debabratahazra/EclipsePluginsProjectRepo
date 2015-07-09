package com.odcgroup.iris.generator;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * @author agoulding
 * Helper class that generates an EMEntity from Version and it's underlying application
 */
public class IRISVersionMapper {
	
	/**
	 * Main entry point, this routine creates an entity from a Version and the underlying application.
	 * @param version In parameter
	 * @param application In parameter. The application that the version is based on.
	 * @return
	 */
	public EMEntity getEntity(Version version, Application application, FieldTypeChecker fieldTypeChecker) {
	    if (version.getT24Name() == null) {
	    	// because version.getForApplication() COULD be an unresolved EMF Proxy, and there was a subtle DS bug (DS-7137), this is safer:  
	        throw new IllegalArgumentException("version.getT24Name() is null for: " + version.eResource());
	    }
	    int pos = version.getT24Name().indexOf(",");
		Resource resource = new Resource(RESOURCE_TYPE.version, version.getT24Name(), pos > 0 ?version.getT24Name().substring(0, pos):version.getT24Name());
		return getEntity(version, application, fieldTypeChecker, resource);
	}
	
	/**
	 * This method will 
	 * @param version
	 * @param application
	 * @param resource
	 * @return
	 */
	private EMEntity getEntity(Version version, Application application, FieldTypeChecker fieldTypeChecker, Resource resource) {
		if (version.getFields().size() == 0 && version.getAssociatedVersions().size() == 0) {
			// If there are no fields in the version or the associated versions, display all fields from the underlying application
			for (AppField appField : application.getFields()) {
				resource.addField(appField);
			}
		} else {
			// The version has fields or associated versions
			resource.addField(application.getIdField()); // Always start with the ID field
			addFields(resource, version.getFields(), application);
			
			// Add fields from associated versions
			for (Version associatedVersion : version.getAssociatedVersions()) {
				addFields(resource, associatedVersion.getFields(), application);
			}
		}
		return resource.toEMEntity(fieldTypeChecker);
	}
	
	
	/**
	 * Adds fields to the resource, based on the list of version fields provided.
	 * Fields are not duplicated, so subsequent attempts to add a field are ignored.
	 * @param resource In/out parameter
	 * @param fields In parameter
	 * @param application In parameter. The application that the version is based on.
	 */
	private void addFields(Resource resource, EList<Field> fields, Application application) {
		for (Field field : fields) {
            if (field.getName().equals("*")) {
                continue;   // Skip these fields, they are just labels
            }
            if (field.getColumn() == null) {
                continue;   // Skip fields with no column. These are likely AUTOM.NEW.CONTENT fields and should not be displayed.
            }
			String t24name = field.getName();
			t24name = t24name.replace("_", ".");
			t24name = t24name.replaceAll("-\\d\\.?\\d?\\Z", "");	// Strip off trailing '-1' from the version field name so it matches the application field name
			AppField appField = application.getFieldByT24Name(t24name);
			if (appField == null) {
				continue;	// Skip fields that don't map to underlying application fields (like '*' fields)
			}
			String selectionField = t24name;			// Always the same as the t24name for versions
			String joinedTo = appField.getJoinedTo();
			String propertyGroup = appField.getPropertyGroup();
			String mvGroup = appField.getMvGroup();
			String svGroup = appField.getSvGroup();
			boolean primaryKey = false;					// Always false, we've already added the primary key
			boolean mandatory = false;					// Always false, not required at selection
			String valueType = appField.getValueType();
			boolean selectionOnly = false;				// Always false, these are proper fields, can always be displayed
			String businessType = appField.getBusinessType();
			
			resource.addField(t24name, selectionField, joinedTo, propertyGroup, mvGroup, svGroup, primaryKey,
					mandatory, valueType, selectionOnly, appField.isLanguageField(), businessType);
		}
	}
	
}
