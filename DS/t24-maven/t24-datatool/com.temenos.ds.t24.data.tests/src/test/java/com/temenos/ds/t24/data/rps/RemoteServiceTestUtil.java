package com.temenos.ds.t24.data.rps;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;

/**
 * @author yandenmatten
 */
public class RemoteServiceTestUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(RemoteServiceTestUtil.class);

	public static String alterField(GetResourceResponse record, String newValue) {
		return alterField(record, "ADDRESS", newValue);
	}
	
	public static String alterField(GetResourceResponse record, String field, String newValue) {
		Field fieldToReplace = findField(record.fields, field);
		Field newField = new GetResourceResponse.Field(
				fieldToReplace.name, 
				newValue, // Small change
				fieldToReplace.mv, 
				fieldToReplace.mvGroupName, 
				fieldToReplace.sv, 
				fieldToReplace.svGroupName);
		record.fields.remove(fieldToReplace);
		record.fields.add(newField);
		return fieldToReplace.value; // old value
	}
	
	public static String toggleFieldValue(GetResourceResponse record, String field, String value1, String value2) {
		Field fieldToReplace = findField(record.fields, field);
		String newValue;
		if (fieldToReplace.value.equals(value1)) {
			newValue = value2;
		} else {
			newValue = value1;
		}
		LOGGER.debug("toggleFieldValue: current value is " + fieldToReplace.value + ", next value is " + newValue);
		Field newField = new GetResourceResponse.Field(
				fieldToReplace.name, 
				newValue, // Small change
				fieldToReplace.mv, 
				fieldToReplace.mvGroupName, 
				fieldToReplace.sv, 
				fieldToReplace.svGroupName);
		record.fields.remove(fieldToReplace);
		record.fields.add(newField);
		return fieldToReplace.value; // old value
	}
	
	public static Field findField(List<Field> fields, String name) {
		for (Field field: fields) {
			if (name.equals(field.name)) {
				return field;
			}
		}
		throw new IllegalStateException("Field not found: " + name);
	}


}
