/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke;

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
 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokeFactory
 * @model kind="package"
 * @generated
 */
public interface BespokePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "bespoke";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.temenos.com/DS/t24ui/cos/bespoke";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "bespoke";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BespokePackage eINSTANCE = com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.SectionImpl <em>Section</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.SectionImpl
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getSection()
	 * @generated
	 */
	int SECTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__RESOURCES = 1;

	/**
	 * The number of structural features of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.PanelImpl <em>Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.PanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getPanel()
	 * @generated
	 */
	int PANEL = 1;

	/**
	 * The number of structural features of the '<em>Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.VersionPanelImpl <em>Version Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.VersionPanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getVersionPanel()
	 * @generated
	 */
	int VERSION_PANEL = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PANEL__VERSION = PANEL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Version Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_PANEL_FEATURE_COUNT = PANEL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.EnquiryPanelImpl <em>Enquiry Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.EnquiryPanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getEnquiryPanel()
	 * @generated
	 */
	int ENQUIRY_PANEL = 3;

	/**
	 * The feature id for the '<em><b>Enquiry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENQUIRY_PANEL__ENQUIRY = PANEL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enquiry Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENQUIRY_PANEL_FEATURE_COUNT = PANEL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.ApplicationPanelImpl <em>Application Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.ApplicationPanelImpl
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getApplicationPanel()
	 * @generated
	 */
	int APPLICATION_PANEL = 4;

	/**
	 * The feature id for the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_PANEL__APPLICATION = PANEL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Application Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_PANEL_FEATURE_COUNT = PANEL_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.bespoke.Section <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Section</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.Section
	 * @generated
	 */
	EClass getSection();

	/**
	 * Returns the meta object for the attribute '{@link com.odcgroup.edge.t24ui.cos.bespoke.Section#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.Section#getName()
	 * @see #getSection()
	 * @generated
	 */
	EAttribute getSection_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.odcgroup.edge.t24ui.cos.bespoke.Section#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.Section#getResources()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_Resources();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.bespoke.Panel <em>Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.Panel
	 * @generated
	 */
	EClass getPanel();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel <em>Version Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel
	 * @generated
	 */
	EClass getVersionPanel();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Version</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel#getVersion()
	 * @see #getVersionPanel()
	 * @generated
	 */
	EReference getVersionPanel_Version();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel <em>Enquiry Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enquiry Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel
	 * @generated
	 */
	EClass getEnquiryPanel();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel#getEnquiry <em>Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Enquiry</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.EnquiryPanel#getEnquiry()
	 * @see #getEnquiryPanel()
	 * @generated
	 */
	EReference getEnquiryPanel_Enquiry();

	/**
	 * Returns the meta object for class '{@link com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel <em>Application Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Panel</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel
	 * @generated
	 */
	EClass getApplicationPanel();

	/**
	 * Returns the meta object for the reference '{@link com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel#getApplication <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Application</em>'.
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel#getApplication()
	 * @see #getApplicationPanel()
	 * @generated
	 */
	EReference getApplicationPanel_Application();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BespokeFactory getBespokeFactory();

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
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.SectionImpl <em>Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.SectionImpl
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getSection()
		 * @generated
		 */
		EClass SECTION = eINSTANCE.getSection();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECTION__NAME = eINSTANCE.getSection_Name();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__RESOURCES = eINSTANCE.getSection_Resources();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.PanelImpl <em>Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.PanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getPanel()
		 * @generated
		 */
		EClass PANEL = eINSTANCE.getPanel();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.VersionPanelImpl <em>Version Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.VersionPanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getVersionPanel()
		 * @generated
		 */
		EClass VERSION_PANEL = eINSTANCE.getVersionPanel();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_PANEL__VERSION = eINSTANCE.getVersionPanel_Version();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.EnquiryPanelImpl <em>Enquiry Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.EnquiryPanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getEnquiryPanel()
		 * @generated
		 */
		EClass ENQUIRY_PANEL = eINSTANCE.getEnquiryPanel();

		/**
		 * The meta object literal for the '<em><b>Enquiry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENQUIRY_PANEL__ENQUIRY = eINSTANCE.getEnquiryPanel_Enquiry();

		/**
		 * The meta object literal for the '{@link com.odcgroup.edge.t24ui.cos.bespoke.impl.ApplicationPanelImpl <em>Application Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.ApplicationPanelImpl
		 * @see com.odcgroup.edge.t24ui.cos.bespoke.impl.BespokePackageImpl#getApplicationPanel()
		 * @generated
		 */
		EClass APPLICATION_PANEL = eINSTANCE.getApplicationPanel();

		/**
		 * The meta object literal for the '<em><b>Application</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_PANEL__APPLICATION = eINSTANCE.getApplicationPanel_Application();

	}

} //BespokePackage
