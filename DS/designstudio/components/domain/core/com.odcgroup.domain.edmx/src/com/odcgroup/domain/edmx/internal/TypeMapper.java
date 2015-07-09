package com.odcgroup.domain.edmx.internal;

import org.odata4j.edm.EdmSimpleType;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * Helper for map the type between Odata and MDF
 */
public class TypeMapper {
	static public MdfPrimitive toMdfPrimitive(EdmSimpleType<?> edmType,String businessType){
		if (edmType.equals(EdmSimpleType.STRING)){
			if ("D".equals(businessType) || "RELTIME".equals(businessType) ){
				return PrimitivesDomain.DATE_TIME;
			}

			if ("AMT".equals(businessType)){
				return PrimitivesDomain.DOUBLE_OBJ;
			}
				
			return PrimitivesDomain.STRING;
		} else {
			return toMdfPrimitive(edmType);
		}
	}
	static public MdfPrimitive toMdfPrimitive(EdmSimpleType<?> edmType){
		if (edmType.equals(EdmSimpleType.INT64)) {
			return PrimitivesDomain.LONG_OBJ;
		} else if (edmType.equals(EdmSimpleType.INT32)) {
			return PrimitivesDomain.INTEGER_OBJ;
		} else if (edmType.equals(EdmSimpleType.INT16)) {
			return PrimitivesDomain.SHORT_OBJ;
		} else if (edmType.equals(EdmSimpleType.DECIMAL)) {
			return PrimitivesDomain.DECIMAL;
		} else if (edmType.equals(EdmSimpleType.DOUBLE)) {
			return PrimitivesDomain.DOUBLE_OBJ;
		} else if (edmType.equals(EdmSimpleType.BOOLEAN)) {
			return PrimitivesDomain.BOOLEAN_OBJ;
		} else if (edmType.equals(EdmSimpleType.DATETIME)) {
			return PrimitivesDomain.DATE_TIME;
		} else if (edmType.equals(EdmSimpleType.DATETIMEOFFSET)) {
			return PrimitivesDomain.DATE_TIME;
		}
		// Default case -> text
		return PrimitivesDomain.STRING;		
	}
}
