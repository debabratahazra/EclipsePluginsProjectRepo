package com.odcgroup.visualrules.integration.datasync;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.generation.util.MdfGenerationUtil;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;

import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.JavaBooleanType;
import de.visualrules.integration.model.JavaBooleanTypeSettings;
import de.visualrules.integration.model.JavaFloatType;
import de.visualrules.integration.model.JavaFloatTypeSettings;
import de.visualrules.integration.model.JavaIntegerType;
import de.visualrules.integration.model.JavaIntegerTypeSettings;
import de.visualrules.integration.model.TypedInfo;

public class SyncHelper {

	public static String getFQCN(MdfEntity entity) {
		return JavaAspect.getPackage(entity.getParentDomain()) + "." + entity.getName();
	}
	
	public static boolean isMany(MdfMultiplicity multiplicity) {
		return MdfMultiplicity.ONE_LITERAL.equals(multiplicity);
	}

	public static MdfMultiplicity getMultiplicity(MdfDatasetProperty p) {
		return MdfMultiplicity.get(p.getMultiplicity());
	}

	public static boolean isRefByValue(MdfAssociation association) {
		return association.getContainment()==MdfContainment.BY_VALUE;
	}

	public static String mapType(MdfProperty p) {
		MdfEntity type = p.getType();
		return mapType(type);
	}

	public static String mapType(MdfDatasetProperty p) {
		MdfEntity type = p.getType();
		return mapType(type);
	}

	public static String mapType(MdfEnumeration enumeration) {
		MdfEntity type = enumeration.getType();
		return mapType(type);
	}

	private static String mapType(MdfEntity type) {
		if (type == null)
			return "Any";
		
		// if it's an enumeration, return the mapped type of the enumeration
		if(type instanceof MdfEnumeration) {
			MdfEnumeration enumeration = (MdfEnumeration) type;
			return mapType(enumeration.getType());
		}

		// DS-1802
		// if it's a business type, return the underlying primitive type
		if(type instanceof MdfBusinessType) {
			MdfBusinessType businessType = (MdfBusinessType) type;
			return mapType(businessType.getType());
		}

		if(type instanceof MdfPrimitive) {
			String primType = type.toString();
			if(PrimitivesDomain.BOOLEAN.toString().equals(primType)) 		return "Boolean";
			if(PrimitivesDomain.BOOLEAN_OBJ.toString().equals(primType))	return "Boolean";
			if(PrimitivesDomain.STRING.toString().equals(primType)) 		return "String";
			if(PrimitivesDomain.CHAR.toString().equals(primType)) 			return "String";
			if(PrimitivesDomain.URI.toString().equals(primType)) 			return "String";
			if(PrimitivesDomain.DATE.toString().equals(primType)) 			return "Date";
			if(PrimitivesDomain.DATE_TIME.toString().equals(primType)) 	return "Date";
			if(PrimitivesDomain.INTEGER.toString().equals(primType)) 		return "Integer";
			if(PrimitivesDomain.INTEGER_OBJ.toString().equals(primType)) 	return "Integer";
			if(PrimitivesDomain.LONG.toString().equals(primType)) 			return "Integer";
			if(PrimitivesDomain.LONG_OBJ.toString().equals(primType)) 		return "Integer";
			if(PrimitivesDomain.SHORT.toString().equals(primType)) 		return "Integer";
			if(PrimitivesDomain.SHORT_OBJ.toString().equals(primType)) 	return "Integer";
			if(PrimitivesDomain.BYTE.toString().equals(primType)) 			return "Integer";
			if(PrimitivesDomain.FLOAT.toString().equals(primType)) 		return "Float";
			if(PrimitivesDomain.FLOAT_OBJ.toString().equals(primType)) 	return "Float";
			if(PrimitivesDomain.DOUBLE.toString().equals(primType)) 		return "Float";
			if(PrimitivesDomain.DOUBLE_OBJ.toString().equals(primType)) 	return "Float";
			
			// map all other types to "Any"
			return "Any";
		} else {
			// if it is no primitive type, simply return its name
			return type.getName();
		}
	}
	
	static public String makeVRcompliant(String s) {
		if(s.equals("TRUE")) 		return "_TRUE";
		if(s.equals("FALSE")) 		return "_FALSE";
		if(s.equals("true")) 		return "_true";
		if(s.equals("false")) 		return "_false";
		if(s.equals("NEW")) 		return "_NEW";
		if(s.equals("timestamp")) 	return "_timestamp";
		if(s.equals("time")) 		return "_time";
		if(s.equals("date")) 		return "_date";
		
		return (s.length()>60)?s.substring(0, 59) : s;
	}


	public static TypedInfo getTypeInfo(MdfProperty p) {
		return getTypeInfo(MdfGenerationUtil.getTypeName(p));
	}

	public static TypedInfo getTypeInfo(MdfDatasetProperty p) {
		return getTypeInfo(MdfGenerationUtil.getTypeName(p));
	}
	
	private static TypedInfo getTypeInfo(String javaType) {
        TypedInfo typeInfo = IntegrationFactory.eINSTANCE.createTypedInfo();
		
		if("int".equals(javaType)) {
		       JavaIntegerTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaIntegerTypeSettings();
		       typeSettings.setIntegerType(JavaIntegerType.INT_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("java.lang.Integer".equals(javaType)) {
		       JavaIntegerTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaIntegerTypeSettings();
		       typeSettings.setIntegerType(JavaIntegerType.JAVA_LANG_INTEGER_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("long".equals(javaType)) {
		       JavaIntegerTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaIntegerTypeSettings();
		       typeSettings.setIntegerType(JavaIntegerType.LONG_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("java.lang.Long".equals(javaType)) {
		       JavaIntegerTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaIntegerTypeSettings();
		       typeSettings.setIntegerType(JavaIntegerType.JAVA_LANG_LONG_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("double".equals(javaType)) {
		       JavaFloatTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaFloatTypeSettings();
		       typeSettings.setFloatType(JavaFloatType.DOUBLE_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("java.lang.Double".equals(javaType)) {
		       JavaFloatTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaFloatTypeSettings();
		       typeSettings.setFloatType(JavaFloatType.JAVA_LANG_DOUBLE_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("float".equals(javaType)) {
		       JavaFloatTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaFloatTypeSettings();
		       typeSettings.setFloatType(JavaFloatType.FLOAT_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("java.lang.Float".equals(javaType)) {
		       JavaFloatTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaFloatTypeSettings();
		       typeSettings.setFloatType(JavaFloatType.JAVA_LANG_FLOAT_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("java.lang.Boolean".equals(javaType)) {
		       JavaBooleanTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaBooleanTypeSettings();
		       typeSettings.setBooleanType(JavaBooleanType.JAVA_LANG_BOOLEAN_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		if("boolean".equals(javaType)) {
		       JavaBooleanTypeSettings typeSettings = IntegrationFactory.eINSTANCE.createJavaBooleanTypeSettings();
		       typeSettings.setBooleanType(JavaBooleanType.BOOLEAN_LITERAL);
		       typeInfo.getLanguageTypeSettings().add(typeSettings);
		}

		return typeInfo;
	}
}
