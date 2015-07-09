/**
 */
package com.odcgroup.t24.version.versionDSL.util;

import com.odcgroup.t24.version.versionDSL.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage
 * @generated
 */
public class VersionDSLAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static VersionDSLPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionDSLAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = VersionDSLPackage.eINSTANCE;
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
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
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
  protected VersionDSLSwitch<Adapter> modelSwitch =
    new VersionDSLSwitch<Adapter>()
    {
      @Override
      public Adapter caseVersion(Version object)
      {
        return createVersionAdapter();
      }
      @Override
      public Adapter caseField(Field object)
      {
        return createFieldAdapter();
      }
      @Override
      public Adapter caseDefault(Default object)
      {
        return createDefaultAdapter();
      }
      @Override
      public Adapter caseRoutine(Routine object)
      {
        return createRoutineAdapter();
      }
      @Override
      public Adapter caseAtRoutine(AtRoutine object)
      {
        return createAtRoutineAdapter();
      }
      @Override
      public Adapter caseValueOrAtRoutine(ValueOrAtRoutine object)
      {
        return createValueOrAtRoutineAdapter();
      }
      @Override
      public Adapter caseJBCRoutine(JBCRoutine object)
      {
        return createJBCRoutineAdapter();
      }
      @Override
      public Adapter caseJavaRoutine(JavaRoutine object)
      {
        return createJavaRoutineAdapter();
      }
      @Override
      public Adapter caseDealSlip(DealSlip object)
      {
        return createDealSlipAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
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
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.Version <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.Version
   * @generated
   */
  public Adapter createVersionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.Field <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.Field
   * @generated
   */
  public Adapter createFieldAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.Default <em>Default</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.Default
   * @generated
   */
  public Adapter createDefaultAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.Routine <em>Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.Routine
   * @generated
   */
  public Adapter createRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.AtRoutine <em>At Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.AtRoutine
   * @generated
   */
  public Adapter createAtRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine <em>Value Or At Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine
   * @generated
   */
  public Adapter createValueOrAtRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.JBCRoutine <em>JBC Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.JBCRoutine
   * @generated
   */
  public Adapter createJBCRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.JavaRoutine <em>Java Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.JavaRoutine
   * @generated
   */
  public Adapter createJavaRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.version.versionDSL.DealSlip <em>Deal Slip</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.version.versionDSL.DealSlip
   * @generated
   */
  public Adapter createDealSlipAdapter()
  {
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
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //VersionDSLAdapterFactory
