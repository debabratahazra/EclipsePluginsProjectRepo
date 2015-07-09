/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.cos.pattern.*;

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
public class PatternFactoryImpl extends EFactoryImpl implements PatternFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PatternFactory init() {
		try {
			PatternFactory thePatternFactory = (PatternFactory)EPackage.Registry.INSTANCE.getEFactory(PatternPackage.eNS_URI);
			if (thePatternFactory != null) {
				return thePatternFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PatternFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatternFactoryImpl() {
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
			case PatternPackage.SINGLE_COMPONENT_PANEL: return createSingleComponentPanel();
			case PatternPackage.MULTI_COMPONENT_PANEL: return createMultiComponentPanel();
			case PatternPackage.INITIAL_COS: return createInitialCOS();
			case PatternPackage.INLINE_INITIAL_COS: return createInlineInitialCOS();
			case PatternPackage.INITIAL_BESPOKE_COS: return createInitialBespokeCOS();
			case PatternPackage.INITIAL_ENQUIRY: return createInitialEnquiry();
			case PatternPackage.INITIAL_SCREEN: return createInitialScreen();
			case PatternPackage.COS_PATTERN: return createCOSPattern();
			case PatternPackage.INITIAL_URL: return createInitialURL();
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
			case PatternPackage.PANEL_SEPARATOR_OPTION:
				return createPanelSeparatorOptionFromString(eDataType, initialValue);
			case PatternPackage.PANEL_OVERFLOW_OPTION:
				return createPanelOverflowOptionFromString(eDataType, initialValue);
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
			case PatternPackage.PANEL_SEPARATOR_OPTION:
				return convertPanelSeparatorOptionToString(eDataType, instanceValue);
			case PatternPackage.PANEL_OVERFLOW_OPTION:
				return convertPanelOverflowOptionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleComponentPanel createSingleComponentPanel() {
		SingleComponentPanelImpl singleComponentPanel = new SingleComponentPanelImpl();
		return singleComponentPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiComponentPanel createMultiComponentPanel() {
		MultiComponentPanelImpl multiComponentPanel = new MultiComponentPanelImpl();
		return multiComponentPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialCOS createInitialCOS() {
		InitialCOSImpl initialCOS = new InitialCOSImpl();
		return initialCOS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InlineInitialCOS createInlineInitialCOS() {
		InlineInitialCOSImpl inlineInitialCOS = new InlineInitialCOSImpl();
		return inlineInitialCOS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialBespokeCOS createInitialBespokeCOS() {
		InitialBespokeCOSImpl initialBespokeCOS = new InitialBespokeCOSImpl();
		return initialBespokeCOS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialEnquiry createInitialEnquiry() {
		InitialEnquiryImpl initialEnquiry = new InitialEnquiryImpl();
		return initialEnquiry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialScreen createInitialScreen() {
		InitialScreenImpl initialScreen = new InitialScreenImpl();
		return initialScreen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public COSPattern createCOSPattern() {
		COSPatternImpl cosPattern = new COSPatternImpl();
		return cosPattern;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public InitialURL createInitialURL() {
		InitialURLImpl initialURL = new InitialURLImpl();
		return initialURL;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelSeparatorOption createPanelSeparatorOptionFromString(EDataType eDataType, String initialValue) {
		PanelSeparatorOption result = PanelSeparatorOption.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPanelSeparatorOptionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelOverflowOption createPanelOverflowOptionFromString(EDataType eDataType, String initialValue) {
		PanelOverflowOption result = PanelOverflowOption.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPanelOverflowOptionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatternPackage getPatternPackage() {
		return (PatternPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PatternPackage getPackage() {
		return PatternPackage.eINSTANCE;
	}

} //PatternFactoryImpl
