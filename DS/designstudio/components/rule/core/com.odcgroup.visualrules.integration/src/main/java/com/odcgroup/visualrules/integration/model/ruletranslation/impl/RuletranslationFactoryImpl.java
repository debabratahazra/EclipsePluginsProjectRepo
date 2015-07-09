/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation.impl;

import com.odcgroup.visualrules.integration.model.ruletranslation.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class RuletranslationFactoryImpl extends EFactoryImpl implements RuletranslationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RuletranslationFactory init() {
		try {
			RuletranslationFactory theRuletranslationFactory = (RuletranslationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/ruletranslation"); 
			if (theRuletranslationFactory != null) {
				return theRuletranslationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RuletranslationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuletranslationFactoryImpl() {
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
			case RuletranslationPackage.RULE_TRANSLATION_MAP: return (EObject)createRuleTranslationMap();
			case RuletranslationPackage.TRANSLATION: return createTranslation();
			case RuletranslationPackage.RULE_MESSAGE_PROXY: return createRuleMessageProxy();
			case RuletranslationPackage.RULE_TRANSLATION_REPO: return createRuleTranslationRepo();
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
			case RuletranslationPackage.TRANSLATION_KIND:
				return createTranslationKindFromString(eDataType, initialValue);
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
			case RuletranslationPackage.TRANSLATION_KIND:
				return convertTranslationKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, RuleMessageProxy> createRuleTranslationMap() {
		RuleTranslationMapImpl ruleTranslationMap = new RuleTranslationMapImpl();
		return ruleTranslationMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Translation createTranslation() {
		TranslationImpl translation = new TranslationImpl();
		return translation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleMessageProxy createRuleMessageProxy() {
		RuleMessageProxyImpl ruleMessageProxy = new RuleMessageProxyImpl();
		return ruleMessageProxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleTranslationRepo createRuleTranslationRepo() {
		RuleTranslationRepoImpl ruleTranslationRepo = new RuleTranslationRepoImpl();
		return ruleTranslationRepo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TranslationKind createTranslationKindFromString(EDataType eDataType, String initialValue) {
		TranslationKind result = TranslationKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTranslationKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuletranslationPackage getRuletranslationPackage() {
		return (RuletranslationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RuletranslationPackage getPackage() {
		return RuletranslationPackage.eINSTANCE;
	}

} //RuletranslationFactoryImpl
