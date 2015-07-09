/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EFactory;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.mdf.ecore.MdfPackage
 */
public interface MdfFactory extends EFactory {
    /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    MdfFactory eINSTANCE = com.odcgroup.mdf.ecore.MdfFactoryImpl.init();

    /**
	 * Returns a new object of class '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entity</em>'.

	 */
	MdfEntity createMdfEntity();

				/**
	 * Returns a new object of class '<em>Domain</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain</em>'.

	 */
    MdfDomain createMdfDomain();

    /**
	 * Returns a new object of class '<em>Class</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Class</em>'.

	 */
    MdfClass createMdfClass();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfClass createMdfClass(IProject project);

    /**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.

	 */
    MdfAttribute createMdfAttribute();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfAttribute createMdfAttribute(IProject project);

    /**
	 * Returns a new object of class '<em>Association</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association</em>'.

	 */
    MdfAssociation createMdfAssociation();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfAssociation createMdfAssociation(IProject project);

    /**
	 * Returns a new object of class '<em>Reverse Association</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reverse Association</em>'.

	 */
    MdfReverseAssociation createMdfReverseAssociation();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfReverseAssociation createMdfReverseAssociation(IProject project);

    /**
	 * Returns a new object of class '<em>Primitive</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive</em>'.

	 */
    MdfPrimitive createMdfPrimitive();

    /**
	 * Returns a new object of class '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration</em>'.

	 */
    MdfEnumeration createMdfEnumeration();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfEnumeration createMdfEnumeration(IProject project);

    /**
	 * Returns a new object of class '<em>Enum Value</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Value</em>'.

	 */
    MdfEnumValue createMdfEnumValue();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfEnumValue createMdfEnumValue(IProject project);

    /**
	 * Returns a new object of class '<em>Dataset</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dataset</em>'.

	 */
    MdfDataset createMdfDataset();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
    MdfDataset createMdfDataset(IProject project);

    /**
	 * Returns a new object of class '<em>Annotation</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Annotation</em>'.

	 */
    MdfAnnotation createMdfAnnotation();

    /**
	 * Returns a new object of class '<em>Annotation Property</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Annotation Property</em>'.

	 */
    MdfAnnotationProperty createMdfAnnotationProperty();

    /**
	 * Returns a new object of class '<em>Dataset Derived Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dataset Derived Property</em>'.

	 */
	MdfDatasetDerivedProperty createMdfDatasetDerivedProperty();
    
    /**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	MdfDatasetDerivedProperty createMdfDatasetDerivedProperty(IProject project);

				/**
	 * Returns a new object of class '<em>Dataset Mapped Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dataset Mapped Property</em>'.

	 */
	MdfDatasetMappedProperty createMdfDatasetMappedProperty();
    
    /**
	 * Returns a new object of class '<em>Business Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Business Type</em>'.

	 */
	MdfBusinessType createMdfBusinessType();
	
	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	MdfBusinessType createMdfBusinessType(IProject project);

	/**
     * DS-1349
     * @param project
     * @return
 NOT
     */
	MdfDatasetMappedProperty createMdfDatasetMappedProperty(IProject project);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the package supported by this factory.

	 */
    MdfPackage getMdfPackage();
    
    /**
     * DS-3053
     * @generated NOT
     */
	public MdfDatasetMappedProperty createMdfDatasetMappedProperty(IProject project, MdfModelElement element);

} //MdfFactory
