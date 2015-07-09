/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.util;

import com.odcgroup.pageflow.model.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionAction;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.pageflow.model.View;
import com.odcgroup.pageflow.model.ViewState;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.pageflow.model.PageflowPackage
 * @generated
 */
public class PageflowAdapterFactory extends AdapterFactoryImpl {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PageflowPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageflowAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PageflowPackage.eINSTANCE;
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
	protected PageflowSwitch modelSwitch =
		new PageflowSwitch() {
			public Object caseEndState(EndState object) {
				return createEndStateAdapter();
			}
			public Object casePageflow(Pageflow object) {
				return createPageflowAdapter();
			}
			public Object caseInitState(InitState object) {
				return createInitStateAdapter();
			}
			public Object caseAction(Action object) {
				return createActionAdapter();
			}
			public Object caseProperty(Property object) {
				return createPropertyAdapter();
			}
			public Object caseState(State object) {
				return createStateAdapter();
			}
			public Object caseTransition(Transition object) {
				return createTransitionAdapter();
			}
			public Object caseViewState(ViewState object) {
				return createViewStateAdapter();
			}
			public Object caseView(View object) {
				return createViewAdapter();
			}
			public Object caseDecisionState(DecisionState object) {
				return createDecisionStateAdapter();
			}
			public Object caseTransitionAction(TransitionAction object) {
				return createTransitionActionAdapter();
			}
			public Object caseDecisionAction(DecisionAction object) {
				return createDecisionActionAdapter();
			}
			public Object caseSubPageflowState(SubPageflowState object) {
				return createSubPageflowStateAdapter();
			}
			public Object caseTransitionMapping(TransitionMapping object) {
				return createTransitionMappingAdapter();
			}
			public Object defaultCase(EObject object) {
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
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.EndState <em>End State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.EndState
	 * @generated
	 */
	public Adapter createEndStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.Pageflow <em>Pageflow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.Pageflow
	 * @generated
	 */
	public Adapter createPageflowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.InitState <em>Init State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.InitState
	 * @generated
	 */
	public Adapter createInitStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.Action
	 * @generated
	 */
	public Adapter createActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.State
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.Transition
	 * @generated
	 */
	public Adapter createTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.ViewState <em>View State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.ViewState
	 * @generated
	 */
	public Adapter createViewStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.View
	 * @generated
	 */
	public Adapter createViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.DecisionState <em>Decision State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.DecisionState
	 * @generated
	 */
	public Adapter createDecisionStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.TransitionAction <em>Transition Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.TransitionAction
	 * @generated
	 */
	public Adapter createTransitionActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.DecisionAction <em>Decision Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.DecisionAction
	 * @generated
	 */
	public Adapter createDecisionActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.SubPageflowState <em>Sub Pageflow State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.SubPageflowState
	 * @generated
	 */
	public Adapter createSubPageflowStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.pageflow.model.TransitionMapping <em>Transition Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.pageflow.model.TransitionMapping
	 * @generated
	 */
	public Adapter createTransitionMappingAdapter() {
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

} //PageflowAdapterFactory
