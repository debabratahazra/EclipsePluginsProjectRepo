/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.util;

import com.odcgroup.edge.t24ui.contextEnquiry.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage
 * @generated
 */
public class ContextEnquiryAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ContextEnquiryPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextEnquiryAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ContextEnquiryPackage.eINSTANCE;
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
	protected ContextEnquirySwitch<Adapter> modelSwitch =
		new ContextEnquirySwitch<Adapter>() {
			@Override
			public Adapter caseContextEnquiry(ContextEnquiry object) {
				return createContextEnquiryAdapter();
			}
			@Override
			public Adapter caseDescription(Description object) {
				return createDescriptionAdapter();
			}
			@Override
			public Adapter caseSelectionCriteria(SelectionCriteria object) {
				return createSelectionCriteriaAdapter();
			}
			@Override
			public Adapter caseAutoLaunch(AutoLaunch object) {
				return createAutoLaunchAdapter();
			}
			@Override
			public Adapter caseAppliedEnquiry(AppliedEnquiry object) {
				return createAppliedEnquiryAdapter();
			}
			@Override
			public Adapter caseApplicationContextEnquiry(ApplicationContextEnquiry object) {
				return createApplicationContextEnquiryAdapter();
			}
			@Override
			public Adapter caseVersionContextEnquiry(VersionContextEnquiry object) {
				return createVersionContextEnquiryAdapter();
			}
			@Override
			public Adapter caseFunction(Function object) {
				return createFunctionAdapter();
			}
			@Override
			public Adapter caseFunctionAutoLaunch(FunctionAutoLaunch object) {
				return createFunctionAutoLaunchAdapter();
			}
			@Override
			public Adapter caseOnChangeAutoLaunch(OnChangeAutoLaunch object) {
				return createOnChangeAutoLaunchAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry <em>Context Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiry
	 * @generated
	 */
	public Adapter createContextEnquiryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.Description <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Description
	 * @generated
	 */
	public Adapter createDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria <em>Selection Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria
	 * @generated
	 */
	public Adapter createSelectionCriteriaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch <em>Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AutoLaunch
	 * @generated
	 */
	public Adapter createAutoLaunchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry <em>Applied Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry
	 * @generated
	 */
	public Adapter createAppliedEnquiryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry <em>Application Context Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.ApplicationContextEnquiry
	 * @generated
	 */
	public Adapter createApplicationContextEnquiryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry <em>Version Context Enquiry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry
	 * @generated
	 */
	public Adapter createVersionContextEnquiryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.Function
	 * @generated
	 */
	public Adapter createFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch <em>Function Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.FunctionAutoLaunch
	 * @generated
	 */
	public Adapter createFunctionAutoLaunchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.contextEnquiry.OnChangeAutoLaunch <em>On Change Auto Launch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.contextEnquiry.OnChangeAutoLaunch
	 * @generated
	 */
	public Adapter createOnChangeAutoLaunchAdapter() {
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

} //ContextEnquiryAdapterFactory
