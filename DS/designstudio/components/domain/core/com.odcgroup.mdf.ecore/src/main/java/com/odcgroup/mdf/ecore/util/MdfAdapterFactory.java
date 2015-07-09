/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * @see com.odcgroup.mdf.ecore.MdfPackage
 */
public class MdfAdapterFactory extends AdapterFactoryImpl {
    /**
	 * The cached model package.
	 */
    protected static MdfPackage modelPackage;

    /**
	 * Creates an instance of the adapter factory.
	 */
    public MdfAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MdfPackage.eINSTANCE;
		}
	}

    /**
	 * Returns whether this factory is applicable for the type of the object.
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * @return whether this factory is applicable for the type of the object.
	 */
    public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

    /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 */
    protected MdfSwitch modelSwitch =
        new MdfSwitch() {
			public Object caseMdfModelElement(MdfModelElement object) {
				return createMdfModelElementAdapter();
			}
			public Object caseMdfEntity(MdfEntity object) {
				return createMdfEntityAdapter();
			}
			public Object caseMdfDomain(MdfDomain object) {
				return createMdfDomainAdapter();
			}
			public Object caseMdfClass(MdfClass object) {
				return createMdfClassAdapter();
			}
			public Object caseMdfProperty(MdfProperty object) {
				return createMdfPropertyAdapter();
			}
			public Object caseMdfAttribute(MdfAttribute object) {
				return createMdfAttributeAdapter();
			}
			public Object caseMdfAssociation(MdfAssociation object) {
				return createMdfAssociationAdapter();
			}
			public Object caseMdfReverseAssociation(MdfReverseAssociation object) {
				return createMdfReverseAssociationAdapter();
			}
			public Object caseMdfPrimitive(MdfPrimitive object) {
				return createMdfPrimitiveAdapter();
			}
			public Object caseMdfEnumeration(MdfEnumeration object) {
				return createMdfEnumerationAdapter();
			}
			public Object caseMdfEnumValue(MdfEnumValue object) {
				return createMdfEnumValueAdapter();
			}
			public Object caseMdfDataset(MdfDataset object) {
				return createMdfDatasetAdapter();
			}
			public Object caseMdfDatasetProperty(MdfDatasetProperty object) {
				return createMdfDatasetPropertyAdapter();
			}
			public Object caseMdfAnnotation(MdfAnnotation object) {
				return createMdfAnnotationAdapter();
			}
			public Object caseMdfAnnotationProperty(MdfAnnotationProperty object) {
				return createMdfAnnotationPropertyAdapter();
			}
			public Object caseMdfDatasetDerivedProperty(MdfDatasetDerivedProperty object) {
				return createMdfDatasetDerivedPropertyAdapter();
			}
			public Object caseMdfDatasetMappedProperty(MdfDatasetMappedProperty object) {
				return createMdfDatasetMappedPropertyAdapter();
			}
			public Object caseMdfBusinessType(MdfBusinessType object) {
				return createMdfBusinessTypeAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

    /**
	 * Creates an adapter for the <code>target</code>.
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 */
    public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfDeprecationInfo <em>Deprecation Info</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfDeprecationInfo
	 */
    public Adapter createMdfDeprecationInfoAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfModelElement <em>Model Element</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfModelElement
	 */
    public Adapter createMdfModelElementAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfEntity <em>Entity</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfEntity
	 */
    public Adapter createMdfEntityAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfDomain <em>Domain</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfDomain
	 */
    public Adapter createMdfDomainAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfClass <em>Class</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfClass
	 */
    public Adapter createMdfClassAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfProperty <em>Property</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfProperty
	 */
    public Adapter createMdfPropertyAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfAttribute <em>Attribute</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfAttribute
	 */
    public Adapter createMdfAttributeAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfAssociation <em>Association</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfAssociation
	 */
    public Adapter createMdfAssociationAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfReverseAssociation <em>Reverse Association</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfReverseAssociation
	 */
    public Adapter createMdfReverseAssociationAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfPrimitive <em>Primitive</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfPrimitive
	 */
    public Adapter createMdfPrimitiveAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfEnumeration <em>Enumeration</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumeration
	 */
    public Adapter createMdfEnumerationAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfEnumValue <em>Enum Value</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfEnumValue
	 */
    public Adapter createMdfEnumValueAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfDataset <em>Dataset</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfDataset
	 */
    public Adapter createMdfDatasetAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfDatasetProperty <em>Dataset Property</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetProperty
	 */
    public Adapter createMdfDatasetPropertyAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfAnnotation <em>Annotation</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotation
	 */
    public Adapter createMdfAnnotationAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfAnnotationProperty <em>Annotation Property</em>}'.
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfAnnotationProperty
	 */
    public Adapter createMdfAnnotationPropertyAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty <em>Dataset Derived Property</em>}'.
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty
	 */
	public Adapter createMdfDatasetDerivedPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty <em>Dataset Mapped Property</em>}'.
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty
	 */
	public Adapter createMdfDatasetMappedPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.mdf.metamodel.MdfBusinessType <em>Business Type</em>}'.
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * @return the new adapter.
	 * @see com.odcgroup.mdf.metamodel.MdfBusinessType
	 */
	public Adapter createMdfBusinessTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
     * This default implementation returns null.
	 * @return the new adapter.
	 */
    public Adapter createEObjectAdapter() {
		return null;
	}

} //MdfAdapterFactory
