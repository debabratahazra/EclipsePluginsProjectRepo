/**
 */
package localRef.impl;

import localRef.LocalField;
import localRef.LocalRefFactory;
import localRef.LocalRefPackage;
import localRef.LocalReferenceApplication;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LocalRefPackageImpl extends EPackageImpl implements LocalRefPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localFieldEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localReferenceApplicationEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see localRef.LocalRefPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private LocalRefPackageImpl()
  {
    super(eNS_URI, LocalRefFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link LocalRefPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static LocalRefPackage init()
  {
    if (isInited) return (LocalRefPackage)EPackage.Registry.INSTANCE.getEPackage(LocalRefPackage.eNS_URI);

    // Obtain or create and register package
    LocalRefPackageImpl theLocalRefPackage = (LocalRefPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LocalRefPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LocalRefPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    EcorePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theLocalRefPackage.createPackageContents();

    // Initialize created meta-data
    theLocalRefPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theLocalRefPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(LocalRefPackage.eNS_URI, theLocalRefPackage);
    return theLocalRefPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalField()
  {
    return localFieldEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalField_Label()
  {
    return (EAttribute)localFieldEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalField_ToolTip()
  {
    return (EAttribute)localFieldEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalField_ForApplication()
  {
    return (EAttribute)localFieldEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalField_GroupName()
  {
    return (EAttribute)localFieldEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalField_LocalRefId()
  {
    return (EAttribute)localFieldEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalReferenceApplication()
  {
    return localReferenceApplicationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLocalReferenceApplication_LocalField()
  {
    return (EAttribute)localReferenceApplicationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalRefFactory getLocalRefFactory()
  {
    return (LocalRefFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    localFieldEClass = createEClass(LOCAL_FIELD);
    createEAttribute(localFieldEClass, LOCAL_FIELD__LABEL);
    createEAttribute(localFieldEClass, LOCAL_FIELD__TOOL_TIP);
    createEAttribute(localFieldEClass, LOCAL_FIELD__FOR_APPLICATION);
    createEAttribute(localFieldEClass, LOCAL_FIELD__GROUP_NAME);
    createEAttribute(localFieldEClass, LOCAL_FIELD__LOCAL_REF_ID);

    localReferenceApplicationEClass = createEClass(LOCAL_REFERENCE_APPLICATION);
    createEAttribute(localReferenceApplicationEClass, LOCAL_REFERENCE_APPLICATION__LOCAL_FIELD);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(localFieldEClass, LocalField.class, "LocalField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalField_Label(), theEcorePackage.getEString(), "label", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalField_ToolTip(), theEcorePackage.getEString(), "toolTip", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalField_ForApplication(), theEcorePackage.getEString(), "forApplication", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalField_GroupName(), theEcorePackage.getEString(), "groupName", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLocalField_LocalRefId(), theEcorePackage.getEString(), "localRefID", null, 0, 1, LocalField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(localReferenceApplicationEClass, LocalReferenceApplication.class, "LocalReferenceApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLocalReferenceApplication_LocalField(), theEcorePackage.getEString(), "localField", null, 1, -1, LocalReferenceApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createExtendedMetaDataAnnotations()
  {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			
    addAnnotation
      (localFieldEClass, 
       source, 
       new String[] 
       {
       "name", "localField",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getLocalField_Label(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "label"
       });		
    addAnnotation
      (getLocalField_ToolTip(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "toolTip"
       });		
    addAnnotation
      (getLocalField_ForApplication(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "forApplication"
       });		
    addAnnotation
      (getLocalField_GroupName(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "groupName"
       });		
    addAnnotation
      (getLocalField_LocalRefId(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "localRefID"
       });		
    addAnnotation
      (localReferenceApplicationEClass, 
       source, 
       new String[] 
       {
       "name", "localReferenceApplication",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getLocalReferenceApplication_LocalField(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "localField"
       });
  }

} //LocalRefPackageImpl
