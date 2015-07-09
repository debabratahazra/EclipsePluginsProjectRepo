/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.odcgroup.mdf.ecore.MdfPackage
 */
public class MdfSwitch {
    /**
	 * The cached model package
	 */
    protected static MdfPackage modelPackage;

    /**
	 * Creates an instance of the switch.
	 */
    public MdfSwitch() {
		if (modelPackage == null) {
			modelPackage = MdfPackage.eINSTANCE;
		}
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 */
    public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 */
    protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 */
    protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case MdfPackage.MDF_MODEL_ELEMENT: {
				MdfModelElement mdfModelElement = (MdfModelElement)theEObject;
				Object result = caseMdfModelElement(mdfModelElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ENTITY: {
				MdfEntity mdfEntity = (MdfEntity)theEObject;
				Object result = caseMdfEntity(mdfEntity);
				if (result == null) result = caseMdfModelElement(mdfEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_DOMAIN: {
				MdfDomain mdfDomain = (MdfDomain)theEObject;
				Object result = caseMdfDomain(mdfDomain);
				if (result == null) result = caseMdfModelElement(mdfDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_CLASS: {
				MdfClass mdfClass = (MdfClass)theEObject;
				Object result = caseMdfClass(mdfClass);
				if (result == null) result = caseMdfEntity(mdfClass);
				if (result == null) result = caseMdfModelElement(mdfClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_PROPERTY: {
				MdfProperty mdfProperty = (MdfProperty)theEObject;
				Object result = caseMdfProperty(mdfProperty);
				if (result == null) result = caseMdfModelElement(mdfProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ATTRIBUTE: {
				MdfAttribute mdfAttribute = (MdfAttribute)theEObject;
				Object result = caseMdfAttribute(mdfAttribute);
				if (result == null) result = caseMdfProperty(mdfAttribute);
				if (result == null) result = caseMdfModelElement(mdfAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ASSOCIATION: {
				MdfAssociation mdfAssociation = (MdfAssociation)theEObject;
				Object result = caseMdfAssociation(mdfAssociation);
				if (result == null) result = caseMdfProperty(mdfAssociation);
				if (result == null) result = caseMdfModelElement(mdfAssociation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_REVERSE_ASSOCIATION: {
				MdfReverseAssociation mdfReverseAssociation = (MdfReverseAssociation)theEObject;
				Object result = caseMdfReverseAssociation(mdfReverseAssociation);
				if (result == null) result = caseMdfProperty(mdfReverseAssociation);
				if (result == null) result = caseMdfModelElement(mdfReverseAssociation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_PRIMITIVE: {
				MdfPrimitive mdfPrimitive = (MdfPrimitive)theEObject;
				Object result = caseMdfPrimitive(mdfPrimitive);
				if (result == null) result = caseMdfEntity(mdfPrimitive);
				if (result == null) result = caseMdfModelElement(mdfPrimitive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ENUMERATION: {
				MdfEnumeration mdfEnumeration = (MdfEnumeration)theEObject;
				Object result = caseMdfEnumeration(mdfEnumeration);
				if (result == null) result = caseMdfPrimitive(mdfEnumeration);
				if (result == null) result = caseMdfEntity(mdfEnumeration);
				if (result == null) result = caseMdfModelElement(mdfEnumeration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ENUM_VALUE: {
				MdfEnumValue mdfEnumValue = (MdfEnumValue)theEObject;
				Object result = caseMdfEnumValue(mdfEnumValue);
				if (result == null) result = caseMdfModelElement(mdfEnumValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_DATASET: {
				MdfDataset mdfDataset = (MdfDataset)theEObject;
				Object result = caseMdfDataset(mdfDataset);
				if (result == null) result = caseMdfEntity(mdfDataset);
				if (result == null) result = caseMdfModelElement(mdfDataset);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_DATASET_PROPERTY: {
				MdfDatasetProperty mdfDatasetProperty = (MdfDatasetProperty)theEObject;
				Object result = caseMdfDatasetProperty(mdfDatasetProperty);
				if (result == null) result = caseMdfModelElement(mdfDatasetProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ANNOTATION: {
				MdfAnnotation mdfAnnotation = (MdfAnnotation)theEObject;
				Object result = caseMdfAnnotation(mdfAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_ANNOTATION_PROPERTY: {
				MdfAnnotationProperty mdfAnnotationProperty = (MdfAnnotationProperty)theEObject;
				Object result = caseMdfAnnotationProperty(mdfAnnotationProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_DATASET_DERIVED_PROPERTY: {
				MdfDatasetDerivedProperty mdfDatasetDerivedProperty = (MdfDatasetDerivedProperty)theEObject;
				Object result = caseMdfDatasetDerivedProperty(mdfDatasetDerivedProperty);
				if (result == null) result = caseMdfDatasetProperty(mdfDatasetDerivedProperty);
				if (result == null) result = caseMdfModelElement(mdfDatasetDerivedProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_DATASET_MAPPED_PROPERTY: {
				MdfDatasetMappedProperty mdfDatasetMappedProperty = (MdfDatasetMappedProperty)theEObject;
				Object result = caseMdfDatasetMappedProperty(mdfDatasetMappedProperty);
				if (result == null) result = caseMdfDatasetProperty(mdfDatasetMappedProperty);
				if (result == null) result = caseMdfModelElement(mdfDatasetMappedProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MdfPackage.MDF_BUSINESS_TYPE: {
				MdfBusinessType mdfBusinessType = (MdfBusinessType)theEObject;
				Object result = caseMdfBusinessType(mdfBusinessType);
				if (result == null) result = caseMdfPrimitive(mdfBusinessType);
				if (result == null) result = caseMdfEntity(mdfBusinessType);
				if (result == null) result = caseMdfModelElement(mdfBusinessType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfModelElement(MdfModelElement object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfEntity(MdfEntity object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Domain</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfDomain(MdfDomain object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Class</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfClass(MdfClass object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfProperty(MdfProperty object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfAttribute(MdfAttribute object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Association</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfAssociation(MdfAssociation object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Reverse Association</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reverse Association</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfReverseAssociation(MdfReverseAssociation object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Primitive</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primitive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfPrimitive(MdfPrimitive object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfEnumeration(MdfEnumeration object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Value</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfEnumValue(MdfEnumValue object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Dataset</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dataset</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfDataset(MdfDataset object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Dataset Property</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dataset Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfDatasetProperty(MdfDatasetProperty object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfAnnotation(MdfAnnotation object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation Property</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotation Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
    public Object caseMdfAnnotationProperty(MdfAnnotationProperty object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Dataset Derived Property</em>'.
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dataset Derived Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
	public Object caseMdfDatasetDerivedProperty(MdfDatasetDerivedProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dataset Mapped Property</em>'.
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dataset Mapped Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
	public Object caseMdfDatasetMappedProperty(MdfDatasetMappedProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Business Type</em>'.
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Business Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 */
	public Object caseMdfBusinessType(MdfBusinessType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 */
    public Object defaultCase(EObject object) {
		return null;
	}

} //MdfSwitch
