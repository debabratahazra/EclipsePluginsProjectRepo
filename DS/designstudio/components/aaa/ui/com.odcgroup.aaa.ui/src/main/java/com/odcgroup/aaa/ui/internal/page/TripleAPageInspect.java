package com.odcgroup.aaa.ui.internal.page;



import java.util.List;

import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * @author ramapriyamn
 *
 */
public class TripleAPageInspect {
	
	private static final String AAA_FORMATS = "aaa.formats";
	private static final String HTTP_WWW_ODCGROUP_COM_MDF_AAA = "http://www.odcgroup.com/mdf/aaa";
	private static final String TRIPLE_A = "TripleA";

	/**
	 * @param attribute
	 * @return
	 */
	public static boolean hasTripleAPropertyForAttribute(MdfAttribute attribute){
		return processAnnotations(attribute.getAnnotations());
	}
	
	/**
	 * @param clazz
	 * @return
	 */
	public static boolean hasTripleAPropertyForClass(MdfClass clazz){
		return processAnnotations(clazz.getAnnotations());
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public static boolean hasTripleAPropertyForEntity(MdfEntity entity){
		return processAnnotations(entity.getAnnotations());
	}

	
	/**
	 * @param enumeratn
	 * @return
	 */
	public static boolean hasTripleAPropertyForEnumeration(MdfEnumeration enumeratn){
		return processAnnotations(enumeratn.getAnnotations());
	}
	
	/**
	 * @param enumVal
	 * @return
	 */
	public static boolean hasTripleAPropertyForEnumVal(MdfEnumValue enumVal){
		return processAnnotations(enumVal.getAnnotations());
	}

	/**
	 * @param mdfProp
	 * @return
	 */
	public static boolean hasTripleAPropertyForProperty(MdfProperty mdfProp){
		return processAnnotations(mdfProp.getAnnotations());
	}

	/**
	 * @param mdfAnnotations
	 * @return
	 */
	private static boolean processAnnotations(@SuppressWarnings("rawtypes") List mdfAnnotations) {
		for(Object anObj: mdfAnnotations){
			MdfAnnotation annotation = (MdfAnnotation)anObj;
			if (annotation.getName().equalsIgnoreCase(TRIPLE_A) && annotation.getNamespace().equalsIgnoreCase(HTTP_WWW_ODCGROUP_COM_MDF_AAA)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param mdfAnnotations
	 * @return
	 */
	public static boolean isFormatClass(Object cls) {
		if(cls instanceof MdfProperty){
			if(JavaAspect.getPackage(((MdfProperty)cls).getParentClass().getParentDomain()).contains(AAA_FORMATS)){
				return true;
			}
		}
		if(cls instanceof MdfClass ){
			if(JavaAspect.getPackage(((MdfClass)cls).getParentDomain()).contains(AAA_FORMATS)){
				return true;
			}
		}
		return false;
	}
	
}
