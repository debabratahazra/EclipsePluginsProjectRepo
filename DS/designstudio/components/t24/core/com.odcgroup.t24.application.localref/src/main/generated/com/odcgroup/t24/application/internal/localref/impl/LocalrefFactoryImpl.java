/**
 */
package com.odcgroup.t24.application.internal.localref.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.t24.application.internal.localref.LocalRef;
import com.odcgroup.t24.application.internal.localref.LocalrefFactory;
import com.odcgroup.t24.application.internal.localref.LocalrefPackage;
import com.odcgroup.t24.application.internal.localref.NoInputChange;
import com.odcgroup.t24.application.internal.localref.VettingTable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LocalrefFactoryImpl extends EFactoryImpl implements LocalrefFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LocalrefFactory init() {
		try {
			LocalrefFactory theLocalrefFactory = (LocalrefFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/t24/application/localref/model/LocalRef"); 
			if (theLocalrefFactory != null) {
				return theLocalrefFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LocalrefFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalrefFactoryImpl() {
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
			case LocalrefPackage.LOCAL_REF: return createLocalRef();
			case LocalrefPackage.VETTING_TABLE: return createVettingTable();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case LocalrefPackage.NO_INPUT_CHANGE:
				return createNoInputChangeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case LocalrefPackage.NO_INPUT_CHANGE:
				return convertNoInputChangeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalRef createLocalRef() {
		LocalRefImpl localRef = new LocalRefImpl();
		return localRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VettingTable createVettingTable() {
		VettingTableImpl vettingTable = new VettingTableImpl();
		return vettingTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoInputChange createNoInputChangeFromString(EDataType eDataType, String initialValue) {
		NoInputChange result = NoInputChange.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNoInputChangeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalrefPackage getLocalrefPackage() {
		return (LocalrefPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LocalrefPackage getPackage() {
		return LocalrefPackage.eINSTANCE;
	}

} //LocalrefFactoryImpl
