/**
 */
package com.odcgroup.edge.t24ui;

import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.odcgroup.edge.t24ui.T24UIFactory
 * @model kind="package"
 * @generated
 */
public interface T24UIPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "t24ui";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.temenos.com/DS/t24ui";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "t24ui";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	T24UIPackage eINSTANCE = com.odcgroup.edge.t24ui.impl.T24UIPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.impl.AvailableCOSPatternsImpl <em>Available COS Patterns</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.impl.AvailableCOSPatternsImpl
	 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getAvailableCOSPatterns()
	 * @generated
	 */
	int AVAILABLE_COS_PATTERNS = 0;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_COS_PATTERNS__PATTERNS = 0;

	/**
	 * The number of structural features of the '<em>Available COS Patterns</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_COS_PATTERNS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.impl.CompositeScreenImpl <em>Composite Screen</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.impl.CompositeScreenImpl
	 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getCompositeScreen()
	 * @generated
	 */
	int COMPOSITE_SCREEN = 1;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__PATTERN = PatternPackage.ABSTRACT_COS__PATTERN;

	/**
	 * The feature id for the '<em><b>Panel Separator Option</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__PANEL_SEPARATOR_OPTION = PatternPackage.ABSTRACT_COS__PANEL_SEPARATOR_OPTION;

	/**
	 * The feature id for the '<em><b>Accordion Pattern Multi Expandable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__ACCORDION_PATTERN_MULTI_EXPANDABLE = PatternPackage.ABSTRACT_COS__ACCORDION_PATTERN_MULTI_EXPANDABLE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__NAME = PatternPackage.ABSTRACT_COS__NAME;

	/**
	 * The feature id for the '<em><b>Panels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__PANELS = PatternPackage.ABSTRACT_COS__PANELS;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__TITLE = PatternPackage.ABSTRACT_COS__TITLE;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__DOMAIN = PatternPackage.ABSTRACT_COS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Menu Dsl Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN__MENU_DSL_NAME = PatternPackage.ABSTRACT_COS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Top Level COS</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPOSITE_SCREEN__TOP_LEVEL_COS = PatternPackage.ABSTRACT_COS_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Composite Screen</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SCREEN_FEATURE_COUNT = PatternPackage.ABSTRACT_COS_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.impl.BespokeCompositeScreenImpl <em>Bespoke Composite Screen</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.impl.BespokeCompositeScreenImpl
	 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getBespokeCompositeScreen()
	 * @generated
	 */
	int BESPOKE_COMPOSITE_SCREEN = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BESPOKE_COMPOSITE_SCREEN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BESPOKE_COMPOSITE_SCREEN__SECTIONS = 1;

	/**
	 * The number of structural features of the '<em>Bespoke Composite Screen</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BESPOKE_COMPOSITE_SCREEN_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.impl.AvailableTranslationLanguagesImpl <em>Available Translation Languages</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.impl.AvailableTranslationLanguagesImpl
	 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getAvailableTranslationLanguages()
	 * @generated
	 */
	int AVAILABLE_TRANSLATION_LANGUAGES = 3;

	/**
	 * The feature id for the '<em><b>Languages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES = 0;

	/**
	 * The number of structural features of the '<em>Available Translation Languages</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_TRANSLATION_LANGUAGES_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.AvailableCOSPatterns <em>Available COS Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Available COS Patterns</em>'.
	 * @see com.odcgroup.edge.t24ui.AvailableCOSPatterns
	 * @generated
	 */
	EClass getAvailableCOSPatterns();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.AvailableCOSPatterns#getPatterns <em>Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Patterns</em>'.
	 * @see com.odcgroup.edge.t24ui.AvailableCOSPatterns#getPatterns()
	 * @see #getAvailableCOSPatterns()
	 * @generated
	 */
	EReference getAvailableCOSPatterns_Patterns();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.CompositeScreen <em>Composite Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Screen</em>'.
	 * @see com.odcgroup.edge.t24ui.CompositeScreen
	 * @generated
	 */
	EClass getCompositeScreen();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.CompositeScreen#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain</em>'.
	 * @see com.odcgroup.edge.t24ui.CompositeScreen#getDomain()
	 * @see #getCompositeScreen()
	 * @generated
	 */
	EAttribute getCompositeScreen_Domain();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.CompositeScreen#getMenuDslName <em>Menu Dsl Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Menu Dsl Name</em>'.
	 * @see com.odcgroup.edge.t24ui.CompositeScreen#getMenuDslName()
	 * @see #getCompositeScreen()
	 * @generated
	 */
	EAttribute getCompositeScreen_MenuDslName();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.CompositeScreen#isTopLevelCOS <em>Top Level COS</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Top Level COS</em>'.
	 * @see com.odcgroup.edge.t24ui.CompositeScreen#isTopLevelCOS()
	 * @see #getCompositeScreen()
	 * @generated
	 */
    EAttribute getCompositeScreen_TopLevelCOS();

    /**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen <em>Bespoke Composite Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bespoke Composite Screen</em>'.
	 * @see com.odcgroup.edge.t24ui.BespokeCompositeScreen
	 * @generated
	 */
	EClass getBespokeCompositeScreen();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.BespokeCompositeScreen#getName()
	 * @see #getBespokeCompositeScreen()
	 * @generated
	 */
	EAttribute getBespokeCompositeScreen_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen#getSections <em>Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sections</em>'.
	 * @see com.odcgroup.edge.t24ui.BespokeCompositeScreen#getSections()
	 * @see #getBespokeCompositeScreen()
	 * @generated
	 */
	EReference getBespokeCompositeScreen_Sections();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.AvailableTranslationLanguages <em>Available Translation Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Available Translation Languages</em>'.
	 * @see com.odcgroup.edge.t24ui.AvailableTranslationLanguages
	 * @generated
	 */
	EClass getAvailableTranslationLanguages();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.AvailableTranslationLanguages#getLanguages <em>Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Languages</em>'.
	 * @see com.odcgroup.edge.t24ui.AvailableTranslationLanguages#getLanguages()
	 * @see #getAvailableTranslationLanguages()
	 * @generated
	 */
	EReference getAvailableTranslationLanguages_Languages();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	T24UIFactory getT24UIFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.impl.AvailableCOSPatternsImpl <em>Available COS Patterns</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.impl.AvailableCOSPatternsImpl
		 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getAvailableCOSPatterns()
		 * @generated
		 */
		EClass AVAILABLE_COS_PATTERNS = eINSTANCE.getAvailableCOSPatterns();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AVAILABLE_COS_PATTERNS__PATTERNS = eINSTANCE.getAvailableCOSPatterns_Patterns();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.impl.CompositeScreenImpl <em>Composite Screen</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.impl.CompositeScreenImpl
		 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getCompositeScreen()
		 * @generated
		 */
		EClass COMPOSITE_SCREEN = eINSTANCE.getCompositeScreen();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOSITE_SCREEN__DOMAIN = eINSTANCE.getCompositeScreen_Domain();

		/**
		 * The meta object literal for the '<em><b>Menu Dsl Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOSITE_SCREEN__MENU_DSL_NAME = eINSTANCE.getCompositeScreen_MenuDslName();

		/**
		 * The meta object literal for the '<em><b>Top Level COS</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute COMPOSITE_SCREEN__TOP_LEVEL_COS = eINSTANCE.getCompositeScreen_TopLevelCOS();

        /**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.impl.BespokeCompositeScreenImpl <em>Bespoke Composite Screen</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.impl.BespokeCompositeScreenImpl
		 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getBespokeCompositeScreen()
		 * @generated
		 */
		EClass BESPOKE_COMPOSITE_SCREEN = eINSTANCE.getBespokeCompositeScreen();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BESPOKE_COMPOSITE_SCREEN__NAME = eINSTANCE.getBespokeCompositeScreen_Name();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BESPOKE_COMPOSITE_SCREEN__SECTIONS = eINSTANCE.getBespokeCompositeScreen_Sections();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.impl.AvailableTranslationLanguagesImpl <em>Available Translation Languages</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.impl.AvailableTranslationLanguagesImpl
		 * @see com.odcgroup.edge.t24ui.impl.T24UIPackageImpl#getAvailableTranslationLanguages()
		 * @generated
		 */
		EClass AVAILABLE_TRANSLATION_LANGUAGES = eINSTANCE.getAvailableTranslationLanguages();

		/**
		 * The meta object literal for the '<em><b>Languages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES = eINSTANCE.getAvailableTranslationLanguages_Languages();

	}

} //T24UIPackage
