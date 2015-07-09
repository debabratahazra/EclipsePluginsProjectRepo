/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FileVersion;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.Graph;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.Security;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.ServerMode;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.enquiry.WebService;

import com.odcgroup.translation.translationDsl.Translations;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enquiry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getHeader <em>Header</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getServerMode <em>Server Mode</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getEnquiryMode <em>Enquiry Mode</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getCompanies <em>Companies</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getAccountField <em>Account Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getCustomerField <em>Customer Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getZeroRecordsDisplay <em>Zero Records Display</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getNoSelection <em>No Selection</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getShowAllBooks <em>Show All Books</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getEndLine <em>End Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getBuildRoutines <em>Build Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getFixedSelections <em>Fixed Selections</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getFixedSorts <em>Fixed Sorts</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getCustomSelection <em>Custom Selection</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getFields <em>Fields</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getToolbar <em>Toolbar</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getTools <em>Tools</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getDrillDowns <em>Drill Downs</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getSecurity <em>Security</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getGraph <em>Graph</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getWebService <em>Web Service</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getGenerateIFP <em>Generate IFP</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getFileVersion <em>File Version</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryImpl#getIntrospectionMessages <em>Introspection Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnquiryImpl extends MinimalEObjectImpl.Container implements Enquiry
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
  protected static final String FILE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
  protected String fileName = FILE_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetamodelVersion()
   * @generated
   * @ordered
   */
  protected static final String METAMODEL_VERSION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetamodelVersion()
   * @generated
   * @ordered
   */
  protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

  /**
   * The cached value of the '{@link #getHeader() <em>Header</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHeader()
   * @generated
   * @ordered
   */
  protected EList<EnquiryHeader> header;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected Translations description;

  /**
   * The default value of the '{@link #getServerMode() <em>Server Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getServerMode()
   * @generated
   * @ordered
   */
  protected static final ServerMode SERVER_MODE_EDEFAULT = ServerMode.T24;

  /**
   * The cached value of the '{@link #getServerMode() <em>Server Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getServerMode()
   * @generated
   * @ordered
   */
  protected ServerMode serverMode = SERVER_MODE_EDEFAULT;

  /**
   * The default value of the '{@link #getEnquiryMode() <em>Enquiry Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnquiryMode()
   * @generated
   * @ordered
   */
  protected static final EnquiryMode ENQUIRY_MODE_EDEFAULT = EnquiryMode.T24;

  /**
   * The cached value of the '{@link #getEnquiryMode() <em>Enquiry Mode</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnquiryMode()
   * @generated
   * @ordered
   */
  protected EnquiryMode enquiryMode = ENQUIRY_MODE_EDEFAULT;

  /**
   * The cached value of the '{@link #getCompanies() <em>Companies</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompanies()
   * @generated
   * @ordered
   */
  protected Companies companies;

  /**
   * The default value of the '{@link #getAccountField() <em>Account Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountField()
   * @generated
   * @ordered
   */
  protected static final String ACCOUNT_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAccountField() <em>Account Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountField()
   * @generated
   * @ordered
   */
  protected String accountField = ACCOUNT_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getCustomerField() <em>Customer Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCustomerField()
   * @generated
   * @ordered
   */
  protected static final String CUSTOMER_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCustomerField() <em>Customer Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCustomerField()
   * @generated
   * @ordered
   */
  protected String customerField = CUSTOMER_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getZeroRecordsDisplay() <em>Zero Records Display</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getZeroRecordsDisplay()
   * @generated
   * @ordered
   */
  protected static final Boolean ZERO_RECORDS_DISPLAY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getZeroRecordsDisplay() <em>Zero Records Display</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getZeroRecordsDisplay()
   * @generated
   * @ordered
   */
  protected Boolean zeroRecordsDisplay = ZERO_RECORDS_DISPLAY_EDEFAULT;

  /**
   * The default value of the '{@link #getNoSelection() <em>No Selection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNoSelection()
   * @generated
   * @ordered
   */
  protected static final Boolean NO_SELECTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNoSelection() <em>No Selection</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNoSelection()
   * @generated
   * @ordered
   */
  protected Boolean noSelection = NO_SELECTION_EDEFAULT;

  /**
   * The default value of the '{@link #getShowAllBooks() <em>Show All Books</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getShowAllBooks()
   * @generated
   * @ordered
   */
  protected static final Boolean SHOW_ALL_BOOKS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getShowAllBooks() <em>Show All Books</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getShowAllBooks()
   * @generated
   * @ordered
   */
  protected Boolean showAllBooks = SHOW_ALL_BOOKS_EDEFAULT;

  /**
   * The default value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartLine()
   * @generated
   * @ordered
   */
  protected static final Integer START_LINE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartLine()
   * @generated
   * @ordered
   */
  protected Integer startLine = START_LINE_EDEFAULT;

  /**
   * The default value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndLine()
   * @generated
   * @ordered
   */
  protected static final Integer END_LINE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndLine()
   * @generated
   * @ordered
   */
  protected Integer endLine = END_LINE_EDEFAULT;

  /**
   * The cached value of the '{@link #getBuildRoutines() <em>Build Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBuildRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> buildRoutines;

  /**
   * The cached value of the '{@link #getFixedSelections() <em>Fixed Selections</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFixedSelections()
   * @generated
   * @ordered
   */
  protected EList<FixedSelection> fixedSelections;

  /**
   * The cached value of the '{@link #getFixedSorts() <em>Fixed Sorts</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFixedSorts()
   * @generated
   * @ordered
   */
  protected EList<FixedSort> fixedSorts;

  /**
   * The cached value of the '{@link #getCustomSelection() <em>Custom Selection</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCustomSelection()
   * @generated
   * @ordered
   */
  protected SelectionExpression customSelection;

  /**
   * The cached value of the '{@link #getFields() <em>Fields</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFields()
   * @generated
   * @ordered
   */
  protected EList<Field> fields;

  /**
   * The default value of the '{@link #getToolbar() <em>Toolbar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolbar()
   * @generated
   * @ordered
   */
  protected static final String TOOLBAR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToolbar() <em>Toolbar</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolbar()
   * @generated
   * @ordered
   */
  protected String toolbar = TOOLBAR_EDEFAULT;

  /**
   * The cached value of the '{@link #getTools() <em>Tools</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTools()
   * @generated
   * @ordered
   */
  protected EList<Tool> tools;

  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected Target target;

  /**
   * The cached value of the '{@link #getDrillDowns() <em>Drill Downs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDrillDowns()
   * @generated
   * @ordered
   */
  protected EList<DrillDown> drillDowns;

  /**
   * The cached value of the '{@link #getSecurity() <em>Security</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSecurity()
   * @generated
   * @ordered
   */
  protected Security security;

  /**
   * The cached value of the '{@link #getGraph() <em>Graph</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGraph()
   * @generated
   * @ordered
   */
  protected Graph graph;

  /**
   * The cached value of the '{@link #getWebService() <em>Web Service</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebService()
   * @generated
   * @ordered
   */
  protected WebService webService;

  /**
   * The default value of the '{@link #getGenerateIFP() <em>Generate IFP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGenerateIFP()
   * @generated
   * @ordered
   */
  protected static final Boolean GENERATE_IFP_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGenerateIFP() <em>Generate IFP</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGenerateIFP()
   * @generated
   * @ordered
   */
  protected Boolean generateIFP = GENERATE_IFP_EDEFAULT;

  /**
   * The cached value of the '{@link #getFileVersion() <em>File Version</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileVersion()
   * @generated
   * @ordered
   */
  protected EList<FileVersion> fileVersion;

  /**
   * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttributes()
   * @generated
   * @ordered
   */
  protected EList<String> attributes;

  /**
   * The cached value of the '{@link #getIntrospectionMessages() <em>Introspection Messages</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIntrospectionMessages()
   * @generated
   * @ordered
   */
  protected EList<String> introspectionMessages;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnquiryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return EnquiryPackage.Literals.ENQUIRY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFileName()
  {
    return fileName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFileName(String newFileName)
  {
    String oldFileName = fileName;
    fileName = newFileName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__FILE_NAME, oldFileName, fileName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMetamodelVersion()
  {
    return metamodelVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMetamodelVersion(String newMetamodelVersion)
  {
    String oldMetamodelVersion = metamodelVersion;
    metamodelVersion = newMetamodelVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<EnquiryHeader> getHeader()
  {
    if (header == null)
    {
      header = new EObjectContainmentEList<EnquiryHeader>(EnquiryHeader.class, this, EnquiryPackage.ENQUIRY__HEADER);
    }
    return header;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDescription(Translations newDescription, NotificationChain msgs)
  {
    Translations oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__DESCRIPTION, oldDescription, newDescription);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(Translations newDescription)
  {
    if (newDescription != description)
    {
      NotificationChain msgs = null;
      if (description != null)
        msgs = ((InternalEObject)description).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__DESCRIPTION, null, msgs);
      if (newDescription != null)
        msgs = ((InternalEObject)newDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__DESCRIPTION, null, msgs);
      msgs = basicSetDescription(newDescription, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__DESCRIPTION, newDescription, newDescription));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ServerMode getServerMode()
  {
    return serverMode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setServerMode(ServerMode newServerMode)
  {
    ServerMode oldServerMode = serverMode;
    serverMode = newServerMode == null ? SERVER_MODE_EDEFAULT : newServerMode;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__SERVER_MODE, oldServerMode, serverMode));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryMode getEnquiryMode()
  {
    return enquiryMode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnquiryMode(EnquiryMode newEnquiryMode)
  {
    EnquiryMode oldEnquiryMode = enquiryMode;
    enquiryMode = newEnquiryMode == null ? ENQUIRY_MODE_EDEFAULT : newEnquiryMode;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__ENQUIRY_MODE, oldEnquiryMode, enquiryMode));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Companies getCompanies()
  {
    return companies;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCompanies(Companies newCompanies, NotificationChain msgs)
  {
    Companies oldCompanies = companies;
    companies = newCompanies;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__COMPANIES, oldCompanies, newCompanies);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCompanies(Companies newCompanies)
  {
    if (newCompanies != companies)
    {
      NotificationChain msgs = null;
      if (companies != null)
        msgs = ((InternalEObject)companies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__COMPANIES, null, msgs);
      if (newCompanies != null)
        msgs = ((InternalEObject)newCompanies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__COMPANIES, null, msgs);
      msgs = basicSetCompanies(newCompanies, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__COMPANIES, newCompanies, newCompanies));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAccountField()
  {
    return accountField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAccountField(String newAccountField)
  {
    String oldAccountField = accountField;
    accountField = newAccountField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__ACCOUNT_FIELD, oldAccountField, accountField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCustomerField()
  {
    return customerField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCustomerField(String newCustomerField)
  {
    String oldCustomerField = customerField;
    customerField = newCustomerField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__CUSTOMER_FIELD, oldCustomerField, customerField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getZeroRecordsDisplay()
  {
    return zeroRecordsDisplay;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setZeroRecordsDisplay(Boolean newZeroRecordsDisplay)
  {
    Boolean oldZeroRecordsDisplay = zeroRecordsDisplay;
    zeroRecordsDisplay = newZeroRecordsDisplay;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__ZERO_RECORDS_DISPLAY, oldZeroRecordsDisplay, zeroRecordsDisplay));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getNoSelection()
  {
    return noSelection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNoSelection(Boolean newNoSelection)
  {
    Boolean oldNoSelection = noSelection;
    noSelection = newNoSelection;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__NO_SELECTION, oldNoSelection, noSelection));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getShowAllBooks()
  {
    return showAllBooks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setShowAllBooks(Boolean newShowAllBooks)
  {
    Boolean oldShowAllBooks = showAllBooks;
    showAllBooks = newShowAllBooks;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__SHOW_ALL_BOOKS, oldShowAllBooks, showAllBooks));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getStartLine()
  {
    return startLine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStartLine(Integer newStartLine)
  {
    Integer oldStartLine = startLine;
    startLine = newStartLine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__START_LINE, oldStartLine, startLine));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getEndLine()
  {
    return endLine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEndLine(Integer newEndLine)
  {
    Integer oldEndLine = endLine;
    endLine = newEndLine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__END_LINE, oldEndLine, endLine));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getBuildRoutines()
  {
    if (buildRoutines == null)
    {
      buildRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, EnquiryPackage.ENQUIRY__BUILD_ROUTINES);
    }
    return buildRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FixedSelection> getFixedSelections()
  {
    if (fixedSelections == null)
    {
      fixedSelections = new EObjectContainmentEList<FixedSelection>(FixedSelection.class, this, EnquiryPackage.ENQUIRY__FIXED_SELECTIONS);
    }
    return fixedSelections;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FixedSort> getFixedSorts()
  {
    if (fixedSorts == null)
    {
      fixedSorts = new EObjectContainmentEList<FixedSort>(FixedSort.class, this, EnquiryPackage.ENQUIRY__FIXED_SORTS);
    }
    return fixedSorts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SelectionExpression getCustomSelection()
  {
    return customSelection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCustomSelection(SelectionExpression newCustomSelection, NotificationChain msgs)
  {
    SelectionExpression oldCustomSelection = customSelection;
    customSelection = newCustomSelection;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__CUSTOM_SELECTION, oldCustomSelection, newCustomSelection);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCustomSelection(SelectionExpression newCustomSelection)
  {
    if (newCustomSelection != customSelection)
    {
      NotificationChain msgs = null;
      if (customSelection != null)
        msgs = ((InternalEObject)customSelection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__CUSTOM_SELECTION, null, msgs);
      if (newCustomSelection != null)
        msgs = ((InternalEObject)newCustomSelection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__CUSTOM_SELECTION, null, msgs);
      msgs = basicSetCustomSelection(newCustomSelection, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__CUSTOM_SELECTION, newCustomSelection, newCustomSelection));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Field> getFields()
  {
    if (fields == null)
    {
      fields = new EObjectContainmentEList<Field>(Field.class, this, EnquiryPackage.ENQUIRY__FIELDS);
    }
    return fields;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getToolbar()
  {
    return toolbar;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setToolbar(String newToolbar)
  {
    String oldToolbar = toolbar;
    toolbar = newToolbar;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__TOOLBAR, oldToolbar, toolbar));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Tool> getTools()
  {
    if (tools == null)
    {
      tools = new EObjectContainmentEList<Tool>(Tool.class, this, EnquiryPackage.ENQUIRY__TOOLS);
    }
    return tools;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Target getTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTarget(Target newTarget, NotificationChain msgs)
  {
    Target oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__TARGET, oldTarget, newTarget);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(Target newTarget)
  {
    if (newTarget != target)
    {
      NotificationChain msgs = null;
      if (target != null)
        msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__TARGET, null, msgs);
      if (newTarget != null)
        msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__TARGET, null, msgs);
      msgs = basicSetTarget(newTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__TARGET, newTarget, newTarget));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<DrillDown> getDrillDowns()
  {
    if (drillDowns == null)
    {
      drillDowns = new EObjectContainmentEList<DrillDown>(DrillDown.class, this, EnquiryPackage.ENQUIRY__DRILL_DOWNS);
    }
    return drillDowns;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Security getSecurity()
  {
    return security;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSecurity(Security newSecurity, NotificationChain msgs)
  {
    Security oldSecurity = security;
    security = newSecurity;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__SECURITY, oldSecurity, newSecurity);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSecurity(Security newSecurity)
  {
    if (newSecurity != security)
    {
      NotificationChain msgs = null;
      if (security != null)
        msgs = ((InternalEObject)security).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__SECURITY, null, msgs);
      if (newSecurity != null)
        msgs = ((InternalEObject)newSecurity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__SECURITY, null, msgs);
      msgs = basicSetSecurity(newSecurity, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__SECURITY, newSecurity, newSecurity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Graph getGraph()
  {
    return graph;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetGraph(Graph newGraph, NotificationChain msgs)
  {
    Graph oldGraph = graph;
    graph = newGraph;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__GRAPH, oldGraph, newGraph);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGraph(Graph newGraph)
  {
    if (newGraph != graph)
    {
      NotificationChain msgs = null;
      if (graph != null)
        msgs = ((InternalEObject)graph).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__GRAPH, null, msgs);
      if (newGraph != null)
        msgs = ((InternalEObject)newGraph).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__GRAPH, null, msgs);
      msgs = basicSetGraph(newGraph, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__GRAPH, newGraph, newGraph));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WebService getWebService()
  {
    return webService;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWebService(WebService newWebService, NotificationChain msgs)
  {
    WebService oldWebService = webService;
    webService = newWebService;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__WEB_SERVICE, oldWebService, newWebService);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebService(WebService newWebService)
  {
    if (newWebService != webService)
    {
      NotificationChain msgs = null;
      if (webService != null)
        msgs = ((InternalEObject)webService).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__WEB_SERVICE, null, msgs);
      if (newWebService != null)
        msgs = ((InternalEObject)newWebService).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY__WEB_SERVICE, null, msgs);
      msgs = basicSetWebService(newWebService, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__WEB_SERVICE, newWebService, newWebService));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getGenerateIFP()
  {
    return generateIFP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGenerateIFP(Boolean newGenerateIFP)
  {
    Boolean oldGenerateIFP = generateIFP;
    generateIFP = newGenerateIFP;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY__GENERATE_IFP, oldGenerateIFP, generateIFP));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileVersion> getFileVersion()
  {
    if (fileVersion == null)
    {
      fileVersion = new EObjectContainmentEList<FileVersion>(FileVersion.class, this, EnquiryPackage.ENQUIRY__FILE_VERSION);
    }
    return fileVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getAttributes()
  {
    if (attributes == null)
    {
      attributes = new EDataTypeEList<String>(String.class, this, EnquiryPackage.ENQUIRY__ATTRIBUTES);
    }
    return attributes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getIntrospectionMessages()
  {
    if (introspectionMessages == null)
    {
      introspectionMessages = new EDataTypeEList<String>(String.class, this, EnquiryPackage.ENQUIRY__INTROSPECTION_MESSAGES);
    }
    return introspectionMessages;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case EnquiryPackage.ENQUIRY__HEADER:
        return ((InternalEList<?>)getHeader()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__DESCRIPTION:
        return basicSetDescription(null, msgs);
      case EnquiryPackage.ENQUIRY__COMPANIES:
        return basicSetCompanies(null, msgs);
      case EnquiryPackage.ENQUIRY__BUILD_ROUTINES:
        return ((InternalEList<?>)getBuildRoutines()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__FIXED_SELECTIONS:
        return ((InternalEList<?>)getFixedSelections()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__FIXED_SORTS:
        return ((InternalEList<?>)getFixedSorts()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__CUSTOM_SELECTION:
        return basicSetCustomSelection(null, msgs);
      case EnquiryPackage.ENQUIRY__FIELDS:
        return ((InternalEList<?>)getFields()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__TOOLS:
        return ((InternalEList<?>)getTools()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__TARGET:
        return basicSetTarget(null, msgs);
      case EnquiryPackage.ENQUIRY__DRILL_DOWNS:
        return ((InternalEList<?>)getDrillDowns()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.ENQUIRY__SECURITY:
        return basicSetSecurity(null, msgs);
      case EnquiryPackage.ENQUIRY__GRAPH:
        return basicSetGraph(null, msgs);
      case EnquiryPackage.ENQUIRY__WEB_SERVICE:
        return basicSetWebService(null, msgs);
      case EnquiryPackage.ENQUIRY__FILE_VERSION:
        return ((InternalEList<?>)getFileVersion()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case EnquiryPackage.ENQUIRY__NAME:
        return getName();
      case EnquiryPackage.ENQUIRY__FILE_NAME:
        return getFileName();
      case EnquiryPackage.ENQUIRY__METAMODEL_VERSION:
        return getMetamodelVersion();
      case EnquiryPackage.ENQUIRY__HEADER:
        return getHeader();
      case EnquiryPackage.ENQUIRY__DESCRIPTION:
        return getDescription();
      case EnquiryPackage.ENQUIRY__SERVER_MODE:
        return getServerMode();
      case EnquiryPackage.ENQUIRY__ENQUIRY_MODE:
        return getEnquiryMode();
      case EnquiryPackage.ENQUIRY__COMPANIES:
        return getCompanies();
      case EnquiryPackage.ENQUIRY__ACCOUNT_FIELD:
        return getAccountField();
      case EnquiryPackage.ENQUIRY__CUSTOMER_FIELD:
        return getCustomerField();
      case EnquiryPackage.ENQUIRY__ZERO_RECORDS_DISPLAY:
        return getZeroRecordsDisplay();
      case EnquiryPackage.ENQUIRY__NO_SELECTION:
        return getNoSelection();
      case EnquiryPackage.ENQUIRY__SHOW_ALL_BOOKS:
        return getShowAllBooks();
      case EnquiryPackage.ENQUIRY__START_LINE:
        return getStartLine();
      case EnquiryPackage.ENQUIRY__END_LINE:
        return getEndLine();
      case EnquiryPackage.ENQUIRY__BUILD_ROUTINES:
        return getBuildRoutines();
      case EnquiryPackage.ENQUIRY__FIXED_SELECTIONS:
        return getFixedSelections();
      case EnquiryPackage.ENQUIRY__FIXED_SORTS:
        return getFixedSorts();
      case EnquiryPackage.ENQUIRY__CUSTOM_SELECTION:
        return getCustomSelection();
      case EnquiryPackage.ENQUIRY__FIELDS:
        return getFields();
      case EnquiryPackage.ENQUIRY__TOOLBAR:
        return getToolbar();
      case EnquiryPackage.ENQUIRY__TOOLS:
        return getTools();
      case EnquiryPackage.ENQUIRY__TARGET:
        return getTarget();
      case EnquiryPackage.ENQUIRY__DRILL_DOWNS:
        return getDrillDowns();
      case EnquiryPackage.ENQUIRY__SECURITY:
        return getSecurity();
      case EnquiryPackage.ENQUIRY__GRAPH:
        return getGraph();
      case EnquiryPackage.ENQUIRY__WEB_SERVICE:
        return getWebService();
      case EnquiryPackage.ENQUIRY__GENERATE_IFP:
        return getGenerateIFP();
      case EnquiryPackage.ENQUIRY__FILE_VERSION:
        return getFileVersion();
      case EnquiryPackage.ENQUIRY__ATTRIBUTES:
        return getAttributes();
      case EnquiryPackage.ENQUIRY__INTROSPECTION_MESSAGES:
        return getIntrospectionMessages();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EnquiryPackage.ENQUIRY__NAME:
        setName((String)newValue);
        return;
      case EnquiryPackage.ENQUIRY__FILE_NAME:
        setFileName((String)newValue);
        return;
      case EnquiryPackage.ENQUIRY__METAMODEL_VERSION:
        setMetamodelVersion((String)newValue);
        return;
      case EnquiryPackage.ENQUIRY__HEADER:
        getHeader().clear();
        getHeader().addAll((Collection<? extends EnquiryHeader>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__DESCRIPTION:
        setDescription((Translations)newValue);
        return;
      case EnquiryPackage.ENQUIRY__SERVER_MODE:
        setServerMode((ServerMode)newValue);
        return;
      case EnquiryPackage.ENQUIRY__ENQUIRY_MODE:
        setEnquiryMode((EnquiryMode)newValue);
        return;
      case EnquiryPackage.ENQUIRY__COMPANIES:
        setCompanies((Companies)newValue);
        return;
      case EnquiryPackage.ENQUIRY__ACCOUNT_FIELD:
        setAccountField((String)newValue);
        return;
      case EnquiryPackage.ENQUIRY__CUSTOMER_FIELD:
        setCustomerField((String)newValue);
        return;
      case EnquiryPackage.ENQUIRY__ZERO_RECORDS_DISPLAY:
        setZeroRecordsDisplay((Boolean)newValue);
        return;
      case EnquiryPackage.ENQUIRY__NO_SELECTION:
        setNoSelection((Boolean)newValue);
        return;
      case EnquiryPackage.ENQUIRY__SHOW_ALL_BOOKS:
        setShowAllBooks((Boolean)newValue);
        return;
      case EnquiryPackage.ENQUIRY__START_LINE:
        setStartLine((Integer)newValue);
        return;
      case EnquiryPackage.ENQUIRY__END_LINE:
        setEndLine((Integer)newValue);
        return;
      case EnquiryPackage.ENQUIRY__BUILD_ROUTINES:
        getBuildRoutines().clear();
        getBuildRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__FIXED_SELECTIONS:
        getFixedSelections().clear();
        getFixedSelections().addAll((Collection<? extends FixedSelection>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__FIXED_SORTS:
        getFixedSorts().clear();
        getFixedSorts().addAll((Collection<? extends FixedSort>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__CUSTOM_SELECTION:
        setCustomSelection((SelectionExpression)newValue);
        return;
      case EnquiryPackage.ENQUIRY__FIELDS:
        getFields().clear();
        getFields().addAll((Collection<? extends Field>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__TOOLBAR:
        setToolbar((String)newValue);
        return;
      case EnquiryPackage.ENQUIRY__TOOLS:
        getTools().clear();
        getTools().addAll((Collection<? extends Tool>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__TARGET:
        setTarget((Target)newValue);
        return;
      case EnquiryPackage.ENQUIRY__DRILL_DOWNS:
        getDrillDowns().clear();
        getDrillDowns().addAll((Collection<? extends DrillDown>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__SECURITY:
        setSecurity((Security)newValue);
        return;
      case EnquiryPackage.ENQUIRY__GRAPH:
        setGraph((Graph)newValue);
        return;
      case EnquiryPackage.ENQUIRY__WEB_SERVICE:
        setWebService((WebService)newValue);
        return;
      case EnquiryPackage.ENQUIRY__GENERATE_IFP:
        setGenerateIFP((Boolean)newValue);
        return;
      case EnquiryPackage.ENQUIRY__FILE_VERSION:
        getFileVersion().clear();
        getFileVersion().addAll((Collection<? extends FileVersion>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends String>)newValue);
        return;
      case EnquiryPackage.ENQUIRY__INTROSPECTION_MESSAGES:
        getIntrospectionMessages().clear();
        getIntrospectionMessages().addAll((Collection<? extends String>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case EnquiryPackage.ENQUIRY__NAME:
        setName(NAME_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__FILE_NAME:
        setFileName(FILE_NAME_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__METAMODEL_VERSION:
        setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__HEADER:
        getHeader().clear();
        return;
      case EnquiryPackage.ENQUIRY__DESCRIPTION:
        setDescription((Translations)null);
        return;
      case EnquiryPackage.ENQUIRY__SERVER_MODE:
        setServerMode(SERVER_MODE_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__ENQUIRY_MODE:
        setEnquiryMode(ENQUIRY_MODE_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__COMPANIES:
        setCompanies((Companies)null);
        return;
      case EnquiryPackage.ENQUIRY__ACCOUNT_FIELD:
        setAccountField(ACCOUNT_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__CUSTOMER_FIELD:
        setCustomerField(CUSTOMER_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__ZERO_RECORDS_DISPLAY:
        setZeroRecordsDisplay(ZERO_RECORDS_DISPLAY_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__NO_SELECTION:
        setNoSelection(NO_SELECTION_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__SHOW_ALL_BOOKS:
        setShowAllBooks(SHOW_ALL_BOOKS_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__START_LINE:
        setStartLine(START_LINE_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__END_LINE:
        setEndLine(END_LINE_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__BUILD_ROUTINES:
        getBuildRoutines().clear();
        return;
      case EnquiryPackage.ENQUIRY__FIXED_SELECTIONS:
        getFixedSelections().clear();
        return;
      case EnquiryPackage.ENQUIRY__FIXED_SORTS:
        getFixedSorts().clear();
        return;
      case EnquiryPackage.ENQUIRY__CUSTOM_SELECTION:
        setCustomSelection((SelectionExpression)null);
        return;
      case EnquiryPackage.ENQUIRY__FIELDS:
        getFields().clear();
        return;
      case EnquiryPackage.ENQUIRY__TOOLBAR:
        setToolbar(TOOLBAR_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__TOOLS:
        getTools().clear();
        return;
      case EnquiryPackage.ENQUIRY__TARGET:
        setTarget((Target)null);
        return;
      case EnquiryPackage.ENQUIRY__DRILL_DOWNS:
        getDrillDowns().clear();
        return;
      case EnquiryPackage.ENQUIRY__SECURITY:
        setSecurity((Security)null);
        return;
      case EnquiryPackage.ENQUIRY__GRAPH:
        setGraph((Graph)null);
        return;
      case EnquiryPackage.ENQUIRY__WEB_SERVICE:
        setWebService((WebService)null);
        return;
      case EnquiryPackage.ENQUIRY__GENERATE_IFP:
        setGenerateIFP(GENERATE_IFP_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY__FILE_VERSION:
        getFileVersion().clear();
        return;
      case EnquiryPackage.ENQUIRY__ATTRIBUTES:
        getAttributes().clear();
        return;
      case EnquiryPackage.ENQUIRY__INTROSPECTION_MESSAGES:
        getIntrospectionMessages().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case EnquiryPackage.ENQUIRY__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case EnquiryPackage.ENQUIRY__FILE_NAME:
        return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
      case EnquiryPackage.ENQUIRY__METAMODEL_VERSION:
        return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
      case EnquiryPackage.ENQUIRY__HEADER:
        return header != null && !header.isEmpty();
      case EnquiryPackage.ENQUIRY__DESCRIPTION:
        return description != null;
      case EnquiryPackage.ENQUIRY__SERVER_MODE:
        return serverMode != SERVER_MODE_EDEFAULT;
      case EnquiryPackage.ENQUIRY__ENQUIRY_MODE:
        return enquiryMode != ENQUIRY_MODE_EDEFAULT;
      case EnquiryPackage.ENQUIRY__COMPANIES:
        return companies != null;
      case EnquiryPackage.ENQUIRY__ACCOUNT_FIELD:
        return ACCOUNT_FIELD_EDEFAULT == null ? accountField != null : !ACCOUNT_FIELD_EDEFAULT.equals(accountField);
      case EnquiryPackage.ENQUIRY__CUSTOMER_FIELD:
        return CUSTOMER_FIELD_EDEFAULT == null ? customerField != null : !CUSTOMER_FIELD_EDEFAULT.equals(customerField);
      case EnquiryPackage.ENQUIRY__ZERO_RECORDS_DISPLAY:
        return ZERO_RECORDS_DISPLAY_EDEFAULT == null ? zeroRecordsDisplay != null : !ZERO_RECORDS_DISPLAY_EDEFAULT.equals(zeroRecordsDisplay);
      case EnquiryPackage.ENQUIRY__NO_SELECTION:
        return NO_SELECTION_EDEFAULT == null ? noSelection != null : !NO_SELECTION_EDEFAULT.equals(noSelection);
      case EnquiryPackage.ENQUIRY__SHOW_ALL_BOOKS:
        return SHOW_ALL_BOOKS_EDEFAULT == null ? showAllBooks != null : !SHOW_ALL_BOOKS_EDEFAULT.equals(showAllBooks);
      case EnquiryPackage.ENQUIRY__START_LINE:
        return START_LINE_EDEFAULT == null ? startLine != null : !START_LINE_EDEFAULT.equals(startLine);
      case EnquiryPackage.ENQUIRY__END_LINE:
        return END_LINE_EDEFAULT == null ? endLine != null : !END_LINE_EDEFAULT.equals(endLine);
      case EnquiryPackage.ENQUIRY__BUILD_ROUTINES:
        return buildRoutines != null && !buildRoutines.isEmpty();
      case EnquiryPackage.ENQUIRY__FIXED_SELECTIONS:
        return fixedSelections != null && !fixedSelections.isEmpty();
      case EnquiryPackage.ENQUIRY__FIXED_SORTS:
        return fixedSorts != null && !fixedSorts.isEmpty();
      case EnquiryPackage.ENQUIRY__CUSTOM_SELECTION:
        return customSelection != null;
      case EnquiryPackage.ENQUIRY__FIELDS:
        return fields != null && !fields.isEmpty();
      case EnquiryPackage.ENQUIRY__TOOLBAR:
        return TOOLBAR_EDEFAULT == null ? toolbar != null : !TOOLBAR_EDEFAULT.equals(toolbar);
      case EnquiryPackage.ENQUIRY__TOOLS:
        return tools != null && !tools.isEmpty();
      case EnquiryPackage.ENQUIRY__TARGET:
        return target != null;
      case EnquiryPackage.ENQUIRY__DRILL_DOWNS:
        return drillDowns != null && !drillDowns.isEmpty();
      case EnquiryPackage.ENQUIRY__SECURITY:
        return security != null;
      case EnquiryPackage.ENQUIRY__GRAPH:
        return graph != null;
      case EnquiryPackage.ENQUIRY__WEB_SERVICE:
        return webService != null;
      case EnquiryPackage.ENQUIRY__GENERATE_IFP:
        return GENERATE_IFP_EDEFAULT == null ? generateIFP != null : !GENERATE_IFP_EDEFAULT.equals(generateIFP);
      case EnquiryPackage.ENQUIRY__FILE_VERSION:
        return fileVersion != null && !fileVersion.isEmpty();
      case EnquiryPackage.ENQUIRY__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
      case EnquiryPackage.ENQUIRY__INTROSPECTION_MESSAGES:
        return introspectionMessages != null && !introspectionMessages.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", fileName: ");
    result.append(fileName);
    result.append(", metamodelVersion: ");
    result.append(metamodelVersion);
    result.append(", serverMode: ");
    result.append(serverMode);
    result.append(", enquiryMode: ");
    result.append(enquiryMode);
    result.append(", accountField: ");
    result.append(accountField);
    result.append(", customerField: ");
    result.append(customerField);
    result.append(", zeroRecordsDisplay: ");
    result.append(zeroRecordsDisplay);
    result.append(", noSelection: ");
    result.append(noSelection);
    result.append(", showAllBooks: ");
    result.append(showAllBooks);
    result.append(", startLine: ");
    result.append(startLine);
    result.append(", endLine: ");
    result.append(endLine);
    result.append(", toolbar: ");
    result.append(toolbar);
    result.append(", generateIFP: ");
    result.append(generateIFP);
    result.append(", attributes: ");
    result.append(attributes);
    result.append(", introspectionMessages: ");
    result.append(introspectionMessages);
    result.append(')');
    return result.toString();
  }

} //EnquiryImpl
