package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassBooleanCollectionAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassBooleanSingleAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassComplexCollectionAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassComplexSingleAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassIntCollectionAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassIntSingleAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassStringCollectionAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassStringSingleAttrCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLBooleanCollectionParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLBooleanSingleParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLComplexCollectionParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLComplexSingleParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLIntCollectionParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLIntSingleParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLOperationCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLStringCollectionParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLStringSingleParaCppDescriptor;
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;

public class UMLElementCppFactory {
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
	public static UMLClassAttrCppDescriptor 
	createUMLClassMember(AttributeDescriptor attr, 
											 int index, 
											 UMLClassCppDescriptor parent) {
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
					new UMLClassIntSingleAttrCppDescriptor(umlName, index, parent);
			}else if (umlTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLClassBooleanSingleAttrCppDescriptor(umlName, index, parent);
			}else if(umlTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLClassStringSingleAttrCppDescriptor(umlName, index, parent);
			}else {
				return
					new UMLClassComplexSingleAttrCppDescriptor(umlName, umlTypeName, index, parent);
			}
		} else {//collection member
			if(umlTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {
				return 
					new UMLClassIntCollectionAttrCppDescriptor(umlName, index, parent);
			}else if (umlTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLClassBooleanCollectionAttrCppDescriptor(umlName, index, parent);
			}else if(umlTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLClassStringCollectionAttrCppDescriptor(umlName, index, parent);
			}else {
				return
					new UMLClassComplexCollectionAttrCppDescriptor(umlName, umlTypeName, index, parent);
			}
		}		
	}	
	
	//create one concrete UMLParameter object for the given
	//parameter defined in the given UML operation
	public static UMLParaCppDescriptor
	createUMLParameter(ParamDescriptor paraDesc, 
										 UMLOperationCppDescriptor parent) {
		String paraName = paraDesc.getName();
		String paraTypeName = paraDesc.getTypeName();
		Direction direction = paraDesc.getDirection();
		
		//As UML model parser will create an attribute to describe
		//void, here null is returned.
		if(paraTypeName.equalsIgnoreCase(UML_VOID_TYPE))
			return null;
		
		if(paraDesc.getCardinality() == Cardinality.SINGLE) {//singleton parameter
			if(paraTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {				
				return 
					new UMLIntSingleParaCppDescriptor(paraName, direction, parent);
			}else if (paraTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLBooleanSingleParaCppDescriptor(
							paraName, direction, parent);
			}else if(paraTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLStringSingleParaCppDescriptor(paraName, direction, parent);
			}else {
				return
					new UMLComplexSingleParaCppDescriptor(paraName,
																					 direction,
																					 paraTypeName,																					 
																					 parent);
			}
		} else {//collection parameter
			if(paraTypeName.equalsIgnoreCase(UML_INTEGER_TYPE)) {				
				return 
					new UMLIntCollectionParaCppDescriptor(paraName, direction, parent);
			}else if (paraTypeName.equalsIgnoreCase(UML_BOOLEAN_TYPE)) {
				return
					new UMLBooleanCollectionParaCppDescriptor(
							paraName, direction, parent);
			}else if(paraTypeName.equalsIgnoreCase(UML_STRING_TYPE)){
				return
					new UMLStringCollectionParaCppDescriptor(paraName, direction, parent);
			}else {
				return
					new UMLComplexCollectionParaCppDescriptor(paraName,
																					 direction,
																					 paraTypeName,																					 
																					 parent);
			}
			
		}
	}
}
