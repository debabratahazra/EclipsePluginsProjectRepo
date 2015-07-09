/**
 */
package com.odcgroup.service.model.component.impl;

import com.odcgroup.service.model.component.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class ComponentFactoryImpl extends EFactoryImpl implements ComponentFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ComponentFactory init()
  {
    try
    {
      ComponentFactory theComponentFactory = (ComponentFactory)EPackage.Registry.INSTANCE.getEFactory(ComponentPackage.eNS_URI);
      if (theComponentFactory != null)
      {
        return theComponentFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ComponentFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ComponentFactoryImpl()
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
      case ComponentPackage.COMPONENT: return createComponent();
      case ComponentPackage.CONTENT: return createContent();
      case ComponentPackage.TABLE: return createTable();
      case ComponentPackage.CONSTANT: return createConstant();
      case ComponentPackage.PROPERTY: return createProperty();
      case ComponentPackage.METHOD: return createMethod();
      case ComponentPackage.INTERFACE: return createInterface();
      case ComponentPackage.ATTRIBUTE: return createAttribute();
      case ComponentPackage.ARGUMENT: return createArgument();
      case ComponentPackage.METHOD_ANNOTATION: return createMethodAnnotation();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case ComponentPackage.T24_METHOD_STEREOTYPE:
        return createT24MethodStereotypeFromString(eDataType, initialValue);
      case ComponentPackage.READ_WRITE:
        return createReadWriteFromString(eDataType, initialValue);
      case ComponentPackage.ACCESS_SPECIFIER:
        return createAccessSpecifierFromString(eDataType, initialValue);
      case ComponentPackage.IN_OUT_TYPE:
        return createInOutTypeFromString(eDataType, initialValue);
      case ComponentPackage.MULTIPLICITY:
        return createMultiplicityFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case ComponentPackage.T24_METHOD_STEREOTYPE:
        return convertT24MethodStereotypeToString(eDataType, instanceValue);
      case ComponentPackage.READ_WRITE:
        return convertReadWriteToString(eDataType, instanceValue);
      case ComponentPackage.ACCESS_SPECIFIER:
        return convertAccessSpecifierToString(eDataType, instanceValue);
      case ComponentPackage.IN_OUT_TYPE:
        return convertInOutTypeToString(eDataType, instanceValue);
      case ComponentPackage.MULTIPLICITY:
        return convertMultiplicityToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Component createComponent()
  {
    ComponentImpl component = new ComponentImpl();
    return component;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Content createContent()
  {
    ContentImpl content = new ContentImpl();
    return content;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Table createTable()
  {
    TableImpl table = new TableImpl();
    return table;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Constant createConstant()
  {
    ConstantImpl constant = new ConstantImpl();
    return constant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Property createProperty()
  {
    PropertyImpl property = new PropertyImpl();
    return property;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Method createMethod()
  {
    MethodImpl method = new MethodImpl();
    return method;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Interface createInterface()
  {
    InterfaceImpl interface_ = new InterfaceImpl();
    return interface_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Attribute createAttribute()
  {
    AttributeImpl attribute = new AttributeImpl();
    return attribute;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Argument createArgument()
  {
    ArgumentImpl argument = new ArgumentImpl();
    return argument;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MethodAnnotation createMethodAnnotation()
  {
    MethodAnnotationImpl methodAnnotation = new MethodAnnotationImpl();
    return methodAnnotation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public T24MethodStereotype createT24MethodStereotypeFromString(EDataType eDataType, String initialValue)
  {
    T24MethodStereotype result = T24MethodStereotype.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertT24MethodStereotypeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReadWrite createReadWriteFromString(EDataType eDataType, String initialValue)
  {
    ReadWrite result = ReadWrite.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertReadWriteToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccessSpecifier createAccessSpecifierFromString(EDataType eDataType, String initialValue)
  {
    AccessSpecifier result = AccessSpecifier.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertAccessSpecifierToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InOutType createInOutTypeFromString(EDataType eDataType, String initialValue)
  {
    InOutType result = InOutType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertInOutTypeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Multiplicity createMultiplicityFromString(EDataType eDataType, String initialValue)
  {
    Multiplicity result = Multiplicity.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertMultiplicityToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ComponentPackage getComponentPackage()
  {
    return (ComponentPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ComponentPackage getPackage()
  {
    return ComponentPackage.eINSTANCE;
  }

} //ComponentFactoryImpl
