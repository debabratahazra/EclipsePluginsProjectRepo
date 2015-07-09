package com.odcgroup.domain.edmx.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.odata4j.core.ImmutableList;
import org.odata4j.core.NamespacedAnnotation;
import org.odata4j.edm.EdmComplexType;
import org.odata4j.edm.EdmEntityContainer;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmNavigationProperty;
import org.odata4j.edm.EdmProperty;
import org.odata4j.edm.EdmProperty.CollectionKind;
import org.odata4j.edm.EdmSchema;
import org.odata4j.edm.EdmSimpleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.domain.annotations.MdfAnnotationsUtil;
import com.odcgroup.domain.edmx.EDMXDomainDetail;
import com.odcgroup.domain.edmx.EDMXImportException;
import com.odcgroup.domain.edmx.EDMXImportReport;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;

/**
 * Transforms an M$ EDMX (OData) into DS Domain.
 */
public class EDMX2DomainMapper {
	/**
	 * 
	 */
	private static final String O_DATA = "OData";

	/**
	 * 
	 */
	private static final String SEMANTIC_TYPE = "semanticType";

	private static final Logger log = LoggerFactory.getLogger(EDMX2DomainMapper.class);

	public static final String NAMESPACE_ODC = "http://www.odcgroup.com/";
	public static final String JAVA_PACKAGE_BASE  = "com.odcgroup.odata."; 
	public static final String ODATA = O_DATA;
	public static final String NAMESPACE_ODATA = NAMESPACE_ODC+"mdf/"+ODATA;
	public static final String IRIS_ODATA_EXTENSION="http://iris.temenos.com/odata-extensions";
	
	private MdfDomainImpl currentDomain;
	private EdmSchema currentSchema;
	private Map<String, MdfAssociationImpl> linkCrossSchemaUnresolved = new HashMap<String, MdfAssociationImpl>();
	private Map<String, MdfDomainImpl> mdfDomains = new HashMap<String, MdfDomainImpl>();
	
	private EDMXImportReport report;
	
	/**
	 * @param report
	 */
	public EDMX2DomainMapper(EDMXImportReport report) {
		this.report = report;
	}

	public Collection<MdfDomainImpl> map(ImmutableList<EdmSchema> schemas) throws EDMXImportException {
		try {
			for (EdmSchema schema : schemas) {
				currentSchema = schema;
				report.add(new EDMXDomainDetail(schema.getNamespace()));
				currentDomain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
				currentDomain.setNamespace(NAMESPACE_ODC+toNameSpace(schema.getNamespace()));
				currentDomain.setName(toModelName(schema.getNamespace()));
				JavaAspectDS.setPackage(currentDomain, JAVA_PACKAGE_BASE+toName(schema.getNamespace()).toLowerCase());
				addAnnotationODataName(currentDomain,schema.getNamespace());
				buildComplexType();
				buildEntities();
	
				if (!currentDomain.getEntities().isEmpty())
					mdfDomains.put(currentDomain.getName(), currentDomain);
			}	
			buildCrossSchemaLink();
		} catch(Exception e) {
			throw new EDMXImportException("Error mapping Domain:"+currentSchema.getNamespace(), e);
		}

		return new ArrayList<MdfDomainImpl>(mdfDomains.values());
	}

	@SuppressWarnings("unchecked")
	private void buildComplexType() {
		// first pass
		for (EdmComplexType complexType : currentSchema.getComplexTypes()) {
			MdfClassImpl newClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
			newClass.setName(complexType.getName());

			for (EdmProperty edmProperty : complexType.getProperties()) {
				if (edmProperty.getType().isSimple()) {
					addSimpleProperty(newClass, edmProperty, (EdmSimpleType<?>) edmProperty.getType());
				}
			}
			addSeqNo(newClass);
			currentDomain.getClasses().add(newClass);
		}

		// second pass, complex type of complex type
		for (EdmComplexType complexType : currentSchema.getComplexTypes()) {
			MdfClassImpl mdfClass = (MdfClassImpl) currentDomain.getClass(complexType.getName());
			for (EdmProperty property : complexType.getProperties()) {
				if (property.getType() instanceof EdmComplexType) {
					addComplexProperty(mdfClass, property, (EdmComplexType) property.getType());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void buildEntities() {
		for (EdmEntityType edmEntity : currentSchema.getEntityTypes()) {
			MdfClassImpl newClass = (MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
			newClass.setName(edmEntity.getName());
			
			for (EdmProperty property : edmEntity.getProperties()) {
				MdfPropertyImpl attribute = addProperty(newClass, property);
				if (edmEntity.getKeys().contains(property.getName())) {
					attribute.setPrimaryKey(true);
				}
			}

			addAnnotation(newClass);
			currentDomain.getClasses().add(newClass);
		}

		// second pass for navigation properties
		for (EdmEntityType edmEntity : currentSchema.getEntityTypes()) {
			MdfClassImpl mdfClass = (MdfClassImpl) currentDomain.getClass(edmEntity.getName());
			for (EdmNavigationProperty navProperty : edmEntity.getNavigationProperties()) {
				addAssociation(mdfClass, navProperty);
			}
		}
	}
	
	/**
	 * add annotation EntitySet on the mdf Class
	 * @param mdfClass
	 */
	private void addAnnotation(MdfClass mdfClass){
		List<EdmEntityContainer> containers = currentSchema.getEntityContainers();
		for (EdmEntityContainer edmEntityContainer : containers) {
			List<EdmEntitySet> entitySets = edmEntityContainer.getEntitySets();
			for (EdmEntitySet edmEntitySet : entitySets) {
				if (edmEntitySet.getType().getName().equals(mdfClass.getName())){
					MdfAnnotationsUtil.setAnnotationValue(mdfClass,NAMESPACE_ODATA, "EntitySet", edmEntitySet.getName());
					break;
				}
			}
		}
	}
	
	
	/**
	 * add annotation OriginalName on the property
	 * @param mdfClass
	 */
	private void addAnnotationODataName(MdfModelElement mdfProperty,String orignalName){
		MdfAnnotationsUtil.setAnnotationProperty(mdfProperty, NAMESPACE_ODATA, O_DATA, "Name", orignalName,false);
	}
	
	/**
	 * add annotation Iris Semantic Type on the property
	 * @param mdfClass
	 */
	private void addAnnotationODataSemanticType(MdfModelElement mdfProperty,String semanticType){
		MdfAnnotationsUtil.setAnnotationProperty(mdfProperty, NAMESPACE_ODATA, O_DATA, "SemanticType",semanticType ,false);
	}
	
	private MdfPropertyImpl addProperty(MdfClass mdfClass, EdmProperty property) {
		if (property.getType() instanceof EdmComplexType) {
			return addComplexProperty(mdfClass, property, (EdmComplexType) property.getType());
		} else {
			return addSimpleProperty(mdfClass, property, (EdmSimpleType<?>) property.getType());
		}
	}

	@SuppressWarnings("unchecked")
	private MdfAttributeImpl addSimpleProperty(MdfClass mdfClass, EdmProperty edmProperty, EdmSimpleType<?> edmType) {
		MdfAttributeImpl attribute = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attribute.setName(toName(edmProperty.getName()));
		attribute.setRequired(!edmProperty.isNullable());
		NamespacedAnnotation<?> anno  =edmProperty.findAnnotation(IRIS_ODATA_EXTENSION,SEMANTIC_TYPE);
		String businessType = null;
		if (anno != null){ 
			businessType = anno.getValue().toString();
			addAnnotationODataSemanticType(attribute,businessType);
			attribute.setType(TypeMapper.toMdfPrimitive((EdmSimpleType<?>) edmProperty.getType(),businessType));
		} else {
			attribute.setType(TypeMapper.toMdfPrimitive((EdmSimpleType<?>) edmProperty.getType()));
		}

		 if (edmProperty.getDefaultValue() != null) {
			attribute.setDefault(edmProperty.getDefaultValue());
		}
		addAnnotationODataName(attribute,edmProperty.getName());
		mdfClass.getProperties().add(attribute);

		return attribute;
	}

	@SuppressWarnings("unchecked")
	private MdfAssociationImpl addComplexProperty(MdfClass mdfClass, EdmProperty property, EdmComplexType edmType) {
		MdfClass mdfType = currentDomain.getClass(edmType.getName());
		// addAssociationByValue
		MdfAssociationImpl asso = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		asso.setName(toName(property.getName()));
		asso.setContainment(MdfContainment.BY_VALUE);
		CollectionKind collKind = property.getCollectionKind();
		if (CollectionKind.NONE.equals(collKind)){
			asso.setMultiplicity(MdfMultiplicity.ONE);
		} else {
			asso.setMultiplicity(MdfMultiplicity.MANY);
		}
		asso.setType(mdfType);
		asso.setRequired(!property.isNullable());
		addAnnotationODataName(asso,property.getName());
		mdfClass.getProperties().add(asso);

		return asso;
	}

	/**
	 * Transform a navigation property to a MdfAssociation.
	 */
	@SuppressWarnings("unchecked")
	private MdfAssociationImpl addAssociation(MdfClass mdfClass, EdmNavigationProperty navProperty) {
		if (mdfClass.getProperty(toName(navProperty.getName())) != null) {
			// navigation property already exist, T24 duplicate navigation
			// property, probably a bug.
			log.warn("The navigation property " + navProperty.getName() + " already exist for the entity "
					+ mdfClass.getName());
			return null;
		}

		MdfAssociationImpl asso = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		asso.setName(toName(navProperty.getName()));
		asso.setContainment(MdfContainment.BY_REFERENCE);
		switch (navProperty.getToRole().getMultiplicity()) {
		case MANY:
			asso.setMultiplicity(MdfMultiplicity.MANY);
			asso.setRequired(false);
			break;
		case ONE:
			asso.setMultiplicity(MdfMultiplicity.ONE);
			asso.setRequired(true);
			break;
		case ZERO_TO_ONE:
			asso.setMultiplicity(MdfMultiplicity.ONE);
			asso.setRequired(false);
			break;
		}
		addAnnotationODataName(asso, navProperty.getName());
		mdfClass.getProperties().add(asso);

		MdfClass mdfType = currentDomain.getClass(navProperty.getToRole().getType().getName());
		if (mdfType == null) {
			linkCrossSchemaUnresolved
					.put(navProperty.getToRole().getType().getFullyQualifiedTypeName() + "." + asso.getQualifiedName(),
							asso);
		} else {
			asso.setType(mdfType);
		}

		return asso;
	}

	private void buildCrossSchemaLink() {
		for (Entry<String, MdfAssociationImpl> elmt : linkCrossSchemaUnresolved.entrySet()) {
			String[] key = elmt.getKey().split("\\.");
			MdfDomainImpl domain = mdfDomains.get(key[0]);
			if (domain != null) {
				MdfClass mdfCls = domain.getClass(key[1]);
				if (mdfCls != null) {
					elmt.getValue().setType(mdfCls);
				}
			}
		}
	}

	private String toNameSpace(String name) {
		StringBuffer sb = new StringBuffer();
		boolean upperStart = true;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				if (upperStart) {
					sb.append(Character.toLowerCase(c));
				} else {
					upperStart = false;
					sb.append('-');
					sb.append(Character.toLowerCase(c));
				}
			} else {
				upperStart = false;
				sb.append(c);
			}
		}

		return sb.toString();
	}
	
	
	private String toModelName(String name){
		StringBuilder sb = new StringBuilder();
		char[] _name = name.toCharArray();
		boolean upperNextCar = true;
		for (char c : _name) {
			if (Character.isLetterOrDigit(c) || c == '_'){
				if (upperNextCar){
					sb.append(Character.toUpperCase(c));
					upperNextCar = false;
					
				} else {
					sb.append(c);
				}
			} else if (c == '-'){
				upperNextCar = true;
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Add a fake primary key, mandatory for create a dataset on the complextype 
	 * @param complexType
	 */
	@SuppressWarnings("unchecked")
	private void addSeqNo(MdfClassImpl complexType){
		MdfAttributeImpl attribute = (MdfAttributeImpl) MdfFactory.eINSTANCE.createMdfAttribute();
		attribute.setName("seqNo");
		attribute.setRequired(false);
		attribute.setPrimaryKey(true);
		attribute.setType(PrimitivesDomain.INTEGER_OBJ);
		attribute.setDefault("0");
		complexType.getProperties().add(attribute);

	}
	/**
	 * This method  remove all characters are not a digit, Letter or '_'
	 * @param name
	 * @return
	 */
	private String toName(String name){
		StringBuilder sb = new StringBuilder();
		char[] _name = name.toCharArray();
		for (char c : _name) {
			if (Character.isLetterOrDigit(c) || c == '_'){
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

}
