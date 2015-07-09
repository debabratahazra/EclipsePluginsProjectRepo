package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenType;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.DownloadType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.PWProcessType;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.ProcessingMode;
import com.odcgroup.t24.enquiry.enquiry.QuitSEEType;
import com.odcgroup.t24.enquiry.enquiry.RunType;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperation;
import com.odcgroup.t24.enquiry.enquiry.TabbedScreenType;
import com.odcgroup.t24.enquiry.enquiry.TotalOperation;
import com.odcgroup.t24.enquiry.enquiry.ValueConversion;
import com.odcgroup.t24.enquiry.enquiry.ViewType;
import com.odcgroup.t24.enquiry.util.EMEntity;

/**
 * @author agoulding
 * Helper class that generates an EMEntity from an Enquiry and it's underlying application
 */
public class IRISEnquiryMapper {
	
	/**
	 * Main entry point, this routine creates an entity from an Enquiry and the underlying application.
	 * @param enquiry
	 * @param mdfDomain
	 * @return
	 */
	public EMEntity getEntity(Enquiry enquiry, Application application, FieldTypeChecker fieldTypeChecker, RESOURCE_TYPE resourceType) {
	    String underlyingApplicationName = getUnderlyingApplicationName(enquiry);
		Resource resource = new Resource(resourceType, enquiry.getName(), underlyingApplicationName);
		addFieldsFromFixedSelections(resource, application, enquiry);
		addFieldsFromFields(resource, application, enquiry);
		if (isNofile(enquiry)) {
			addFieldsFromCustomSelections(resource, application, enquiry);	// For NOFILES we add only the custom selections
		} else {
			addFieldsFromUnderlyingApplication(resource, application, enquiry);  // For normal enquiries we add all underlying fields
		}
		addPrimaryKey(resource, enquiry);
		return resource.toEMEntity(fieldTypeChecker);
	}



	/**
	 * Returns a subset of the Enquiry.field Field objects which are defined as 'result' fields - ie data
	 * that need to be returned at runtime when the enquiry is executed.
	 * Fields are 'result' fields if <ul>
	 * <li>they are normally displayed in a column or header / footer section in browser, ie if they have a Position/Column</li>
	 * <li>they are needed to execute a drilldown.</li>
	 * <li>they are used in a BREAK CHANGE operation by another field</li>
	 * <ul>
	 * @param enquiry
	 * @return
	 */
	public List<Field> getResultFields(Enquiry enquiry) {
		List<Field> resultFields = new ArrayList<Field>(); // Don't want duplicates

		// Add the 'display' fields
		// these are fields that start with a number in COLUMN, and do not have NONE in DISPLAY.BREAK
		List<Field> fields = enquiry.getFields();
		for (Field field : fields) {
			FieldPosition position = field.getPosition();
			if (position == null) {
				continue;  // Display fields must have a position
			}
			if (position.getColumn() == null || position.getColumn().intValue() <= 0) {
				continue; // Display fields must have a column
			}
			// If the field is DISPLAY.BREAK NONE then skip it.
			// Complication is that when a field has DISPLAY.BREAK = someField then it is marked as BreakKind = NONE by the DS model
			// So we also have to check that getField == null. (DS-6793)
			BreakCondition breakCondition = field.getBreakCondition();
			if (breakCondition != null && breakCondition.getBreak() != null && breakCondition.getField() == null && breakCondition.getBreak().equals(BreakKind.NONE)) {
				continue; // Ignore display fields output on the 'NONE' break condition
			}
			resultFields.add(field);  // If the processing gets here, it's a display field
		}
		
		// Add fields that are used in breakOnChangeOperation, enables UIs to determine when to throw page break / group separator 
		for (Field field : fields) {
			Operation operation = field.getOperation();
			if (operation instanceof BreakOnChangeOperation) {
				String fieldname = ((BreakOnChangeOperation)operation).getField();
				Field bcField = getFieldByName(fields, fieldname);
				if (!resultFields.contains(bcField)) {
					resultFields.add(getFieldByName(fields, fieldname));
				}
			}
		}
		
		// Add all fields that are referenced by drilldowns, regardless of whether they have COLUMN or NONE
		// But not if they are already in the list
		List<DrillDown> drilldowns = enquiry.getDrillDowns();
		for (DrillDown drilldown : drilldowns) {
			// Add fields from drilldown types that can contain field names
			DrillDownType drilldownType = drilldown.getType();
			String possibleFieldName = "";
			if (drilldownType instanceof CompositeScreenType) {
				possibleFieldName = ((CompositeScreenType)drilldownType).getValue();
			} else if (drilldownType instanceof DownloadType) {
				possibleFieldName = ((DownloadType)drilldownType).getValue();
			} else if (drilldownType instanceof FromFieldType) {
				possibleFieldName = ((FromFieldType)drilldownType).getValue();
			} else if (drilldownType instanceof PWProcessType) {
				possibleFieldName = ((PWProcessType)drilldownType).getValue();
			} else if (drilldownType instanceof QuitSEEType) {
				possibleFieldName = ((QuitSEEType)drilldownType).getValue();
			} else if (drilldownType instanceof RunType) {
				possibleFieldName = ((RunType)drilldownType).getValue();
			} else if (drilldownType instanceof TabbedScreenType) {
				possibleFieldName = ((TabbedScreenType)drilldownType).getValue();
			} else if (drilldownType instanceof ViewType) {
				possibleFieldName = ((ViewType)drilldownType).getValue();
			}
			if (possibleFieldName != null && !possibleFieldName.isEmpty()) {
				resultFields = tryAddResultField(resultFields, fields, possibleFieldName);
			}
			
			/*
			 * On some drilldown, for an obscure reason, the DrildownType is null.
			 * Protect against it.
			 */
			if (drilldownType != null){
				resultFields = tryAddResultField(resultFields, fields, drilldownType.getProperty());
			}
			
			// Add fields from parameters
			Parameters drillParameters = drilldown.getParameters();
			if (drillParameters != null) {
				for (String value : drillParameters.getFieldName()) {
					resultFields = tryAddResultField(resultFields, fields, value);
				}
				for (String value : drillParameters.getVariable()) {
					resultFields = tryAddResultField(resultFields, fields, value);
				}
				if (drillParameters.getPwActivity() != null && !drillParameters.getPwActivity().isEmpty()) {
					resultFields = tryAddResultField(resultFields, fields, drillParameters.getPwActivity());
				}
			}

			// Add fields from selection criteria
			List<SelectionCriteria> selectionCriteria = drilldown.getCriteria();
			for (SelectionCriteria criterion : selectionCriteria) {
				resultFields = tryAddResultField(resultFields, fields, criterion.getField());
				List<String> values = criterion.getValues();
				for (String value : values) {
					resultFields = tryAddResultField(resultFields, fields, value);
				}
			}
		}
		return resultFields;
	}
	

	/**
	 * Returns the name of the field in the underlying application, or null if not available.
	 * Handles fields that refer to earlier fields and recursively looks them up.
	 * @param enquiry
	 * @param application
     * @param fieldname The name of the field in the enquiry
	 * @param isForMvSv For the purposes of determining field multiplicity we can ignore most conversions
	 * @param checkedFields A list of fields that have already been searched. This is to handle possible cyclic field definitions or fields referring to themselves.
	 * @return The name of the field in the base application, or null
	 */
	public String getBaseAppField(Enquiry enquiry, Application application, String fieldname, boolean isForMvSv, List<String> checkedFields) {
		String baseAppField = null;
		List<Field> fields = enquiry.getFields();
		Field field = getFieldByName(fields, fieldname);
		if (field != null) {
			if (field.getConversion().isEmpty() || (isForMvSv && !(containsValueConversion(field)))) {
				Operation operation = field.getOperation();
				if (operation instanceof ApplicationFieldNameOperation) {
					baseAppField = ((ApplicationFieldNameOperation)operation).getField();
				} else if (operation instanceof FieldNumberOperation) {
					int sysNumber = ((FieldNumberOperation)operation).getNumber();
					baseAppField = application.getT24Name(sysNumber);
				} else if (operation instanceof SelectionOperation) {
					baseAppField = ((SelectionOperation)operation).getField();
				} else if (operation instanceof FieldExtractOperation) {
					String previousField = ((FieldExtractOperation)operation).getField();
					if (checkedFields.contains(previousField)) {
						baseAppField = null;  // Cyclic field definition
					} else {
					    checkedFields.add(fieldname);
					    baseAppField = getBaseAppField(enquiry, application, previousField, isForMvSv, checkedFields);
					}
				} else if (isForMvSv && field.getSingleMulti().equals(ProcessingMode.MULTI)) {
					baseAppField = field.getName();
				}
			}
		}
		return baseAppField;
	}
	
	
	
	/**
	 * Any fixed selections that use !CURRENT variables need to be exposed as mandatory selection fields.
	 * @param resource In/out parameter
	 * @param application Input parameter
	 * @param enquiry Input parameter
	 */
	public void addFieldsFromFixedSelections(Resource resource, Application application, Enquiry enquiry) {
		List<Field> resultFields = getResultFields(enquiry);
		List<Selection> selectionFields = null;
		SelectionExpression selectionExpression = enquiry.getCustomSelection();
		if(selectionExpression != null) {
			selectionFields = selectionExpression.getSelection();			
		}
		List<FixedSelection> fixedSelections = enquiry.getFixedSelections();
		for (FixedSelection fixedSelection : fixedSelections) {
			List<String> values = fixedSelection.getValues();
			for (String value : values) {
				if (value.startsWith("!CURRENT.")) {
					String fieldname = value.substring(1);  // Drop the '!'
					// check if any result fields with this name exist, throw error if true
					if (getFieldByName(resultFields, fieldname) != null) {
						throw new RuntimeException("!CURRENT variable in FIXED.SELECTION duplicated in FIELD.NAME: " + fieldname); // TODO: which exception?
					}
					if (getSelectionFieldByName(selectionFields, fieldname) != null) {
						throw new RuntimeException("!CURRENT variable in FIXED.SELECTION duplicated in SELECTION.FLDS: " + fieldname); // TODO: which exception?
					}
					String selectionField = fieldname;
					String joinedTo = "";  // we don't support joinedTo on enquiries
					String propertyGroup = "";  // we don't support propertyGroup on enquiries
					boolean mandatory = true;  // Fixed selections must be mandatory.
					boolean primaryKey = false;  // Variables can never be the primary key
					boolean selectionOnly = true;  // These are always selection only
                    boolean isLanguageField = false; // Enquiry fields are never language fields as T24 should always return single most appropriate value
					String valueType = application.getValueType(selectionField);
					String mvGroup = application.getMvGroup(selectionField);
					String svGroup = application.getSvGroup(selectionField);
					String businessType = application.getBusinessType(selectionField);
					resource.addField(fieldname, selectionField, joinedTo, propertyGroup, mvGroup,
							svGroup, primaryKey, mandatory, valueType, selectionOnly, isLanguageField, businessType);
				}
			}
		}
	}



	/**
	 * For NOFILE enquiries, we must add the CustomSelections as selectionOnly fields
	 * @param resource In/out parameter
	 * @param application
	 * @param enquiry
	 * @return
	 */
	public void addFieldsFromCustomSelections(Resource resource, Application application, Enquiry enquiry) {
		SelectionExpression customSelection = enquiry.getCustomSelection();
		if(customSelection != null) {
			List<Selection> selectionFields = customSelection.getSelection();
			for (Selection selection : selectionFields) {
				String fieldname = selection.getField();
				String selectionField = fieldname;
				String joinedTo = "";  // we don't support joinedTo on enquiries
				String propertyGroup = "";  // we don't support propertyGroup on enquiries
				boolean mandatory=false;
				if(selection.getMandatory() !=null){
				  mandatory = selection.getMandatory().booleanValue();
				}
				boolean selectionOnly = true;  // NOFILE custom selection fields are virtual only, can't actually display them
				boolean primaryKey = false; // NOFILE selection fields can't be the primary key as can't be returned
				boolean isLanguageField = false; // Enquiry fields are never language fields as T24 should always return single most appropriate value
				String valueType = application.getValueType(selectionField);
				String mvGroup = application.getMvGroup(selectionField);
				String svGroup = application.getSvGroup(selectionField);
				String businessType = application.getBusinessType(selectionField);
				resource.addField(fieldname, selectionField, joinedTo, propertyGroup, mvGroup, svGroup,
						primaryKey, mandatory, valueType, selectionOnly, isLanguageField, businessType);
			}
		}
	}


	/**
	 * For normal enquiries, all fields from the underlying application should be added to the resource, since T24 allows any field in the underlying
	 * application to be used in filters whether or not custom selections are listed. (NOFILE enquiries are an exception since they don't have underlying fields.)
	 * Fields explicitly listed in custom selections will be added to T24 output by T24, so they need to be handled slightly differently to other underlying application fields.
	 * @param resource In/out parameter
	 * @param application
	 * @param enquiry
	 * @return
	 */
	public void addFieldsFromUnderlyingApplication(Resource resource, Application application, Enquiry enquiry) {
		List<Selection> selectionFields = null;
		SelectionExpression selectionExpression = enquiry.getCustomSelection();
		if (selectionExpression != null) {
			selectionFields = selectionExpression.getSelection();
		}
		for (AppField appField : application.getFields()) {
			String fieldname = appField.getT24name();
			String selectionField = fieldname;
			String joinedTo = "";  // we don't support joinedTo on enquiries
			String propertyGroup = "";  // we don't support propertyGroup on enquiries
			
			// Check if it's a custom selection - some properties differ
			Selection selection = null;
			if (selectionFields != null) {
				for (Selection selectionFieldToCheck : selectionFields) {
					if (selectionFieldToCheck.getField().equals(fieldname)) {
						selection = selectionFieldToCheck;
						break;
					}
				}
			}
			
			// Set mandatory nature of the field
			boolean mandatory = false;
			if (selection != null && selection.getMandatory() != null) {
				mandatory = selection.getMandatory().booleanValue();
			}
			
			// Set primary key nature of the field
			boolean primaryKey = false;
			if (selection != null && application.isPrimaryKey(selectionField) && !resource.hasPrimaryKey()) {
				primaryKey = true;
			}
			
			// Set the selectionOnly property
			boolean selectionOnly = true;
			if (selection != null) {
				selectionOnly = false;
			}
			
			boolean isLanguageField = false; // Enquiry fields are never language fields as T24 should always return single most appropriate value
			String valueType = appField.getValueType();
			String mvGroup = appField.getMvGroup();
			String svGroup = appField.getSvGroup();
			String businessType = appField.getBusinessType();
			resource.addField(fieldname, selectionField, joinedTo, propertyGroup, mvGroup, svGroup, 
					primaryKey, mandatory, valueType, selectionOnly, isLanguageField, businessType);
		}
	}

	
	/**
	 * Adds the result fields to the list
	 * @param enquiry
	 * @param resultFields
	 * @return
	 */
	public void addFieldsFromFields(Resource resource, Application application, Enquiry enquiry) {
		List<Field> resultFields = getResultFields(enquiry);
		for (Field field : resultFields) {
			String fieldname = field.getName();
			String selectionField = getBaseAppField(enquiry, application, fieldname, false, new ArrayList<String>());
			String joinedTo = "";  // we don't support joinedTo on enquiries
			String propertyGroup = "";  // we don't support propertyGroup on enquiries
			boolean mandatory = false;  // result fields are not generally selectable, so making them mandatory would make the enquiry impossible to run.
			boolean primaryKey = false;
			boolean isLanguageField = false; // Enquiry fields are never language fields as T24 should always return single most appropriate value
			if (application.isPrimaryKey(selectionField) && !resource.hasPrimaryKey()) {
				primaryKey = true;
			}
			boolean selectionOnly = false;  // result fields are not generally selectable.
			String valueType = application.getValueType(selectionField);
			// If the field is a total type field, then set the value type to NUMERIC
			if (valueType.isEmpty()) {
			    Operation operation = field.getOperation();
			    if (operation instanceof TotalOperation) {
					valueType = GeneratorConstants.T24_TYPE_NUMBER;
			    }
			}
			String businessType = application.getBusinessType(selectionField);
			// Work out the multivalue group the field belongs to. This is getting more and more complicated...
			String mvGroup = "";
			String svGroup = "";
            String mvSvGroupName = getBaseAppField(enquiry, application, fieldname, true, new ArrayList<String>());
            if (mvSvGroupName == null) {
            	if (field.getSingleMulti().equals(ProcessingMode.MULTI)) {
            		mvGroup = field.getName();
            	}
            } else {
                boolean isMvSvGroupNameAnAppField = (application.getFieldByT24Name(mvSvGroupName) != null);
                if (isMvSvGroupNameAnAppField) {
        			mvGroup = application.getMvGroup(mvSvGroupName);
        			svGroup = application.getSvGroup(mvSvGroupName);
                } else {
                	Field mvSvEnqField = getFieldByName(enquiry.getFields(), mvSvGroupName);
                	if (mvSvEnqField != null && mvSvEnqField.getSingleMulti().equals(ProcessingMode.MULTI)) {
                		mvGroup = mvSvGroupName;
                	}
    			}
            }
			resource.addField(fieldname, selectionField, joinedTo, propertyGroup, mvGroup, svGroup,
					primaryKey, mandatory, valueType, selectionOnly, isLanguageField, businessType);
		}
	}


	
	/**
	 * Adds a field called PrimaryKey if required
	 * @param properties
	 * @param enquiry
	 */
	public void addPrimaryKey(Resource resource, Enquiry enquiry) {
		// If there is no primary key field, we need to add one
		if (!resource.hasPrimaryKey()) {
			String fieldname = "PrimaryKey";
			String selectionField = "@ID";  // NB: do not use ID.F. Must use @ID as the runtime can't look up ID.F.
			if (isNofile(enquiry)) {
				selectionField = "";   // NOFILE enquiries can't be listed by @ID.
			}
			String joinedTo = "";  // primary key is not joined to anything else
			String propertyGroup = "";  // Primary key can not be propertyGroup
			boolean mandatory = false;
			boolean primaryKey = true;
			boolean selectionOnly = false;
            boolean isLanguageField = false; // Primary key is not a language field
			String valueType = "";
			String mvGroup = "";
			String svGroup = "";
			String businessType = "";
			resource.addField(fieldname, selectionField, joinedTo, propertyGroup, mvGroup, svGroup,
					primaryKey, mandatory, valueType, selectionOnly, isLanguageField, businessType);
		}
	}
	
	

	/**
	 * Checks if the given field is an enquiryField, then adds it to resultFields, if it's not there already. 
	 * @param resultFields. In/Out parameter.
	 * @param enquiryFields. In parameter, the list of fields in the ENQ.FIELD.NAME field.
	 * @param fieldname. The field to add, if not already there.
	 * @return
	 */
	private List<Field> tryAddResultField(List<Field> resultFields, List<Field> enquiryFields, String fieldname) {
		if (fieldname != null && !fieldname.isEmpty()) {
			Field field = getFieldByName(enquiryFields, fieldname);
			if (field != null) {
				if (getFieldByName(resultFields, fieldname) == null) {
					resultFields.add(field);
				}
			}
		}
		return resultFields;
	}

	/**
	 * Returns the named field from the given list.
	 * @param fields
	 * @param fieldname
	 * @return
	 */
	private Field getFieldByName(List<Field> fields, String fieldname) {
		if (fields == null) {
			return null;
		}
		for (Field field : fields) {
			if (field.getName().equals(fieldname)) {
				return field;
			}
		}
		return null;
	}


	/**
	 * Returns the named CustomSelection from the given list.
	 * @param selections
	 * @param fieldname
	 * @return
	 */
	private Object getSelectionFieldByName(List<Selection> selections, String fieldname) {
		if (selections == null) {
			return null;
		}
		for (Selection selection : selections) {
			if (selection.getField().equals(fieldname)) {
				return selection;
			}
		}
		return null;
	}
	
	

	/**
	 * Returns true if the enquiry is a NOFILE enquiry, false otherwise.
	 * @param enquiry
	 * @return
	 */
	private boolean isNofile(Enquiry enquiry) {
        String underlyingApplicationName = getUnderlyingApplicationName(enquiry);
        if (underlyingApplicationName.startsWith(GeneratorConstants.ENQ_NO_FILE + ".")) {
            return true;
        } else {
            return false;
        }
	}

	/**
	 * Returns the T24 name of the application underlying the enquiry
	 * @param enquiry
	 * @return
	 */
	private String getUnderlyingApplicationName(Enquiry enquiry) {
        String underlyingApplicationName = enquiry.getFileName().split("\\$")[0];
        underlyingApplicationName = underlyingApplicationName.split(":")[2];
        underlyingApplicationName = underlyingApplicationName.split("#")[0];
        return underlyingApplicationName;
	}

	/**
	 * Method checks whether field contains a Value conversion in its conversion list.
	 * @param field
	 * @return
	 */
    private boolean containsValueConversion(Field field){
    	if(field.getConversion() != null && !field.getConversion().isEmpty()){
    		for(Conversion conversion : field.getConversion()){
    			if(conversion instanceof ValueConversion){
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }

}
