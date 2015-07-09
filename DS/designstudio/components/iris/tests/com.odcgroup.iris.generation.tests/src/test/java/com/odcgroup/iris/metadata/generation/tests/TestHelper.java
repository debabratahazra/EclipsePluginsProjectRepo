package com.odcgroup.iris.metadata.generation.tests;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 * Contains useful methods for generating objects such as MdfDomain object needed by many of the test cases
 *
 * @author agoulding
 */
public class TestHelper {

	
	/**
	 * Returns Application object based on the TEC.ITEMS application.
	 * @return
	 */
	public Application getTecItemsAsApplication() {
		return new Application(getTecItemsAsClass());
	}
	
	/**
	 * Returns Application object based on the TEC.ITEMS application.
	 * @return
	 */
	public MdfDomain getTecItemsAsDomain() {
		return createDomain(getTecItemsAsClass());
	}
	
	/**
	 * Returns Application object based on the hypothetical application.
	 * @return
	 */
	public Application getBasicAppAsApplication() {
		return new Application(getBasicAppAsClass());
	}
	
	
	/**
	 * Creates the first few fields of TEC.ITEMS, a good example with mv group that has 3 fields, one of which is an sv group.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MdfClass getTecItemsAsClass() {
		String appName = "TEC.ITEMS";
		MdfClass mdfClass = createClass(appName);
		mdfClass.getProperties().add(createAppField("TEC.PROFILE.ID", 0.0, true, PrimitivesDomain.INTEGER));
		
		// DESCRIPTION is a multivalue field - only one field in the group
		List<MdfProperty> mdfProperties = new ArrayList<MdfProperty>();
		mdfProperties.add(createAppField("DESCRIPTION", 1.0, false, PrimitivesDomain.STRING));
		mdfClass.getProperties().add(createAssociation(appName, mdfProperties));

		// AREA and METRIC.TYPE are single value fields
		mdfClass.getProperties().add(createAppField("AREA", 2.0, false, PrimitivesDomain.STRING));
		mdfClass.getProperties().add(createAppField("METRIC.TYPE", 3.0, false, PrimitivesDomain.STRING));

		// SUBSCRIBER is a subvalue field inside the THRESHOLD.TYPE group
		mdfProperties = new ArrayList<MdfProperty>();
		mdfProperties.add(createAppField("SUBSCRIBER", 6.0, false, PrimitivesDomain.STRING));
		MdfProperty subscriberSvGroup = createAssociation(appName, mdfProperties);

		// THRESHOLD.TYPE, THRESHOLD and the subscriber subvalue are all part of the THRESHOLD.TYPE group 
		mdfProperties = new ArrayList<MdfProperty>();
		mdfProperties.add(createAppField("THRESHOLD.TYPE", 4.0, false, PrimitivesDomain.STRING));
		mdfProperties.add(createAppField("THRESHOLD", 5.0, false, PrimitivesDomain.STRING));
		mdfProperties.add(subscriberSvGroup);
		mdfClass.getProperties().add(createAssociation(appName, mdfProperties));
		
		return mdfClass;
	}
	

	/**
	 * Creates a few fields from the ACCOUNT application, very basic, just has a primary key, date field and integer field in mv group.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MdfClass getBasicAppAsClass() {
		String appName = "ACCOUNT";
		MdfClass mdfClass = createClass(appName);
		
		// Add the primary key
		mdfClass.getProperties().add(createAppField("ACCOUNT.ID", 0.0, true, PrimitivesDomain.INTEGER));
		
		// Add a plain ordinary string single value field
		mdfClass.getProperties().add(createAppField("SHORT.TITLE", 1.0, false, PrimitivesDomain.STRING));

		// FROM.DATE and LOCKED.AMOUNT are in the same mv group
		List<MdfProperty> mdfProperties = new ArrayList<MdfProperty>();
		mdfProperties.add(createAppField("FROM.DATE", 2.0, false, PrimitivesDomain.DATE));
		mdfProperties.add(createAppField("LOCKED.AMOUNT", 3.0, false, PrimitivesDomain.INTEGER));
		mdfClass.getProperties().add(createAssociation(appName, mdfProperties));
		
		return mdfClass;
	}
	

	/**
	 * Creates a few fields from a hypothetical Mortgage AA application.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MdfClass getAaAsClass() {
		
		// Create the main class, has an ID field and a single value field
		String appName = "Mortgage";
		MdfClass mdfClass = createClass(appName);
		mdfClass.getProperties().add(createAppField("ARR.SEQUENCE", 0.0, true, PrimitivesDomain.INTEGER));
		mdfClass.getProperties().add(createAppField("PRODUCT", 1.0, false, PrimitivesDomain.STRING));

		// Create the AA property to go inside
		String customerGroupName = "CustomerGroup";
		MdfClass customerGroup = createClass(appName + "__" + customerGroupName);
		// Add the '@t24:i (aaProperty=CUSTOMER)' annotation to the class
		MdfAnnotation mdfAnnotation = createMdfAnnotation(null, GeneratorConstants.T24_ANNOTATION_AA_PROPERTY, "CUSTOMER");
		customerGroup.getAnnotations().add(mdfAnnotation);
		// Add a field - has no primary key
		customerGroup.getProperties().add(createAppField("PRIMARY.OWNER", 1.0, false, PrimitivesDomain.STRING));
		// Add another field of different type
        customerGroup.getProperties().add(createAppField("DATE.OF.BIRTH", 2.0, false, PrimitivesDomain.DATE));
		// nest it inside the Mortgage class
		mdfClass.getProperties().add(createAssociation(customerGroupName, customerGroup));
		
		return mdfClass;
	}
	

		
	
	/**
	 * Creates an MdfDomain object with the given class inside it
	 * @param mdfClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MdfDomain createDomain(MdfClass mdfClass) {
		MdfDomain mdfDomain = MdfFactory.eINSTANCE.createMdfDomain();
		MdfDomainImpl domainImpl = (MdfDomainImpl)mdfDomain;
		domainImpl.setName("DomainName");
		mdfDomain.getClasses().add(mdfClass);
		return mdfDomain;
	}
	
	/**
	 * Creates an MdfClass with the given name
	 * @param name
	 * @return
	 */
    @SuppressWarnings("unchecked")
	private MdfClass createClass(String name) {
		MdfClassImpl mdfClassImpl =(MdfClassImpl) MdfFactory.eINSTANCE.createMdfClass();
		mdfClassImpl.setName(name);
		mdfClassImpl.getAnnotations().add(createMdfAnnotation(null, "t24Name", name));
		return mdfClassImpl;
	}


	/**
	 * Create an association = multivalue group with a given list of fields
	 * This takes it's name from the first field in the list of fields, plus the name of the parent class 
	 * @param parentName
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MdfAssociation createAssociation(String parentName, List<MdfProperty> properties) {
		String firstFieldname = properties.get(0).getName();
		MdfClass type = createClass(parentName + "__" + firstFieldname);
		for (MdfProperty property : properties) {
			type.getProperties().add(property);
		}
		return createAssociation(firstFieldname, type);
	}

	
	/**
	 * Create an association out of a given MdfClass
	 * @param name of the association - typically a 'plain' fieldname without '__'
	 * @param mdfClass the class
	 * @return
	 */
	private MdfAssociation createAssociation(String name, MdfClass mdfClass) {
		MdfAssociationImpl mdfAssociation = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		mdfAssociation.setName(name);
		mdfAssociation.setContainment(MdfConstants.CONTAINMENT_BYVALUE);
		mdfAssociation.setMultiplicity(MdfConstants.MULTIPLICITY_MANY);
		mdfAssociation.setType(mdfClass);
		return mdfAssociation;
	}

	
	/**
	 * Creates an MdfProperty with the given attributes
	 * @param t24name
	 * @param sysNumber
	 * @param isPrimaryKey
	 * @param fieldType
	 * @return
	 */
	private MdfProperty createAppField(String t24name, double sysNumber, boolean isPrimaryKey, MdfPrimitive fieldType) {
		MdfAttributeImpl field = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
		field.setName(t24name.replace(".", "_"));
		field.setPrimaryKey(isPrimaryKey);
		field.setType(fieldType);
		T24Aspect.setT24Name(field, t24name);
		T24Aspect.setSysNumber(field, sysNumber);
		return field;
	}
	
	
	/**
	 * Creates an MdfAnnotationProperty with the given name and value, and either adds it to the given MdfAnnotation,
	 * or (if MdfAnnotation is null) creates a new MdfAnnotation to store it in.
	 * @param mdfAnnotation
	 * @param name
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MdfAnnotation createMdfAnnotation(MdfAnnotation mdfAnnotation, String name, String value) {
		if (mdfAnnotation == null) {
			MdfAnnotationImpl mdfAnnotationImpl = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
			mdfAnnotationImpl.setNamespace(GeneratorConstants.T24_ANNOTATION_NAMESPACE);
			mdfAnnotationImpl.setName(GeneratorConstants.T24_ANNOTATION_NAME);
			mdfAnnotation = mdfAnnotationImpl;
		}
		MdfAnnotationPropertyImpl mdfAnnotationProperty = (MdfAnnotationPropertyImpl)MdfFactory.eINSTANCE.createMdfAnnotationProperty();
		mdfAnnotationProperty.setName(name);
		mdfAnnotationProperty.setValue(value);
		mdfAnnotation.getProperties().add(mdfAnnotationProperty);
		return mdfAnnotation;
	}


}
