/**
 */
package com.odcgroup.t24.enquiry.enquiry;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enquiry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileName <em>File Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getHeader <em>Header</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getServerMode <em>Server Mode</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getEnquiryMode <em>Enquiry Mode</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCompanies <em>Companies</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getAccountField <em>Account Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomerField <em>Customer Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getZeroRecordsDisplay <em>Zero Records Display</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getNoSelection <em>No Selection</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getShowAllBooks <em>Show All Books</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getEndLine <em>End Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getBuildRoutines <em>Build Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFixedSelections <em>Fixed Selections</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFixedSorts <em>Fixed Sorts</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomSelection <em>Custom Selection</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFields <em>Fields</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getToolbar <em>Toolbar</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getTools <em>Tools</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getTarget <em>Target</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getDrillDowns <em>Drill Downs</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getSecurity <em>Security</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getGraph <em>Graph</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getWebService <em>Web Service</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getGenerateIFP <em>Generate IFP</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileVersion <em>File Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getIntrospectionMessages <em>Introspection Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry()
 * @model
 * @generated
 */
public interface Enquiry extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Name</em>' attribute.
   * @see #setFileName(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_FileName()
   * @model
   * @generated
   */
  String getFileName();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getFileName <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Name</em>' attribute.
   * @see #getFileName()
   * @generated
   */
  void setFileName(String value);

  /**
   * Returns the value of the '<em><b>Metamodel Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Metamodel Version</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metamodel Version</em>' attribute.
   * @see #setMetamodelVersion(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_MetamodelVersion()
   * @model
   * @generated
   */
  String getMetamodelVersion();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getMetamodelVersion <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metamodel Version</em>' attribute.
   * @see #getMetamodelVersion()
   * @generated
   */
  void setMetamodelVersion(String value);

  /**
   * Returns the value of the '<em><b>Header</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Header</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Header</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Header()
   * @model containment="true"
   * @generated
   */
  EList<EnquiryHeader> getHeader();

  /**
   * Returns the value of the '<em><b>Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' containment reference.
   * @see #setDescription(Translations)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Description()
   * @model containment="true"
   * @generated
   */
  Translations getDescription();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getDescription <em>Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' containment reference.
   * @see #getDescription()
   * @generated
   */
  void setDescription(Translations value);

  /**
   * Returns the value of the '<em><b>Server Mode</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.ServerMode}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Server Mode</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Server Mode</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.ServerMode
   * @see #setServerMode(ServerMode)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_ServerMode()
   * @model
   * @generated
   */
  ServerMode getServerMode();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getServerMode <em>Server Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Server Mode</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.ServerMode
   * @see #getServerMode()
   * @generated
   */
  void setServerMode(ServerMode value);

  /**
   * Returns the value of the '<em><b>Enquiry Mode</b></em>' attribute.
   * The literals are from the enumeration {@link com.odcgroup.t24.enquiry.enquiry.EnquiryMode}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enquiry Mode</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enquiry Mode</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryMode
   * @see #setEnquiryMode(EnquiryMode)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_EnquiryMode()
   * @model
   * @generated
   */
  EnquiryMode getEnquiryMode();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getEnquiryMode <em>Enquiry Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enquiry Mode</em>' attribute.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryMode
   * @see #getEnquiryMode()
   * @generated
   */
  void setEnquiryMode(EnquiryMode value);

  /**
   * Returns the value of the '<em><b>Companies</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Companies</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Companies</em>' containment reference.
   * @see #setCompanies(Companies)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Companies()
   * @model containment="true"
   * @generated
   */
  Companies getCompanies();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCompanies <em>Companies</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Companies</em>' containment reference.
   * @see #getCompanies()
   * @generated
   */
  void setCompanies(Companies value);

  /**
   * Returns the value of the '<em><b>Account Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Field</em>' attribute.
   * @see #setAccountField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_AccountField()
   * @model
   * @generated
   */
  String getAccountField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getAccountField <em>Account Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Field</em>' attribute.
   * @see #getAccountField()
   * @generated
   */
  void setAccountField(String value);

  /**
   * Returns the value of the '<em><b>Customer Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Customer Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Customer Field</em>' attribute.
   * @see #setCustomerField(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_CustomerField()
   * @model
   * @generated
   */
  String getCustomerField();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomerField <em>Customer Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Customer Field</em>' attribute.
   * @see #getCustomerField()
   * @generated
   */
  void setCustomerField(String value);

  /**
   * Returns the value of the '<em><b>Zero Records Display</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Zero Records Display</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Zero Records Display</em>' attribute.
   * @see #setZeroRecordsDisplay(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_ZeroRecordsDisplay()
   * @model
   * @generated
   */
  Boolean getZeroRecordsDisplay();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getZeroRecordsDisplay <em>Zero Records Display</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Zero Records Display</em>' attribute.
   * @see #getZeroRecordsDisplay()
   * @generated
   */
  void setZeroRecordsDisplay(Boolean value);

  /**
   * Returns the value of the '<em><b>No Selection</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>No Selection</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>No Selection</em>' attribute.
   * @see #setNoSelection(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_NoSelection()
   * @model
   * @generated
   */
  Boolean getNoSelection();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getNoSelection <em>No Selection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>No Selection</em>' attribute.
   * @see #getNoSelection()
   * @generated
   */
  void setNoSelection(Boolean value);

  /**
   * Returns the value of the '<em><b>Show All Books</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Show All Books</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Show All Books</em>' attribute.
   * @see #setShowAllBooks(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_ShowAllBooks()
   * @model
   * @generated
   */
  Boolean getShowAllBooks();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getShowAllBooks <em>Show All Books</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Show All Books</em>' attribute.
   * @see #getShowAllBooks()
   * @generated
   */
  void setShowAllBooks(Boolean value);

  /**
   * Returns the value of the '<em><b>Start Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start Line</em>' attribute.
   * @see #setStartLine(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_StartLine()
   * @model
   * @generated
   */
  Integer getStartLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getStartLine <em>Start Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start Line</em>' attribute.
   * @see #getStartLine()
   * @generated
   */
  void setStartLine(Integer value);

  /**
   * Returns the value of the '<em><b>End Line</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>End Line</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>End Line</em>' attribute.
   * @see #setEndLine(Integer)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_EndLine()
   * @model
   * @generated
   */
  Integer getEndLine();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getEndLine <em>End Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>End Line</em>' attribute.
   * @see #getEndLine()
   * @generated
   */
  void setEndLine(Integer value);

  /**
   * Returns the value of the '<em><b>Build Routines</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Routine}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Build Routines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Build Routines</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_BuildRoutines()
   * @model containment="true"
   * @generated
   */
  EList<Routine> getBuildRoutines();

  /**
   * Returns the value of the '<em><b>Fixed Selections</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.FixedSelection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fixed Selections</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fixed Selections</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_FixedSelections()
   * @model containment="true"
   * @generated
   */
  EList<FixedSelection> getFixedSelections();

  /**
   * Returns the value of the '<em><b>Fixed Sorts</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.FixedSort}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fixed Sorts</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fixed Sorts</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_FixedSorts()
   * @model containment="true"
   * @generated
   */
  EList<FixedSort> getFixedSorts();

  /**
   * Returns the value of the '<em><b>Custom Selection</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Custom Selection</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Custom Selection</em>' containment reference.
   * @see #setCustomSelection(SelectionExpression)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_CustomSelection()
   * @model containment="true"
   * @generated
   */
  SelectionExpression getCustomSelection();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getCustomSelection <em>Custom Selection</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Custom Selection</em>' containment reference.
   * @see #getCustomSelection()
   * @generated
   */
  void setCustomSelection(SelectionExpression value);

  /**
   * Returns the value of the '<em><b>Fields</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Field}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fields</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fields</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Fields()
   * @model containment="true"
   * @generated
   */
  EList<Field> getFields();

  /**
   * Returns the value of the '<em><b>Toolbar</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Toolbar</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Toolbar</em>' attribute.
   * @see #setToolbar(String)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Toolbar()
   * @model
   * @generated
   */
  String getToolbar();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getToolbar <em>Toolbar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Toolbar</em>' attribute.
   * @see #getToolbar()
   * @generated
   */
  void setToolbar(String value);

  /**
   * Returns the value of the '<em><b>Tools</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.Tool}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tools</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tools</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Tools()
   * @model containment="true"
   * @generated
   */
  EList<Tool> getTools();

  /**
   * Returns the value of the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' containment reference.
   * @see #setTarget(Target)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Target()
   * @model containment="true"
   * @generated
   */
  Target getTarget();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getTarget <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' containment reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(Target value);

  /**
   * Returns the value of the '<em><b>Drill Downs</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.DrillDown}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Drill Downs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Drill Downs</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_DrillDowns()
   * @model containment="true"
   * @generated
   */
  EList<DrillDown> getDrillDowns();

  /**
   * Returns the value of the '<em><b>Security</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Security</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Security</em>' containment reference.
   * @see #setSecurity(Security)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Security()
   * @model containment="true"
   * @generated
   */
  Security getSecurity();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getSecurity <em>Security</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Security</em>' containment reference.
   * @see #getSecurity()
   * @generated
   */
  void setSecurity(Security value);

  /**
   * Returns the value of the '<em><b>Graph</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Graph</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Graph</em>' containment reference.
   * @see #setGraph(Graph)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Graph()
   * @model containment="true"
   * @generated
   */
  Graph getGraph();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getGraph <em>Graph</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Graph</em>' containment reference.
   * @see #getGraph()
   * @generated
   */
  void setGraph(Graph value);

  /**
   * Returns the value of the '<em><b>Web Service</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Web Service</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Web Service</em>' containment reference.
   * @see #setWebService(WebService)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_WebService()
   * @model containment="true"
   * @generated
   */
  WebService getWebService();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getWebService <em>Web Service</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Web Service</em>' containment reference.
   * @see #getWebService()
   * @generated
   */
  void setWebService(WebService value);

  /**
   * Returns the value of the '<em><b>Generate IFP</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generate IFP</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generate IFP</em>' attribute.
   * @see #setGenerateIFP(Boolean)
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_GenerateIFP()
   * @model
   * @generated
   */
  Boolean getGenerateIFP();

  /**
   * Sets the value of the '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry#getGenerateIFP <em>Generate IFP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Generate IFP</em>' attribute.
   * @see #getGenerateIFP()
   * @generated
   */
  void setGenerateIFP(Boolean value);

  /**
   * Returns the value of the '<em><b>File Version</b></em>' containment reference list.
   * The list contents are of type {@link com.odcgroup.t24.enquiry.enquiry.FileVersion}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File Version</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Version</em>' containment reference list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_FileVersion()
   * @model containment="true"
   * @generated
   */
  EList<FileVersion> getFileVersion();

  /**
   * Returns the value of the '<em><b>Attributes</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attributes</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attributes</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_Attributes()
   * @model unique="false"
   * @generated
   */
  EList<String> getAttributes();

  /**
   * Returns the value of the '<em><b>Introspection Messages</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Introspection Messages</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Introspection Messages</em>' attribute list.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#getEnquiry_IntrospectionMessages()
   * @model unique="false"
   * @generated
   */
  EList<String> getIntrospectionMessages();

} // Enquiry
