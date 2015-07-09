/**
 */
package localRef.impl;

import localRef.*;

import org.eclipse.emf.ecore.EClass;
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
public class LocalRefFactoryImpl extends EFactoryImpl implements LocalRefFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static LocalRefFactory init()
  {
    try
    {
      LocalRefFactory theLocalRefFactory = (LocalRefFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/t24/application/localRef/model/localRef"); 
      if (theLocalRefFactory != null)
      {
        return theLocalRefFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new LocalRefFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalRefFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case LocalRefPackage.LOCAL_FIELD: return createLocalField();
      case LocalRefPackage.LOCAL_REFERENCE_APPLICATION: return createLocalReferenceApplication();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalField createLocalField()
  {
    LocalFieldImpl localField = new LocalFieldImpl();
    return localField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalReferenceApplication createLocalReferenceApplication()
  {
    LocalReferenceApplicationImpl localReferenceApplication = new LocalReferenceApplicationImpl();
    return localReferenceApplication;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalRefPackage getLocalRefPackage()
  {
    return (LocalRefPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static LocalRefPackage getPackage()
  {
    return LocalRefPackage.eINSTANCE;
  }

} //LocalRefFactoryImpl
