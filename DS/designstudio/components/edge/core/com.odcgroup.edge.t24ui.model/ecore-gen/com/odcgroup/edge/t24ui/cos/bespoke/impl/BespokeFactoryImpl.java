/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke.impl;

import com.odcgroup.edge.t24ui.cos.bespoke.*;

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
public class BespokeFactoryImpl extends EFactoryImpl implements BespokeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BespokeFactory init() {
		try {
			BespokeFactory theBespokeFactory = (BespokeFactory)EPackage.Registry.INSTANCE.getEFactory(BespokePackage.eNS_URI);
			if (theBespokeFactory != null) {
				return theBespokeFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BespokeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BespokeFactoryImpl() {
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
			case BespokePackage.SECTION: return createSection();
			case BespokePackage.VERSION_PANEL: return createVersionPanel();
			case BespokePackage.ENQUIRY_PANEL: return createEnquiryPanel();
			case BespokePackage.APPLICATION_PANEL: return createApplicationPanel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Section createSection() {
		SectionImpl section = new SectionImpl();
		return section;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionPanel createVersionPanel() {
		VersionPanelImpl versionPanel = new VersionPanelImpl();
		return versionPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnquiryPanel createEnquiryPanel() {
		EnquiryPanelImpl enquiryPanel = new EnquiryPanelImpl();
		return enquiryPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationPanel createApplicationPanel() {
		ApplicationPanelImpl applicationPanel = new ApplicationPanelImpl();
		return applicationPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BespokePackage getBespokePackage() {
		return (BespokePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BespokePackage getPackage() {
		return BespokePackage.eINSTANCE;
	}

} //BespokeFactoryImpl
