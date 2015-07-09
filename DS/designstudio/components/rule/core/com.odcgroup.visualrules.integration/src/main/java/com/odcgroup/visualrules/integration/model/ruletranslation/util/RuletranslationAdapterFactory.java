/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation.util;

import com.odcgroup.visualrules.integration.model.ruletranslation.*;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage
 * @generated
 */
public class RuletranslationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RuletranslationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuletranslationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RuletranslationPackage.eINSTANCE;
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
	protected RuletranslationSwitch<Adapter> modelSwitch =
		new RuletranslationSwitch<Adapter>() {
			@Override
			public Adapter caseRuleTranslationMap(Map.Entry<String, RuleMessageProxy> object) {
				return createRuleTranslationMapAdapter();
			}
			@Override
			public Adapter caseTranslation(Translation object) {
				return createTranslationAdapter();
			}
			@Override
			public Adapter caseRuleMessageProxy(RuleMessageProxy object) {
				return createRuleMessageProxyAdapter();
			}
			@Override
			public Adapter caseRuleTranslationRepo(RuleTranslationRepo object) {
				return createRuleTranslationRepoAdapter();
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
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Rule Translation Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createRuleTranslationMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.visualrules.integration.model.ruletranslation.Translation <em>Translation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.Translation
	 * @generated
	 */
	public Adapter createTranslationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy <em>Rule Message Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy
	 * @generated
	 */
	public Adapter createRuleMessageProxyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo <em>Rule Translation Repo</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo
	 * @generated
	 */
	public Adapter createRuleTranslationRepoAdapter() {
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

} //RuletranslationAdapterFactory
