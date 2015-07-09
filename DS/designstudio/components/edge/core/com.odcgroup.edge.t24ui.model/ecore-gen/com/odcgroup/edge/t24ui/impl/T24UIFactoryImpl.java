/**
 */
package com.odcgroup.edge.t24ui.impl;

import com.odcgroup.edge.t24ui.*;

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
public class T24UIFactoryImpl extends EFactoryImpl implements T24UIFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static T24UIFactory init() {
		try {
			T24UIFactory theT24UIFactory = (T24UIFactory)EPackage.Registry.INSTANCE.getEFactory(T24UIPackage.eNS_URI);
			if (theT24UIFactory != null) {
				return theT24UIFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new T24UIFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T24UIFactoryImpl() {
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
			case T24UIPackage.AVAILABLE_COS_PATTERNS: return createAvailableCOSPatterns();
			case T24UIPackage.COMPOSITE_SCREEN: return createCompositeScreen();
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN: return createBespokeCompositeScreen();
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES: return createAvailableTranslationLanguages();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AvailableCOSPatterns createAvailableCOSPatterns() {
		AvailableCOSPatternsImpl availableCOSPatterns = new AvailableCOSPatternsImpl();
		return availableCOSPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeScreen createCompositeScreen() {
		CompositeScreenImpl compositeScreen = new CompositeScreenImpl();
		return compositeScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BespokeCompositeScreen createBespokeCompositeScreen() {
		BespokeCompositeScreenImpl bespokeCompositeScreen = new BespokeCompositeScreenImpl();
		return bespokeCompositeScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AvailableTranslationLanguages createAvailableTranslationLanguages() {
		AvailableTranslationLanguagesImpl availableTranslationLanguages = new AvailableTranslationLanguagesImpl();
		return availableTranslationLanguages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T24UIPackage getT24UIPackage() {
		return (T24UIPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static T24UIPackage getPackage() {
		return T24UIPackage.eINSTANCE;
	}

} //T24UIFactoryImpl
