/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.util;

import com.odcgroup.edge.t24ui.cos.pattern.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage
 * @generated
 */
public class PatternAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PatternPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatternAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PatternPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternSwitch<Adapter> modelSwitch =
		new PatternSwitch<Adapter>() {
			@Override
			public Adapter caseCOSPanel(COSPanel object) {
				return createCOSPanelAdapter();
			}
			@Override
			public Adapter caseSingleComponentPanel(SingleComponentPanel object) {
				return createSingleComponentPanelAdapter();
			}
			@Override
			public Adapter caseMultiComponentPanel(MultiComponentPanel object) {
				return createMultiComponentPanelAdapter();
			}
			@Override
			public Adapter caseInitialPanelContentSpec(InitialPanelContentSpec object) {
				return createInitialPanelContentSpecAdapter();
			}
			@Override
			public Adapter caseInitialCOS(InitialCOS object) {
				return createInitialCOSAdapter();
			}
			@Override
			public Adapter caseInlineInitialCOS(InlineInitialCOS object) {
				return createInlineInitialCOSAdapter();
			}
			@Override
			public Adapter caseInitialBespokeCOS(InitialBespokeCOS object) {
				return createInitialBespokeCOSAdapter();
			}
			@Override
			public Adapter caseInitialEnquiry(InitialEnquiry object) {
				return createInitialEnquiryAdapter();
			}
			@Override
			public Adapter caseInitialScreen(InitialScreen object) {
				return createInitialScreenAdapter();
			}
			@Override
			public Adapter caseAbstractCOS(AbstractCOS object) {
				return createAbstractCOSAdapter();
			}
			@Override
			public Adapter caseCOSPatternContainer(COSPatternContainer object) {
				return createCOSPatternContainerAdapter();
			}
			@Override
			public Adapter caseCOSPattern(COSPattern object) {
				return createCOSPatternAdapter();
			}
			@Override
			public Adapter caseChildResourceSpec(ChildResourceSpec object) {
				return createChildResourceSpecAdapter();
			}
			@Override
			public Adapter caseInitialURL(InitialURL object) {
				return createInitialURLAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPanel <em>COS Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPanel
	 * @generated
	 */
	public Adapter createCOSPanelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel <em>Single Component Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel
	 * @generated
	 */
	public Adapter createSingleComponentPanelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel <em>Multi Component Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel
	 * @generated
	 */
	public Adapter createMultiComponentPanelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec <em>Initial Panel Content Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec
	 * @generated
	 */
	public Adapter createInitialPanelContentSpecAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialCOS <em>Initial COS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialCOS
	 * @generated
	 */
	public Adapter createInitialCOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InlineInitialCOS <em>Inline Initial COS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InlineInitialCOS
	 * @generated
	 */
	public Adapter createInlineInitialCOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS <em>Initial Bespoke COS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialBespokeCOS
	 * @generated
	 */
	public Adapter createInitialBespokeCOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry <em>Initial Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry
	 * @generated
	 */
	public Adapter createInitialEnquiryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialScreen <em>Initial Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialScreen
	 * @generated
	 */
	public Adapter createInitialScreenAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPattern <em>COS Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPattern
	 * @generated
	 */
	public Adapter createCOSPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec <em>Child Resource Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec
	 * @generated
	 */
	public Adapter createChildResourceSpecAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialURL <em>Initial URL</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.InitialURL
	 * @generated
	 */
    public Adapter createInitialURLAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS <em>Abstract COS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS
	 * @generated
	 */
	public Adapter createAbstractCOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer <em>COS Pattern Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer
	 * @generated
	 */
	public Adapter createCOSPatternContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PatternAdapterFactory
