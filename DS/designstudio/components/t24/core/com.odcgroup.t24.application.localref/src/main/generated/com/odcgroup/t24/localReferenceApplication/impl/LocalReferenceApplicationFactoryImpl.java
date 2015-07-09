/**
 */
package com.odcgroup.t24.localReferenceApplication.impl;

import com.odcgroup.t24.localReferenceApplication.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LocalReferenceApplicationFactoryImpl extends EFactoryImpl implements LocalReferenceApplicationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LocalReferenceApplicationFactory init() {
		try {
			LocalReferenceApplicationFactory theLocalReferenceApplicationFactory = (LocalReferenceApplicationFactory)EPackage.Registry.INSTANCE.getEFactory(LocalReferenceApplicationPackage.eNS_URI);
			if (theLocalReferenceApplicationFactory != null) {
				return theLocalReferenceApplicationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LocalReferenceApplicationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalReferenceApplicationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case LocalReferenceApplicationPackage.LOCAL_REFERENCE_APPLICATION: return createLocalReferenceApplication();
			case LocalReferenceApplicationPackage.LOCAL_FIELD: return createLocalField();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalReferenceApplication createLocalReferenceApplication() {
		LocalReferenceApplicationImpl localReferenceApplication = new LocalReferenceApplicationImpl();
		return localReferenceApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalField createLocalField() {
		LocalFieldImpl localField = new LocalFieldImpl();
		return localField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalReferenceApplicationPackage getLocalReferenceApplicationPackage() {
		return (LocalReferenceApplicationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LocalReferenceApplicationPackage getPackage() {
		return LocalReferenceApplicationPackage.eINSTANCE;
	}

} //LocalReferenceApplicationFactoryImpl
