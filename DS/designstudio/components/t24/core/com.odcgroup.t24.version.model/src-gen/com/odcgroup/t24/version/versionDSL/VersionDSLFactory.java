/**
 */
package com.odcgroup.t24.version.versionDSL;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage
 * @generated
 */
public interface VersionDSLFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  VersionDSLFactory eINSTANCE = com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Version</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Version</em>'.
   * @generated
   */
  Version createVersion();

  /**
   * Returns a new object of class '<em>Field</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Field</em>'.
   * @generated
   */
  Field createField();

  /**
   * Returns a new object of class '<em>Default</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Default</em>'.
   * @generated
   */
  Default createDefault();

  /**
   * Returns a new object of class '<em>Routine</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Routine</em>'.
   * @generated
   */
  Routine createRoutine();

  /**
   * Returns a new object of class '<em>At Routine</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>At Routine</em>'.
   * @generated
   */
  AtRoutine createAtRoutine();

  /**
   * Returns a new object of class '<em>Value Or At Routine</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Value Or At Routine</em>'.
   * @generated
   */
  ValueOrAtRoutine createValueOrAtRoutine();

  /**
   * Returns a new object of class '<em>JBC Routine</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>JBC Routine</em>'.
   * @generated
   */
  JBCRoutine createJBCRoutine();

  /**
   * Returns a new object of class '<em>Java Routine</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Java Routine</em>'.
   * @generated
   */
  JavaRoutine createJavaRoutine();

  /**
   * Returns a new object of class '<em>Deal Slip</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Deal Slip</em>'.
   * @generated
   */
  DealSlip createDealSlip();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  VersionDSLPackage getVersionDSLPackage();

} //VersionDSLFactory
