/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model.impl;

import com.odcgroup.pageflow.model.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionAction;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.pageflow.model.View;
import com.odcgroup.pageflow.model.ViewState;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PageflowFactoryImpl extends EFactoryImpl implements PageflowFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "ODCGROUP";

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PageflowFactory init() {
		try {
			PageflowFactory thePageflowFactory = (PageflowFactory)EPackage.Registry.INSTANCE.getEFactory(PageflowPackage.eNS_URI);
			if (thePageflowFactory != null) {
				return thePageflowFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PageflowFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageflowFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PageflowPackage.END_STATE: return createEndState();
			case PageflowPackage.PAGEFLOW: return createPageflow();
			case PageflowPackage.INIT_STATE: return createInitState();
			case PageflowPackage.PROPERTY: return createProperty();
			case PageflowPackage.TRANSITION: return createTransition();
			case PageflowPackage.VIEW_STATE: return createViewState();
			case PageflowPackage.VIEW: return createView();
			case PageflowPackage.DECISION_STATE: return createDecisionState();
			case PageflowPackage.TRANSITION_ACTION: return createTransitionAction();
			case PageflowPackage.DECISION_ACTION: return createDecisionAction();
			case PageflowPackage.SUB_PAGEFLOW_STATE: return createSubPageflowState();
			case PageflowPackage.TRANSITION_MAPPING: return createTransitionMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PageflowPackage.PROBLEM_MANAGEMENT:
				return createProblemManagementFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PageflowPackage.PROBLEM_MANAGEMENT:
				return convertProblemManagementToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndState createEndState() {
		EndStateImpl endState = new EndStateImpl();
		return endState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pageflow createPageflow() {
		PageflowImpl pageflow = new PageflowImpl();
		return pageflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitState createInitState() {
		InitStateImpl initState = new InitStateImpl();
		return initState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewState createViewState() {
		ViewStateImpl viewState = new ViewStateImpl();
		return viewState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View createView() {
		ViewImpl view = new ViewImpl();
		return view;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionState createDecisionState() {
		DecisionStateImpl decisionState = new DecisionStateImpl();
		return decisionState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionAction createTransitionAction() {
		TransitionActionImpl transitionAction = new TransitionActionImpl();
		return transitionAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionAction createDecisionAction() {
		DecisionActionImpl decisionAction = new DecisionActionImpl();
		return decisionAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubPageflowState createSubPageflowState() {
		SubPageflowStateImpl subPageflowState = new SubPageflowStateImpl();
		return subPageflowState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionMapping createTransitionMapping() {
		TransitionMappingImpl transitionMapping = new TransitionMappingImpl();
		return transitionMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProblemManagement createProblemManagementFromString(EDataType eDataType, String initialValue) {
		ProblemManagement result = ProblemManagement.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProblemManagementToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageflowPackage getPageflowPackage() {
		return (PageflowPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static PageflowPackage getPackage() {
		return PageflowPackage.eINSTANCE;
	}

} //PageflowFactoryImpl
