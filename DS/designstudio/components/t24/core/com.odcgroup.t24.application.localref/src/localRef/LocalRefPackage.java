/**
 */
package localRef;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see localRef.LocalRefFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='/t24-application-localref/src' modelPluginID='t24-application-localref' modelName='LocalRefApplication' importerID='org.eclipse.xsd.ecore.importer'"
 * @generated
 */
public interface LocalRefPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "localRef";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.odcgroup.com/t24/application/localRef/model/localRef";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "localRef";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  LocalRefPackage eINSTANCE = localRef.impl.LocalRefPackageImpl.init();

  /**
   * The meta object id for the '{@link localRef.impl.LocalFieldImpl <em>Local Field</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see localRef.impl.LocalFieldImpl
   * @see localRef.impl.LocalRefPackageImpl#getLocalField()
   * @generated
   */
  int LOCAL_FIELD = 0;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD__LABEL = 0;

  /**
   * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD__TOOL_TIP = 1;

  /**
   * The feature id for the '<em><b>For Application</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD__FOR_APPLICATION = 2;

  /**
   * The feature id for the '<em><b>Group Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD__GROUP_NAME = 3;

  /**
   * The feature id for the '<em><b>Local Ref Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD__LOCAL_REF_ID = 4;

  /**
   * The number of structural features of the '<em>Local Field</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD_FEATURE_COUNT = 5;

  /**
   * The number of operations of the '<em>Local Field</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_FIELD_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link localRef.impl.LocalReferenceApplicationImpl <em>Local Reference Application</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see localRef.impl.LocalReferenceApplicationImpl
   * @see localRef.impl.LocalRefPackageImpl#getLocalReferenceApplication()
   * @generated
   */
  int LOCAL_REFERENCE_APPLICATION = 1;

  /**
   * The feature id for the '<em><b>Local Field</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD = 0;

  /**
   * The number of structural features of the '<em>Local Reference Application</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_REFERENCE_APPLICATION_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Local Reference Application</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LOCAL_REFERENCE_APPLICATION_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link localRef.LocalField <em>Local Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Local Field</em>'.
   * @see localRef.LocalField
   * @generated
   */
  EClass getLocalField();

  /**
   * Returns the meta object for the attribute '{@link localRef.LocalField#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see localRef.LocalField#getLabel()
   * @see #getLocalField()
   * @generated
   */
  EAttribute getLocalField_Label();

  /**
   * Returns the meta object for the attribute '{@link localRef.LocalField#getToolTip <em>Tool Tip</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tool Tip</em>'.
   * @see localRef.LocalField#getToolTip()
   * @see #getLocalField()
   * @generated
   */
  EAttribute getLocalField_ToolTip();

  /**
   * Returns the meta object for the attribute '{@link localRef.LocalField#getForApplication <em>For Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>For Application</em>'.
   * @see localRef.LocalField#getForApplication()
   * @see #getLocalField()
   * @generated
   */
  EAttribute getLocalField_ForApplication();

  /**
   * Returns the meta object for the attribute '{@link localRef.LocalField#getGroupName <em>Group Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Group Name</em>'.
   * @see localRef.LocalField#getGroupName()
   * @see #getLocalField()
   * @generated
   */
  EAttribute getLocalField_GroupName();

  /**
   * Returns the meta object for the attribute '{@link localRef.LocalField#getLocalRefId <em>Local Ref Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Local Ref Id</em>'.
   * @see localRef.LocalField#getLocalRefId()
   * @see #getLocalField()
   * @generated
   */
  EAttribute getLocalField_LocalRefId();

  /**
   * Returns the meta object for class '{@link localRef.LocalReferenceApplication <em>Local Reference Application</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Local Reference Application</em>'.
   * @see localRef.LocalReferenceApplication
   * @generated
   */
  EClass getLocalReferenceApplication();

  /**
   * Returns the meta object for the attribute list '{@link localRef.LocalReferenceApplication#getLocalField <em>Local Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Local Field</em>'.
   * @see localRef.LocalReferenceApplication#getLocalField()
   * @see #getLocalReferenceApplication()
   * @generated
   */
  EAttribute getLocalReferenceApplication_LocalField();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  LocalRefFactory getLocalRefFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link localRef.impl.LocalFieldImpl <em>Local Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see localRef.impl.LocalFieldImpl
     * @see localRef.impl.LocalRefPackageImpl#getLocalField()
     * @generated
     */
    EClass LOCAL_FIELD = eINSTANCE.getLocalField();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_FIELD__LABEL = eINSTANCE.getLocalField_Label();

    /**
     * The meta object literal for the '<em><b>Tool Tip</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_FIELD__TOOL_TIP = eINSTANCE.getLocalField_ToolTip();

    /**
     * The meta object literal for the '<em><b>For Application</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_FIELD__FOR_APPLICATION = eINSTANCE.getLocalField_ForApplication();

    /**
     * The meta object literal for the '<em><b>Group Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_FIELD__GROUP_NAME = eINSTANCE.getLocalField_GroupName();

    /**
     * The meta object literal for the '<em><b>Local Ref Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_FIELD__LOCAL_REF_ID = eINSTANCE.getLocalField_LocalRefId();

    /**
     * The meta object literal for the '{@link localRef.impl.LocalReferenceApplicationImpl <em>Local Reference Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see localRef.impl.LocalReferenceApplicationImpl
     * @see localRef.impl.LocalRefPackageImpl#getLocalReferenceApplication()
     * @generated
     */
    EClass LOCAL_REFERENCE_APPLICATION = eINSTANCE.getLocalReferenceApplication();

    /**
     * The meta object literal for the '<em><b>Local Field</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD = eINSTANCE.getLocalReferenceApplication_LocalField();

  }

} //LocalRefPackage
