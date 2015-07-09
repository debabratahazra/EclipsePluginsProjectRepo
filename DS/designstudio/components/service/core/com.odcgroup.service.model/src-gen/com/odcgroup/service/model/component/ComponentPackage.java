/**
 */
package com.odcgroup.service.model.component;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.odcgroup.service.model.component.ComponentFactory
 * @model kind="package"
 * @generated
 */
public interface ComponentPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "component";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.odcgroup.com/component/model/Component";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "component";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ComponentPackage eINSTANCE = com.odcgroup.service.model.component.impl.ComponentPackageImpl.init();

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.ComponentImpl <em>Component</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.ComponentImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getComponent()
   * @generated
   */
  int COMPONENT = 0;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPONENT__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPONENT__NAME = 1;

  /**
   * The feature id for the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPONENT__METAMODEL_VERSION = 2;

  /**
   * The feature id for the '<em><b>Content</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPONENT__CONTENT = 3;

  /**
   * The number of structural features of the '<em>Component</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPONENT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.ContentImpl <em>Content</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.ContentImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getContent()
   * @generated
   */
  int CONTENT = 1;

  /**
   * The feature id for the '<em><b>Interface</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTENT__INTERFACE = 0;

  /**
   * The feature id for the '<em><b>Method</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTENT__METHOD = 1;

  /**
   * The feature id for the '<em><b>Property</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTENT__PROPERTY = 2;

  /**
   * The feature id for the '<em><b>Constant</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTENT__CONSTANT = 3;

  /**
   * The feature id for the '<em><b>Table</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTENT__TABLE = 4;

  /**
   * The number of structural features of the '<em>Content</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTENT_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.TableImpl <em>Table</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.TableImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getTable()
   * @generated
   */
  int TABLE = 2;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABLE__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Access Specifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABLE__ACCESS_SPECIFIER = 1;

  /**
   * The feature id for the '<em><b>Table Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABLE__TABLE_NAME = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABLE__TYPE = 3;

  /**
   * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABLE__ATTRIBUTE = 4;

  /**
   * The number of structural features of the '<em>Table</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TABLE_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.ConstantImpl <em>Constant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.ConstantImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getConstant()
   * @generated
   */
  int CONSTANT = 3;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Access Specifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT__ACCESS_SPECIFIER = 1;

  /**
   * The feature id for the '<em><b>Constant Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT__CONSTANT_NAME = 2;

  /**
   * The feature id for the '<em><b>JBC Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT__JBC_NAME = 3;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT__VALUE = 4;

  /**
   * The number of structural features of the '<em>Constant</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONSTANT_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.PropertyImpl <em>Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.PropertyImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getProperty()
   * @generated
   */
  int PROPERTY = 4;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Access Specifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__ACCESS_SPECIFIER = 1;

  /**
   * The feature id for the '<em><b>Read Only</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__READ_ONLY = 2;

  /**
   * The feature id for the '<em><b>Property Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__PROPERTY_NAME = 3;

  /**
   * The feature id for the '<em><b>Type1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__TYPE1 = 4;

  /**
   * The feature id for the '<em><b>Type2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__TYPE2 = 5;

  /**
   * The feature id for the '<em><b>Array</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__ARRAY = 6;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__VALUE = 7;

  /**
   * The number of structural features of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_FEATURE_COUNT = 8;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.MethodImpl <em>Method</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.MethodImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getMethod()
   * @generated
   */
  int METHOD = 5;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Access Specifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD__ACCESS_SPECIFIER = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD__NAME = 2;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD__ANNOTATIONS = 3;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD__ARGUMENTS = 4;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD__TYPE = 5;

  /**
   * The number of structural features of the '<em>Method</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.InterfaceImpl <em>Interface</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.InterfaceImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getInterface()
   * @generated
   */
  int INTERFACE = 6;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERFACE__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Access Specifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERFACE__ACCESS_SPECIFIER = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERFACE__NAME = 2;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERFACE__ARGUMENTS = 3;

  /**
   * The number of structural features of the '<em>Interface</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERFACE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.AttributeImpl <em>Attribute</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.AttributeImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getAttribute()
   * @generated
   */
  int ATTRIBUTE = 7;

  /**
   * The feature id for the '<em><b>Attr Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATTRIBUTE__ATTR_NAME = 0;

  /**
   * The feature id for the '<em><b>JBC Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATTRIBUTE__JBC_NAME = 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATTRIBUTE__VALUE = 2;

  /**
   * The number of structural features of the '<em>Attribute</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATTRIBUTE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.ArgumentImpl <em>Argument</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.ArgumentImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getArgument()
   * @generated
   */
  int ARGUMENT = 8;

  /**
   * The feature id for the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__DOCUMENTATION = 0;

  /**
   * The feature id for the '<em><b>Inout</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__INOUT = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__NAME = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__TYPE = 3;

  /**
   * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT__MULTIPLICITY = 4;

  /**
   * The number of structural features of the '<em>Argument</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARGUMENT_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.impl.MethodAnnotationImpl <em>Method Annotation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.impl.MethodAnnotationImpl
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getMethodAnnotation()
   * @generated
   */
  int METHOD_ANNOTATION = 9;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_ANNOTATION__NAME = 0;

  /**
   * The number of structural features of the '<em>Method Annotation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int METHOD_ANNOTATION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.T24MethodStereotype <em>T24 Method Stereotype</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.T24MethodStereotype
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getT24MethodStereotype()
   * @generated
   */
  int T24_METHOD_STEREOTYPE = 10;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.ReadWrite <em>Read Write</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.ReadWrite
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getReadWrite()
   * @generated
   */
  int READ_WRITE = 11;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.AccessSpecifier <em>Access Specifier</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getAccessSpecifier()
   * @generated
   */
  int ACCESS_SPECIFIER = 12;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.InOutType <em>In Out Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.InOutType
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getInOutType()
   * @generated
   */
  int IN_OUT_TYPE = 13;

  /**
   * The meta object id for the '{@link com.odcgroup.service.model.component.Multiplicity <em>Multiplicity</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.odcgroup.service.model.component.Multiplicity
   * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getMultiplicity()
   * @generated
   */
  int MULTIPLICITY = 14;


  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Component <em>Component</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Component</em>'.
   * @see com.odcgroup.service.model.component.Component
   * @generated
   */
  EClass getComponent();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Component#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Component#getDocumentation()
   * @see #getComponent()
   * @generated
   */
  EAttribute getComponent_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Component#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.service.model.component.Component#getName()
   * @see #getComponent()
   * @generated
   */
  EAttribute getComponent_Name();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Component#getMetamodelVersion <em>Metamodel Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Metamodel Version</em>'.
   * @see com.odcgroup.service.model.component.Component#getMetamodelVersion()
   * @see #getComponent()
   * @generated
   */
  EAttribute getComponent_MetamodelVersion();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Component#getContent <em>Content</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Content</em>'.
   * @see com.odcgroup.service.model.component.Component#getContent()
   * @see #getComponent()
   * @generated
   */
  EReference getComponent_Content();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Content <em>Content</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Content</em>'.
   * @see com.odcgroup.service.model.component.Content
   * @generated
   */
  EClass getContent();

  /**
   * Returns the meta object for the containment reference '{@link com.odcgroup.service.model.component.Content#getInterface <em>Interface</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interface</em>'.
   * @see com.odcgroup.service.model.component.Content#getInterface()
   * @see #getContent()
   * @generated
   */
  EReference getContent_Interface();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Content#getMethod <em>Method</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Method</em>'.
   * @see com.odcgroup.service.model.component.Content#getMethod()
   * @see #getContent()
   * @generated
   */
  EReference getContent_Method();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Content#getProperty <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Property</em>'.
   * @see com.odcgroup.service.model.component.Content#getProperty()
   * @see #getContent()
   * @generated
   */
  EReference getContent_Property();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Content#getConstant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Constant</em>'.
   * @see com.odcgroup.service.model.component.Content#getConstant()
   * @see #getContent()
   * @generated
   */
  EReference getContent_Constant();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Content#getTable <em>Table</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Table</em>'.
   * @see com.odcgroup.service.model.component.Content#getTable()
   * @see #getContent()
   * @generated
   */
  EReference getContent_Table();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Table <em>Table</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Table</em>'.
   * @see com.odcgroup.service.model.component.Table
   * @generated
   */
  EClass getTable();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Table#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Table#getDocumentation()
   * @see #getTable()
   * @generated
   */
  EAttribute getTable_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Table#getAccessSpecifier <em>Access Specifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Access Specifier</em>'.
   * @see com.odcgroup.service.model.component.Table#getAccessSpecifier()
   * @see #getTable()
   * @generated
   */
  EAttribute getTable_AccessSpecifier();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Table#getTableName <em>Table Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Table Name</em>'.
   * @see com.odcgroup.service.model.component.Table#getTableName()
   * @see #getTable()
   * @generated
   */
  EAttribute getTable_TableName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Table#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.odcgroup.service.model.component.Table#getType()
   * @see #getTable()
   * @generated
   */
  EAttribute getTable_Type();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Table#getAttribute <em>Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Attribute</em>'.
   * @see com.odcgroup.service.model.component.Table#getAttribute()
   * @see #getTable()
   * @generated
   */
  EReference getTable_Attribute();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Constant <em>Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Constant</em>'.
   * @see com.odcgroup.service.model.component.Constant
   * @generated
   */
  EClass getConstant();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Constant#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Constant#getDocumentation()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Constant#getAccessSpecifier <em>Access Specifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Access Specifier</em>'.
   * @see com.odcgroup.service.model.component.Constant#getAccessSpecifier()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_AccessSpecifier();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Constant#getConstantName <em>Constant Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Constant Name</em>'.
   * @see com.odcgroup.service.model.component.Constant#getConstantName()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_ConstantName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Constant#getJBCName <em>JBC Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>JBC Name</em>'.
   * @see com.odcgroup.service.model.component.Constant#getJBCName()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_JBCName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Constant#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.service.model.component.Constant#getValue()
   * @see #getConstant()
   * @generated
   */
  EAttribute getConstant_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property</em>'.
   * @see com.odcgroup.service.model.component.Property
   * @generated
   */
  EClass getProperty();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Property#getDocumentation()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getAccessSpecifier <em>Access Specifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Access Specifier</em>'.
   * @see com.odcgroup.service.model.component.Property#getAccessSpecifier()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_AccessSpecifier();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getReadOnly <em>Read Only</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Read Only</em>'.
   * @see com.odcgroup.service.model.component.Property#getReadOnly()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_ReadOnly();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getPropertyName <em>Property Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Property Name</em>'.
   * @see com.odcgroup.service.model.component.Property#getPropertyName()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_PropertyName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getType1 <em>Type1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type1</em>'.
   * @see com.odcgroup.service.model.component.Property#getType1()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Type1();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getType2 <em>Type2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type2</em>'.
   * @see com.odcgroup.service.model.component.Property#getType2()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Type2();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#isArray <em>Array</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Array</em>'.
   * @see com.odcgroup.service.model.component.Property#isArray()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Array();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Property#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.service.model.component.Property#getValue()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Method <em>Method</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method</em>'.
   * @see com.odcgroup.service.model.component.Method
   * @generated
   */
  EClass getMethod();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Method#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Method#getDocumentation()
   * @see #getMethod()
   * @generated
   */
  EAttribute getMethod_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Method#getAccessSpecifier <em>Access Specifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Access Specifier</em>'.
   * @see com.odcgroup.service.model.component.Method#getAccessSpecifier()
   * @see #getMethod()
   * @generated
   */
  EAttribute getMethod_AccessSpecifier();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Method#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.service.model.component.Method#getName()
   * @see #getMethod()
   * @generated
   */
  EAttribute getMethod_Name();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Method#getAnnotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Annotations</em>'.
   * @see com.odcgroup.service.model.component.Method#getAnnotations()
   * @see #getMethod()
   * @generated
   */
  EReference getMethod_Annotations();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Method#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see com.odcgroup.service.model.component.Method#getArguments()
   * @see #getMethod()
   * @generated
   */
  EReference getMethod_Arguments();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Method#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.odcgroup.service.model.component.Method#getType()
   * @see #getMethod()
   * @generated
   */
  EAttribute getMethod_Type();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Interface <em>Interface</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interface</em>'.
   * @see com.odcgroup.service.model.component.Interface
   * @generated
   */
  EClass getInterface();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Interface#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Interface#getDocumentation()
   * @see #getInterface()
   * @generated
   */
  EAttribute getInterface_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Interface#getAccessSpecifier <em>Access Specifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Access Specifier</em>'.
   * @see com.odcgroup.service.model.component.Interface#getAccessSpecifier()
   * @see #getInterface()
   * @generated
   */
  EAttribute getInterface_AccessSpecifier();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Interface#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.service.model.component.Interface#getName()
   * @see #getInterface()
   * @generated
   */
  EAttribute getInterface_Name();

  /**
   * Returns the meta object for the containment reference list '{@link com.odcgroup.service.model.component.Interface#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see com.odcgroup.service.model.component.Interface#getArguments()
   * @see #getInterface()
   * @generated
   */
  EReference getInterface_Arguments();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Attribute <em>Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Attribute</em>'.
   * @see com.odcgroup.service.model.component.Attribute
   * @generated
   */
  EClass getAttribute();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Attribute#getAttrName <em>Attr Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Attr Name</em>'.
   * @see com.odcgroup.service.model.component.Attribute#getAttrName()
   * @see #getAttribute()
   * @generated
   */
  EAttribute getAttribute_AttrName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Attribute#getJBCName <em>JBC Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>JBC Name</em>'.
   * @see com.odcgroup.service.model.component.Attribute#getJBCName()
   * @see #getAttribute()
   * @generated
   */
  EAttribute getAttribute_JBCName();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Attribute#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.odcgroup.service.model.component.Attribute#getValue()
   * @see #getAttribute()
   * @generated
   */
  EAttribute getAttribute_Value();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.Argument <em>Argument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Argument</em>'.
   * @see com.odcgroup.service.model.component.Argument
   * @generated
   */
  EClass getArgument();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Argument#getDocumentation <em>Documentation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Documentation</em>'.
   * @see com.odcgroup.service.model.component.Argument#getDocumentation()
   * @see #getArgument()
   * @generated
   */
  EAttribute getArgument_Documentation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Argument#getInout <em>Inout</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Inout</em>'.
   * @see com.odcgroup.service.model.component.Argument#getInout()
   * @see #getArgument()
   * @generated
   */
  EAttribute getArgument_Inout();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Argument#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.service.model.component.Argument#getName()
   * @see #getArgument()
   * @generated
   */
  EAttribute getArgument_Name();

  /**
   * Returns the meta object for the reference '{@link com.odcgroup.service.model.component.Argument#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Type</em>'.
   * @see com.odcgroup.service.model.component.Argument#getType()
   * @see #getArgument()
   * @generated
   */
  EReference getArgument_Type();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.Argument#getMultiplicity <em>Multiplicity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Multiplicity</em>'.
   * @see com.odcgroup.service.model.component.Argument#getMultiplicity()
   * @see #getArgument()
   * @generated
   */
  EAttribute getArgument_Multiplicity();

  /**
   * Returns the meta object for class '{@link com.odcgroup.service.model.component.MethodAnnotation <em>Method Annotation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Method Annotation</em>'.
   * @see com.odcgroup.service.model.component.MethodAnnotation
   * @generated
   */
  EClass getMethodAnnotation();

  /**
   * Returns the meta object for the attribute '{@link com.odcgroup.service.model.component.MethodAnnotation#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.odcgroup.service.model.component.MethodAnnotation#getName()
   * @see #getMethodAnnotation()
   * @generated
   */
  EAttribute getMethodAnnotation_Name();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.service.model.component.T24MethodStereotype <em>T24 Method Stereotype</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>T24 Method Stereotype</em>'.
   * @see com.odcgroup.service.model.component.T24MethodStereotype
   * @generated
   */
  EEnum getT24MethodStereotype();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.service.model.component.ReadWrite <em>Read Write</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Read Write</em>'.
   * @see com.odcgroup.service.model.component.ReadWrite
   * @generated
   */
  EEnum getReadWrite();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.service.model.component.AccessSpecifier <em>Access Specifier</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Access Specifier</em>'.
   * @see com.odcgroup.service.model.component.AccessSpecifier
   * @generated
   */
  EEnum getAccessSpecifier();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.service.model.component.InOutType <em>In Out Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>In Out Type</em>'.
   * @see com.odcgroup.service.model.component.InOutType
   * @generated
   */
  EEnum getInOutType();

  /**
   * Returns the meta object for enum '{@link com.odcgroup.service.model.component.Multiplicity <em>Multiplicity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Multiplicity</em>'.
   * @see com.odcgroup.service.model.component.Multiplicity
   * @generated
   */
  EEnum getMultiplicity();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ComponentFactory getComponentFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.ComponentImpl <em>Component</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.ComponentImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getComponent()
     * @generated
     */
    EClass COMPONENT = eINSTANCE.getComponent();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPONENT__DOCUMENTATION = eINSTANCE.getComponent_Documentation();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPONENT__NAME = eINSTANCE.getComponent_Name();

    /**
     * The meta object literal for the '<em><b>Metamodel Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPONENT__METAMODEL_VERSION = eINSTANCE.getComponent_MetamodelVersion();

    /**
     * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPONENT__CONTENT = eINSTANCE.getComponent_Content();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.ContentImpl <em>Content</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.ContentImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getContent()
     * @generated
     */
    EClass CONTENT = eINSTANCE.getContent();

    /**
     * The meta object literal for the '<em><b>Interface</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTENT__INTERFACE = eINSTANCE.getContent_Interface();

    /**
     * The meta object literal for the '<em><b>Method</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTENT__METHOD = eINSTANCE.getContent_Method();

    /**
     * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTENT__PROPERTY = eINSTANCE.getContent_Property();

    /**
     * The meta object literal for the '<em><b>Constant</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTENT__CONSTANT = eINSTANCE.getContent_Constant();

    /**
     * The meta object literal for the '<em><b>Table</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTENT__TABLE = eINSTANCE.getContent_Table();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.TableImpl <em>Table</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.TableImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getTable()
     * @generated
     */
    EClass TABLE = eINSTANCE.getTable();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TABLE__DOCUMENTATION = eINSTANCE.getTable_Documentation();

    /**
     * The meta object literal for the '<em><b>Access Specifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TABLE__ACCESS_SPECIFIER = eINSTANCE.getTable_AccessSpecifier();

    /**
     * The meta object literal for the '<em><b>Table Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TABLE__TABLE_NAME = eINSTANCE.getTable_TableName();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TABLE__TYPE = eINSTANCE.getTable_Type();

    /**
     * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TABLE__ATTRIBUTE = eINSTANCE.getTable_Attribute();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.ConstantImpl <em>Constant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.ConstantImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getConstant()
     * @generated
     */
    EClass CONSTANT = eINSTANCE.getConstant();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT__DOCUMENTATION = eINSTANCE.getConstant_Documentation();

    /**
     * The meta object literal for the '<em><b>Access Specifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT__ACCESS_SPECIFIER = eINSTANCE.getConstant_AccessSpecifier();

    /**
     * The meta object literal for the '<em><b>Constant Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT__CONSTANT_NAME = eINSTANCE.getConstant_ConstantName();

    /**
     * The meta object literal for the '<em><b>JBC Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT__JBC_NAME = eINSTANCE.getConstant_JBCName();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONSTANT__VALUE = eINSTANCE.getConstant_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.PropertyImpl <em>Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.PropertyImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getProperty()
     * @generated
     */
    EClass PROPERTY = eINSTANCE.getProperty();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__DOCUMENTATION = eINSTANCE.getProperty_Documentation();

    /**
     * The meta object literal for the '<em><b>Access Specifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__ACCESS_SPECIFIER = eINSTANCE.getProperty_AccessSpecifier();

    /**
     * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__READ_ONLY = eINSTANCE.getProperty_ReadOnly();

    /**
     * The meta object literal for the '<em><b>Property Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__PROPERTY_NAME = eINSTANCE.getProperty_PropertyName();

    /**
     * The meta object literal for the '<em><b>Type1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__TYPE1 = eINSTANCE.getProperty_Type1();

    /**
     * The meta object literal for the '<em><b>Type2</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__TYPE2 = eINSTANCE.getProperty_Type2();

    /**
     * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__ARRAY = eINSTANCE.getProperty_Array();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.MethodImpl <em>Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.MethodImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getMethod()
     * @generated
     */
    EClass METHOD = eINSTANCE.getMethod();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute METHOD__DOCUMENTATION = eINSTANCE.getMethod_Documentation();

    /**
     * The meta object literal for the '<em><b>Access Specifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute METHOD__ACCESS_SPECIFIER = eINSTANCE.getMethod_AccessSpecifier();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute METHOD__NAME = eINSTANCE.getMethod_Name();

    /**
     * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference METHOD__ANNOTATIONS = eINSTANCE.getMethod_Annotations();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference METHOD__ARGUMENTS = eINSTANCE.getMethod_Arguments();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute METHOD__TYPE = eINSTANCE.getMethod_Type();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.InterfaceImpl <em>Interface</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.InterfaceImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getInterface()
     * @generated
     */
    EClass INTERFACE = eINSTANCE.getInterface();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERFACE__DOCUMENTATION = eINSTANCE.getInterface_Documentation();

    /**
     * The meta object literal for the '<em><b>Access Specifier</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERFACE__ACCESS_SPECIFIER = eINSTANCE.getInterface_AccessSpecifier();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERFACE__NAME = eINSTANCE.getInterface_Name();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTERFACE__ARGUMENTS = eINSTANCE.getInterface_Arguments();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.AttributeImpl <em>Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.AttributeImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getAttribute()
     * @generated
     */
    EClass ATTRIBUTE = eINSTANCE.getAttribute();

    /**
     * The meta object literal for the '<em><b>Attr Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ATTRIBUTE__ATTR_NAME = eINSTANCE.getAttribute_AttrName();

    /**
     * The meta object literal for the '<em><b>JBC Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ATTRIBUTE__JBC_NAME = eINSTANCE.getAttribute_JBCName();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.ArgumentImpl <em>Argument</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.ArgumentImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getArgument()
     * @generated
     */
    EClass ARGUMENT = eINSTANCE.getArgument();

    /**
     * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARGUMENT__DOCUMENTATION = eINSTANCE.getArgument_Documentation();

    /**
     * The meta object literal for the '<em><b>Inout</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARGUMENT__INOUT = eINSTANCE.getArgument_Inout();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARGUMENT__NAME = eINSTANCE.getArgument_Name();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARGUMENT__TYPE = eINSTANCE.getArgument_Type();

    /**
     * The meta object literal for the '<em><b>Multiplicity</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARGUMENT__MULTIPLICITY = eINSTANCE.getArgument_Multiplicity();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.impl.MethodAnnotationImpl <em>Method Annotation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.impl.MethodAnnotationImpl
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getMethodAnnotation()
     * @generated
     */
    EClass METHOD_ANNOTATION = eINSTANCE.getMethodAnnotation();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute METHOD_ANNOTATION__NAME = eINSTANCE.getMethodAnnotation_Name();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.T24MethodStereotype <em>T24 Method Stereotype</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.T24MethodStereotype
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getT24MethodStereotype()
     * @generated
     */
    EEnum T24_METHOD_STEREOTYPE = eINSTANCE.getT24MethodStereotype();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.ReadWrite <em>Read Write</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.ReadWrite
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getReadWrite()
     * @generated
     */
    EEnum READ_WRITE = eINSTANCE.getReadWrite();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.AccessSpecifier <em>Access Specifier</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.AccessSpecifier
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getAccessSpecifier()
     * @generated
     */
    EEnum ACCESS_SPECIFIER = eINSTANCE.getAccessSpecifier();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.InOutType <em>In Out Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.InOutType
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getInOutType()
     * @generated
     */
    EEnum IN_OUT_TYPE = eINSTANCE.getInOutType();

    /**
     * The meta object literal for the '{@link com.odcgroup.service.model.component.Multiplicity <em>Multiplicity</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.odcgroup.service.model.component.Multiplicity
     * @see com.odcgroup.service.model.component.impl.ComponentPackageImpl#getMultiplicity()
     * @generated
     */
    EEnum MULTIPLICITY = eINSTANCE.getMultiplicity();

  }

} //ComponentPackage
