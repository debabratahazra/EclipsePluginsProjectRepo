package com.odcgroup.service.gen.t24.internal.categories.dotnet.api;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassBooleanCollectionAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassBooleanSingleAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassComplexCollectionAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassComplexSingleAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassIntCollectionAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassIntSingleAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassStringCollectionAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassStringSingleAttrDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLBooleanCollectionParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLBooleanSingleParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLComplexCollectionParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLComplexSingleParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLIntCollectionParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLIntSingleParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLOperationDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLStringCollectionParaDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLStringSingleParaDotNetDescriptor;

public class UMLElementDotNetFactory {
	//UML integer primitive type
	private static final String UML_INTEGER_TYPE = "Integer";
	
	//UML boolean primitive type
	private static final String UML_BOOLEAN_TYPE = "Boolean";
	
	//UML string primitive type
	private static final String UML_STRING_TYPE = "String";
	
	//UML void type
	private static final String UML_VOID_TYPE = "void";
	
	//create concrete UMLClassMember object for the given
	//UML class member	
	public static UMLClassAttrDotNetDescriptor 
	createUMLClassMember(AttributeDescriptor attr, 
											 int index, 
											 UMLClassDotNetDescriptor parent) {
		String umlName = attr.getName();
		String umlTypeName = attr.getTypeName();
		Cardinality cardinality = attr.getCardinality();
		
		//As UML model parser will create an attribute to describe
		//void, here null is returned.
		if(umlTypeName.equalsIgnoreCase(UML_VOID_TYPE))
			return null;
		
		if(cardinality == Cardinality.SINGLE) {//singleton member
			if(umlTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {				
				return 
					new UMLClassIntSingleAttrDotNetDescriptor(umlName, index, parent);
			}else if (umlTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLClassBooleanSingleAttrDotNetDescriptor(umlName, index, parent);
			}else if(umlTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLClassStringSingleAttrDotNetDescriptor(umlName, index, parent);
			}else {
				return
					new UMLClassComplexSingleAttrDotNetDescriptor(umlName, umlTypeName, index, parent);
			}
		} else {//collection member
			if(umlTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {
				return 
					new UMLClassIntCollectionAttrDotNetDescriptor(umlName, index, parent);
			}else if (umlTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLClassBooleanCollectionAttrDotNetDescriptor(umlName, index, parent);
			}else if(umlTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLClassStringCollectionAttrDotNetDescriptor(umlName, index, parent);
			}else {
				return
					new UMLClassComplexCollectionAttrDotNetDescriptor(umlName, umlTypeName, index, parent);
			}
		}		
	}	
	
	//create one concrete UMLParameter object for the given
	//parameter defined in the given UML operation
	public static UMLParaDotNetDescriptor
	createUMLParameter(ParamDescriptor paraDesc, 
										 UMLOperationDotNetDescriptor operationDotNetDescriptor) {
		String paraName = paraDesc.getName();
		String paraTypeName = paraDesc.getTypeName();
		Direction direction = paraDesc.getDirection();
		boolean isMandatory = paraDesc.isMandatory();
		
		//As UML model parser will create an attribute to describe
		//void, here null is returned.
		if(paraTypeName.equalsIgnoreCase(UML_VOID_TYPE))
			return null;
		
		if(paraDesc.getCardinality() == Cardinality.SINGLE) {//singleton parameter
			if(paraTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {				
				return 
					new UMLIntSingleParaDotNetDescriptor(
							paraName, direction, isMandatory, operationDotNetDescriptor);
			}else if (paraTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLBooleanSingleParaDotNetDescriptor(
							paraName, direction, isMandatory, operationDotNetDescriptor);
			}else if(paraTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLStringSingleParaDotNetDescriptor(
							paraName, direction, isMandatory, operationDotNetDescriptor);
			}else {
				return
					new UMLComplexSingleParaDotNetDescriptor(
							paraName, direction, isMandatory, paraTypeName, operationDotNetDescriptor);
			}
		} else {//collection parameter
			if(paraTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {				
				return 
					new UMLIntCollectionParaDotNetDescriptor(
							paraName, direction, isMandatory, operationDotNetDescriptor);
			}else if (paraTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLBooleanCollectionParaDotNetDescriptor(
							paraName, direction, isMandatory, operationDotNetDescriptor);
			}else if(paraTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLStringCollectionParaDotNetDescriptor(
							paraName, direction, isMandatory, operationDotNetDescriptor);
			}else {
				return
					new UMLComplexCollectionParaDotNetDescriptor(
							paraName, direction, isMandatory, paraTypeName, operationDotNetDescriptor);
			}
			
		}
	}
}
