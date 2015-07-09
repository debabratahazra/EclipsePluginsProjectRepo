/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory
 * @model kind="package"
 * @generated
 */
public interface RuletranslationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ruletranslation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.odcgroup.com/ruletranslation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ruletranslation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuletranslationPackage eINSTANCE = com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationMapImpl <em>Rule Translation Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationMapImpl
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getRuleTranslationMap()
	 * @generated
	 */
	int RULE_TRANSLATION_MAP = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRANSLATION_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRANSLATION_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Rule Translation Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRANSLATION_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.TranslationImpl <em>Translation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.TranslationImpl
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getTranslation()
	 * @generated
	 */
	int TRANSLATION = 1;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION__KIND = 2;

	/**
	 * The number of structural features of the '<em>Translation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSLATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleMessageProxyImpl <em>Rule Message Proxy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleMessageProxyImpl
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getRuleMessageProxy()
	 * @generated
	 */
	int RULE_MESSAGE_PROXY = 2;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_MESSAGE_PROXY__CODE = 0;

	/**
	 * The feature id for the '<em><b>Translations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_MESSAGE_PROXY__TRANSLATIONS = 1;

	/**
	 * The number of structural features of the '<em>Rule Message Proxy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_MESSAGE_PROXY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationRepoImpl <em>Rule Translation Repo</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationRepoImpl
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getRuleTranslationRepo()
	 * @generated
	 */
	int RULE_TRANSLATION_REPO = 3;

	/**
	 * The feature id for the '<em><b>Code Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRANSLATION_REPO__CODE_MAP = 0;

	/**
	 * The number of structural features of the '<em>Rule Translation Repo</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRANSLATION_REPO_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind <em>Translation Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getTranslationKind()
	 * @generated
	 */
	int TRANSLATION_KIND = 4;


	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Rule Translation Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Translation Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString" keyRequired="true"
	 *        valueType="com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy" valueContainment="true" valueRequired="true"
	 * @generated
	 */
	EClass getRuleTranslationMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getRuleTranslationMap()
	 * @generated
	 */
	EAttribute getRuleTranslationMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getRuleTranslationMap()
	 * @generated
	 */
	EReference getRuleTranslationMap_Value();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation <em>Translation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Translation</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.Translation
	 * @generated
	 */
	EClass getTranslation();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getLanguage()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Language();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getMessage()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Message();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.Translation#getKind()
	 * @see #getTranslation()
	 * @generated
	 */
	EAttribute getTranslation_Kind();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy <em>Rule Message Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Message Proxy</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy
	 * @generated
	 */
	EClass getRuleMessageProxy();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getCode()
	 * @see #getRuleMessageProxy()
	 * @generated
	 */
	EAttribute getRuleMessageProxy_Code();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getTranslations <em>Translations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Translations</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy#getTranslations()
	 * @see #getRuleMessageProxy()
	 * @generated
	 */
	EReference getRuleMessageProxy_Translations();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo <em>Rule Translation Repo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Translation Repo</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo
	 * @generated
	 */
	EClass getRuleTranslationRepo();

	/**
	 * Returns the meta object for the map '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo#getCodeMap <em>Code Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Code Map</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo#getCodeMap()
	 * @see #getRuleTranslationRepo()
	 * @generated
	 */
	EReference getRuleTranslationRepo_CodeMap();

	/**
	 * Returns the meta object for enum '{@link com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind <em>Translation Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Translation Kind</em>'.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind
	 * @generated
	 */
	EEnum getTranslationKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RuletranslationFactory getRuletranslationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationMapImpl <em>Rule Translation Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationMapImpl
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getRuleTranslationMap()
		 * @generated
		 */
		EClass RULE_TRANSLATION_MAP = eINSTANCE.getRuleTranslationMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TRANSLATION_MAP__KEY = eINSTANCE.getRuleTranslationMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TRANSLATION_MAP__VALUE = eINSTANCE.getRuleTranslationMap_Value();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.TranslationImpl <em>Translation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.TranslationImpl
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getTranslation()
		 * @generated
		 */
		EClass TRANSLATION = eINSTANCE.getTranslation();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__LANGUAGE = eINSTANCE.getTranslation_Language();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__MESSAGE = eINSTANCE.getTranslation_Message();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSLATION__KIND = eINSTANCE.getTranslation_Kind();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleMessageProxyImpl <em>Rule Message Proxy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleMessageProxyImpl
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getRuleMessageProxy()
		 * @generated
		 */
		EClass RULE_MESSAGE_PROXY = eINSTANCE.getRuleMessageProxy();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_MESSAGE_PROXY__CODE = eINSTANCE.getRuleMessageProxy_Code();

		/**
		 * The meta object literal for the '<em><b>Translations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_MESSAGE_PROXY__TRANSLATIONS = eINSTANCE.getRuleMessageProxy_Translations();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationRepoImpl <em>Rule Translation Repo</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationRepoImpl
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getRuleTranslationRepo()
		 * @generated
		 */
		EClass RULE_TRANSLATION_REPO = eINSTANCE.getRuleTranslationRepo();

		/**
		 * The meta object literal for the '<em><b>Code Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TRANSLATION_REPO__CODE_MAP = eINSTANCE.getRuleTranslationRepo_CodeMap();

		/**
		 * The meta object literal for the '{@link com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind <em>Translation Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.TranslationKind
		 * @see com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuletranslationPackageImpl#getTranslationKind()
		 * @generated
		 */
		EEnum TRANSLATION_KIND = eINSTANCE.getTranslationKind();

	}

} //RuletranslationPackage
