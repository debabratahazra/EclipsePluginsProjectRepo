package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.iris.generator.utils.IRISMetadataGeneratorCommon;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;
import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * Class representing a resource, which is 'a thing with fields and links to other resources'.
 * It can also be thought of as a 'flat' representation of an application, version or enquiry, all treated the same.
 * The idea behind this class is to be simpler to construct and manipulate than an EMEntity.
 * Then the entity can be generated from the resource, hopefully it is less work overall.
 * Enquiries in particular need some processing to turn them into EMEntity objects, using Resource makes this easier.
 *
 * @author agoulding
 *
 */
public class Resource {

	public enum RESOURCE_TYPE{

		unknown(""),
		composite("cos"),
		version("ver"),
		enquiry("enq"),
		menu("mnu"),
		enqlist("enqlist"),
		dynamic("dyn"); // for Dynamic DrillDown
		
		String prefix = "";
		
		RESOURCE_TYPE(String s){
			prefix = s;
		}
		
		public String toString(){
			return prefix;
		}
	}
	
	private final RESOURCE_TYPE type;
	private final String t24name;
	private final String underlyingApplicationName;   // Enquiries and versions have underlying application names
	private final List<ResourceField> fields = new ArrayList<ResourceField>();
	
	public Resource(RESOURCE_TYPE resourceType, String t24name, String underlyingApplicationName) {
		this.type = resourceType;
		this.t24name = t24name;
		this.underlyingApplicationName = underlyingApplicationName;
	}
	
	private void addField(ResourceField newField) {
		if (fields.contains(newField)) {
			// Already there, we need to merge certain values.
			// If the field is marked as selectionOnly, but a resultField with same name, we can change it to selectable
			ResourceField existingField = this.getFieldByIrisName(newField.getIrisName());
			if (!newField.getSelectionField().isEmpty()) {
				existingField.setSelectionField(newField.getSelectionField());
			}
			// If the data type of the new field differs from the existing one, set it to String to be the most open type possible.
			if (!newField.getValueType().equals(existingField.getValueType())) {
				existingField.setValueType(GeneratorConstants.T24_TYPE_STRING);
			}
			// If the new field is the primary key, mark the existing field as the primary key
			if (!existingField.isPrimaryKey() && newField.isPrimaryKey()) {
				existingField.setPrimaryKey(true);
			}
		} else {
			// Not there yet, so add it.
			fields.add(newField);
		}
	}
	
	public void addField(String t24name, String selectionField, String joinedTo, String propertyGroup, String mvGroup, String svGroup, boolean primaryKey, boolean mandatory, String valueType, boolean selectionOnly, boolean isLanguageTypeField, String businessType) {
		ResourceField field = new ResourceField(t24name, selectionField, joinedTo, propertyGroup, mvGroup, svGroup, primaryKey, mandatory, valueType, selectionOnly, isLanguageTypeField, businessType);
		addField(field);
	}
	
	public void addField(AppField appField) {
		if (appField != null) {
			ResourceField field = new ResourceField(appField);
			addField(field);
		}
	}
	
	public boolean hasPrimaryKey() {
		for (ResourceField field : fields) {
			if (field.isPrimaryKey()) {
				return true;
			}
		}
		return false;
	}
	
	public ResourceField getFieldByT24Name(String t24name) {
		for (ResourceField field : fields) {
			if (field.getT24name().equals(t24name)) {
				return field;
			}
		}
		return null;
	}
	
    public ResourceField getFieldByIrisName(String irisName) {
        for (ResourceField field : fields) {
            if (field.getIrisName().equals(irisName)) {
                return field;
            }
        }
        return null;
    }
    
	public List<ResourceField> getFields() {
		return this.fields;
	}
	
	@Override
	public String toString() {
		return t24name + ": " + fields.toString();
	}
	
	/**
	 * This is the main method of this class.
	 * It's easy to generate a Resource object, and then the Resource can be used to generate the EMEntity
	 * @return
	 */
	public EMEntity toEMEntity(FieldTypeChecker fieldTypeChecker) {
		EMEntity entity = null;
		/*
		 * If this is an enqlist, use the application name as it could be sometimes "-LIST" and sometimes not.
		 */
		if (this.type == RESOURCE_TYPE.enqlist){
			entity = new EMEntity(type + EMUtils.camelCaseName(this.underlyingApplicationName));
		}else{
			entity = new EMEntity(type + EMUtils.camelCaseName(t24name));
		}
		entity.setT24Name(t24name);
		for (ResourceField field : fields) {
			List<EMProperty> properties = entity.getProperties();
			// Create the property
			EMProperty property = new EMProperty(field.getIrisName());
			property.setT24Name(field.getT24name());
			if (field.isMandatory()) {
				property.addVocabularyTerm(new EMTerm(EMTermType.REQ_TERM, "true"));
			}
			if (field.isPrimaryKey()) {
				property.addVocabularyTerm(new EMTerm(EMTermType.ID_TERM, "true"));
			}
			
			// Value types are unreliable - only add to the model if it is flagged as safe in the fieldTypeChecker
            if (!field.getValueType().isEmpty() && fieldTypeChecker.isTypeSafe(this.underlyingApplicationName, field.getT24name())) {
			    property.addVocabularyTerm(new EMTerm(EMTermType.TYPE_TERM, field.getValueType()));
			}
            
            if (!field.getBusinessType().isEmpty()) {
            	property.addVocabularyTerm(new EMTerm(EMTermType.BUSINESS_TYPE_TERM, field.getBusinessType()));
            }
            
			// Handle the selection field flags.
			if (field.isSelectionOnly()) {
				property.addVocabularyTerm(new EMTerm(EMTermType.FIELD_RESTRICTION_TERM, "filterOnly"));
				property.setSelectionInfo("selectionOnly: true");
			} else if (field.getSelectionField().isEmpty()) {
				property.addVocabularyTerm(new EMTerm(EMTermType.FIELD_RESTRICTION_TERM, "displayOnly"));
				property.setSelectionInfo("selectable: false");
			} else if (field.getSelectionField().equals(field.getT24name())){
				// Doesn't need FIELD_RESTRICTION because is both displayable and filterable.
				property.setSelectionInfo("");  // Display nothing, since the values are the same
			} else {
				// Doesn't need FIELD_RESTRICTION because is both displayable and filterable.
				property.setSelectionInfo("selectionField: " + field.getSelectionField());
			} 
			
			// handle the 'joinedTo' field
			if (!field.getJoinedTo().isEmpty()) {
				property.setJoinedto("joinedTo: " + field.getJoinedTo());
			}
			
			// handle the 'propertyGroup' field
			if (!field.getPropertyGroup().isEmpty()) {
				property.setPropertyGroup("propertyGroup: " + field.getPropertyGroup());
			}
			
			// Now add the property to the right part of the entity, may need to create parent properties
			String mvGroupName = field.getMvGroup();
			if (!mvGroupName.isEmpty()) {
				String mvgn = EMUtils.camelCaseName(mvGroupName) + GeneratorConstants.MVGROUP_SUFFIX;
				EMProperty mvGroup = EMUtils.getPropertyByName(properties, mvgn);
				if (mvGroup == null) {
					mvGroup = new EMProperty(mvgn);
					mvGroup.addVocabularyTerm(new EMTerm(EMTermType.LIST_TERM, "true"));
					if (RESOURCE_TYPE.version.equals(type)) {
						IRISMetadataGeneratorCommon.addOriginalMvSvPropertiesInGroup(mvGroup);
					}
					properties.add(mvGroup);
					properties = mvGroup.getSubProperties();
				} else {
					properties = mvGroup.getSubProperties();
				}
				if (field.isLanguageField() && field.getSvGroup().isEmpty()) {
					mvGroup.addVocabularyTerm(new EMTerm(EMTermType.TERM_LANG_TYPE,"true"));
					EMProperty languageCodeProperty = new EMProperty("LanguageCode");
					properties.add(languageCodeProperty);
				}
			}
			String svGroupName = field.getSvGroup();
			if (!svGroupName.isEmpty()) {
				String svgn = EMUtils.camelCaseName(svGroupName) + GeneratorConstants.SVGROUP_SUFFIX;
				EMProperty svGroup = EMUtils.getPropertyByName(properties, svgn);
				if (svGroup == null) {
					svGroup = new EMProperty(svgn);
					svGroup.addVocabularyTerm(new EMTerm(EMTermType.LIST_TERM, "true"));
					if (RESOURCE_TYPE.version.equals(type)) {
						IRISMetadataGeneratorCommon.addOriginalMvSvPropertiesInGroup(svGroup);
					}
					properties.add(svGroup);
					properties = svGroup.getSubProperties();
				} else {
					properties = svGroup.getSubProperties();
				}
				if (field.isLanguageField()) {
					svGroup.addVocabularyTerm(new EMTerm(EMTermType.TERM_LANG_TYPE,"true"));
					EMProperty languageCodeProperty = new EMProperty("LanguageCode");
					properties.add(languageCodeProperty);
				}			
			}
			properties.add(property);
		}
		return entity;
	}
}
