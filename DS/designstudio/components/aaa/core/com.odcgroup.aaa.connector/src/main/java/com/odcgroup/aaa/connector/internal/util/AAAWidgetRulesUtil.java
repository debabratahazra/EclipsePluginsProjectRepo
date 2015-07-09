package com.odcgroup.aaa.connector.internal.util;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.mdfmml.DataType2Primitives;
import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * DS-4176 
 *  Display Long Code / Long Synonym / Meta-Dict size data to upload
 *  Utility class to allow testing.
 * 
 * @author gmc
 */
public class AAAWidgetRulesUtil {

	 private static final String TRIPLEA_NAMESPACE_BASE = "http://www.odcgroup.com/mdf/";
	 private static final String TRIPLEA_NAMESPACE = TRIPLEA_NAMESPACE_BASE+"aaa";
	 private static final String TRIPLEA_NAMESPACE_CONSTRAINT = TRIPLEA_NAMESPACE_BASE+"ext/constraints";
     private static final String TRIPLE_A_ANNOTATION_NAME = "TripleA";
     private static final String TRIPLEA_CONSTRAINTS_NAME = "Constraints";
     
     private final DataType2Primitives dataType2Primitives;
     
	public static AAAWidgetRulesUtil createWidgetRulesUtil() {
		return new AAAWidgetRulesUtil(new DataType2Primitives());
	}
     
	public AAAWidgetRulesUtil(DataType2Primitives dataType2Primitives) {
		this.dataType2Primitives = dataType2Primitives;
	}
     
	public void checkLongCodeForNameAndCodeBusinessTypes(MdfDomain businessTypesDomain, DictAttribute dictAttribute, MdfPropertyImpl aaaAttribute) {
		MdfBusinessType businessType = dataType2Primitives.getBusinessType(dictAttribute.getDatatype(), businessTypesDomain);
		String businessTypeName = getBusinessTypeValue(businessType);
		if (businessTypeName != null && (businessTypeName.equals("code_t") || businessTypeName.equals("name_t"))) {
			int businessTypeMaxLength = getDefaultLength(businessType);
			int maxDbLen = dictAttribute.getMaxDbLen();
			int displayLength = dictAttribute.getDefaultDisplayLen();
			if(maxDbLen > 0 && displayLength <= maxDbLen && maxDbLen < businessTypeMaxLength) {
				addAnnotationPropertyToTripleANameSpace(aaaAttribute, "AttrMaxDbLength", String.valueOf(maxDbLen));
			}
			if(displayLength > 0 && displayLength <= maxDbLen && displayLength < businessTypeMaxLength) {
				addAnnotationPropertyToTripleANameSpace(aaaAttribute, "AttrDefaultDisplayLength", String.valueOf(displayLength));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void addAnnotationPropertyToTripleANameSpace(MdfPropertyImpl aaaAttribute,
			String key, String value) {
		MdfAnnotationImpl ann = (MdfAnnotationImpl)aaaAttribute.getAnnotation(TRIPLEA_NAMESPACE, TRIPLE_A_ANNOTATION_NAME);
		if (ann == null) {
			ann = (MdfAnnotationImpl) MdfFactory.eINSTANCE.createMdfAnnotation();
			ann.setNamespace(TRIPLEA_NAMESPACE);
			ann.setName(TRIPLE_A_ANNOTATION_NAME);
			aaaAttribute.getAnnotations().add(ann);
		}
		MdfAnnotationPropertyImpl property = (MdfAnnotationPropertyImpl)MdfFactory.eINSTANCE.
				createMdfAnnotationProperty();
		property.setCDATA(false);
		property.setName(key);
		property.setValue(value);
		ann.getProperties().add(property);
	}

	private int getDefaultLength(MdfBusinessType businessType) {
		String value = businessType.getAnnotation(TRIPLEA_NAMESPACE_CONSTRAINT, TRIPLEA_CONSTRAINTS_NAME).getProperty("maxLength").getValue();
		return Integer.valueOf(value);
	}

	private String getBusinessTypeValue(MdfBusinessType businessType) {
		if(businessType.getAnnotation(TRIPLEA_NAMESPACE, TRIPLE_A_ANNOTATION_NAME) != null &&
				businessType.getAnnotation(TRIPLEA_NAMESPACE, TRIPLE_A_ANNOTATION_NAME).getProperty("BType") != null) {
			return  businessType.getAnnotation(TRIPLEA_NAMESPACE, TRIPLE_A_ANNOTATION_NAME).getProperty("BType").getValue();
		}
		return null;
	}
}
