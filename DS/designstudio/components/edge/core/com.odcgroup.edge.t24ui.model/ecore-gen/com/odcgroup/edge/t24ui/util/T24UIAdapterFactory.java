/**
 */
package com.odcgroup.edge.t24ui.util;

import com.odcgroup.edge.t24ui.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.edge.t24ui.AvailableCOSPatterns;
import com.odcgroup.edge.t24ui.AvailableTranslationLanguages;
import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.T24UIPackage;
import com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.edge.t24ui.T24UIPackage
 * @generated
 */
public class T24UIAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static T24UIPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T24UIAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = T24UIPackage.eINSTANCE;
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
	protected T24UISwitch<Adapter> modelSwitch =
		new T24UISwitch<Adapter>() {
			@Override
			public Adapter caseAvailableCOSPatterns(AvailableCOSPatterns object) {
				return createAvailableCOSPatternsAdapter();
			}
			@Override
			public Adapter caseCompositeScreen(CompositeScreen object) {
				return createCompositeScreenAdapter();
			}
			@Override
			public Adapter caseBespokeCompositeScreen(BespokeCompositeScreen object) {
				return createBespokeCompositeScreenAdapter();
			}
			@Override
			public Adapter caseAvailableTranslationLanguages(AvailableTranslationLanguages object) {
				return createAvailableTranslationLanguagesAdapter();
			}
			@Override
			public Adapter caseCOSPatternContainer(COSPatternContainer object) {
				return createCOSPatternContainerAdapter();
			}
			@Override
			public Adapter caseAbstractCOS(AbstractCOS object) {
				return createAbstractCOSAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.AvailableCOSPatterns <em>Available COS Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.AvailableCOSPatterns
	 * @generated
	 */
	public Adapter createAvailableCOSPatternsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.CompositeScreen <em>Composite Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.CompositeScreen
	 * @generated
	 */
	public Adapter createCompositeScreenAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.BespokeCompositeScreen <em>Bespoke Composite Screen</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.BespokeCompositeScreen
	 * @generated
	 */
	public Adapter createBespokeCompositeScreenAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.odcgroup.edge.t24ui.AvailableTranslationLanguages <em>Available Translation Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.odcgroup.edge.t24ui.AvailableTranslationLanguages
	 * @generated
	 */
	public Adapter createAvailableTranslationLanguagesAdapter() {
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

} //T24UIAdapterFactory
