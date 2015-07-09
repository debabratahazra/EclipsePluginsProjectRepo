/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.AlignmentKind;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.EscapeSequence;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.Format;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.ProcessingMode;

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
 * An implementation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getComments <em>Comments</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getDisplayType <em>Display Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getBreakCondition <em>Break Condition</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getAlignment <em>Alignment</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getCommaSeparator <em>Comma Separator</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getNumberOfDecimals <em>Number Of Decimals</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getEscapeSequence <em>Escape Sequence</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getFmtMask <em>Fmt Mask</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getDisplaySection <em>Display Section</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getColumnWidth <em>Column Width</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getSpoolBreak <em>Spool Break</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getSingleMulti <em>Single Multi</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getHidden <em>Hidden</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getNoHeader <em>No Header</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getNoColumnLabel <em>No Column Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getOperation <em>Operation</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getConversion <em>Conversion</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldImpl#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldImpl extends MinimalEObjectImpl.Container implements Field
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
   * The cached value of the '{@link #getLabel() <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected Translations label;

  /**
   * The default value of the '{@link #getComments() <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getComments()
   * @generated
   * @ordered
   */
  protected static final String COMMENTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getComments() <em>Comments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getComments()
   * @generated
   * @ordered
   */
  protected String comments = COMMENTS_EDEFAULT;

  /**
   * The default value of the '{@link #getDisplayType() <em>Display Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplayType()
   * @generated
   * @ordered
   */
  protected static final String DISPLAY_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDisplayType() <em>Display Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplayType()
   * @generated
   * @ordered
   */
  protected String displayType = DISPLAY_TYPE_EDEFAULT;

  /**
   * The cached value of the '{@link #getFormat() <em>Format</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFormat()
   * @generated
   * @ordered
   */
  protected Format format;

  /**
   * The cached value of the '{@link #getBreakCondition() <em>Break Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBreakCondition()
   * @generated
   * @ordered
   */
  protected BreakCondition breakCondition;

  /**
   * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLength()
   * @generated
   * @ordered
   */
  protected static final Integer LENGTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLength()
   * @generated
   * @ordered
   */
  protected Integer length = LENGTH_EDEFAULT;

  /**
   * The default value of the '{@link #getAlignment() <em>Alignment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlignment()
   * @generated
   * @ordered
   */
  protected static final AlignmentKind ALIGNMENT_EDEFAULT = AlignmentKind.UNSPECIFIED;

  /**
   * The cached value of the '{@link #getAlignment() <em>Alignment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlignment()
   * @generated
   * @ordered
   */
  protected AlignmentKind alignment = ALIGNMENT_EDEFAULT;

  /**
   * The default value of the '{@link #getCommaSeparator() <em>Comma Separator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCommaSeparator()
   * @generated
   * @ordered
   */
  protected static final Boolean COMMA_SEPARATOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCommaSeparator() <em>Comma Separator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCommaSeparator()
   * @generated
   * @ordered
   */
  protected Boolean commaSeparator = COMMA_SEPARATOR_EDEFAULT;

  /**
   * The default value of the '{@link #getNumberOfDecimals() <em>Number Of Decimals</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfDecimals()
   * @generated
   * @ordered
   */
  protected static final Integer NUMBER_OF_DECIMALS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNumberOfDecimals() <em>Number Of Decimals</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfDecimals()
   * @generated
   * @ordered
   */
  protected Integer numberOfDecimals = NUMBER_OF_DECIMALS_EDEFAULT;

  /**
   * The default value of the '{@link #getEscapeSequence() <em>Escape Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEscapeSequence()
   * @generated
   * @ordered
   */
  protected static final EscapeSequence ESCAPE_SEQUENCE_EDEFAULT = EscapeSequence.UNSPECIFIED;

  /**
   * The cached value of the '{@link #getEscapeSequence() <em>Escape Sequence</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEscapeSequence()
   * @generated
   * @ordered
   */
  protected EscapeSequence escapeSequence = ESCAPE_SEQUENCE_EDEFAULT;

  /**
   * The default value of the '{@link #getFmtMask() <em>Fmt Mask</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFmtMask()
   * @generated
   * @ordered
   */
  protected static final String FMT_MASK_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFmtMask() <em>Fmt Mask</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFmtMask()
   * @generated
   * @ordered
   */
  protected String fmtMask = FMT_MASK_EDEFAULT;

  /**
   * The default value of the '{@link #getDisplaySection() <em>Display Section</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplaySection()
   * @generated
   * @ordered
   */
  protected static final DisplaySectionKind DISPLAY_SECTION_EDEFAULT = DisplaySectionKind.UNSPECIFIED;

  /**
   * The cached value of the '{@link #getDisplaySection() <em>Display Section</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplaySection()
   * @generated
   * @ordered
   */
  protected DisplaySectionKind displaySection = DISPLAY_SECTION_EDEFAULT;

  /**
   * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPosition()
   * @generated
   * @ordered
   */
  protected FieldPosition position;

  /**
   * The default value of the '{@link #getColumnWidth() <em>Column Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getColumnWidth()
   * @generated
   * @ordered
   */
  protected static final Integer COLUMN_WIDTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getColumnWidth() <em>Column Width</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getColumnWidth()
   * @generated
   * @ordered
   */
  protected Integer columnWidth = COLUMN_WIDTH_EDEFAULT;

  /**
   * The default value of the '{@link #getSpoolBreak() <em>Spool Break</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpoolBreak()
   * @generated
   * @ordered
   */
  protected static final Boolean SPOOL_BREAK_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSpoolBreak() <em>Spool Break</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpoolBreak()
   * @generated
   * @ordered
   */
  protected Boolean spoolBreak = SPOOL_BREAK_EDEFAULT;

  /**
   * The default value of the '{@link #getSingleMulti() <em>Single Multi</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSingleMulti()
   * @generated
   * @ordered
   */
  protected static final ProcessingMode SINGLE_MULTI_EDEFAULT = ProcessingMode.UNSPECIFIED;

  /**
   * The cached value of the '{@link #getSingleMulti() <em>Single Multi</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSingleMulti()
   * @generated
   * @ordered
   */
  protected ProcessingMode singleMulti = SINGLE_MULTI_EDEFAULT;

  /**
   * The default value of the '{@link #getHidden() <em>Hidden</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHidden()
   * @generated
   * @ordered
   */
  protected static final Boolean HIDDEN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getHidden() <em>Hidden</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHidden()
   * @generated
   * @ordered
   */
  protected Boolean hidden = HIDDEN_EDEFAULT;

  /**
   * The default value of the '{@link #getNoHeader() <em>No Header</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNoHeader()
   * @generated
   * @ordered
   */
  protected static final Boolean NO_HEADER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNoHeader() <em>No Header</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNoHeader()
   * @generated
   * @ordered
   */
  protected Boolean noHeader = NO_HEADER_EDEFAULT;

  /**
   * The default value of the '{@link #getNoColumnLabel() <em>No Column Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNoColumnLabel()
   * @generated
   * @ordered
   */
  protected static final Boolean NO_COLUMN_LABEL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNoColumnLabel() <em>No Column Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNoColumnLabel()
   * @generated
   * @ordered
   */
  protected Boolean noColumnLabel = NO_COLUMN_LABEL_EDEFAULT;

  /**
   * The cached value of the '{@link #getOperation() <em>Operation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperation()
   * @generated
   * @ordered
   */
  protected Operation operation;

  /**
   * The cached value of the '{@link #getConversion() <em>Conversion</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConversion()
   * @generated
   * @ordered
   */
  protected EList<Conversion> conversion;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FieldImpl()
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
    return EnquiryPackage.Literals.FIELD;
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getLabel()
  {
    return label;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLabel(Translations newLabel, NotificationChain msgs)
  {
    Translations oldLabel = label;
    label = newLabel;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__LABEL, oldLabel, newLabel);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLabel(Translations newLabel)
  {
    if (newLabel != label)
    {
      NotificationChain msgs = null;
      if (label != null)
        msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__LABEL, null, msgs);
      if (newLabel != null)
        msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__LABEL, null, msgs);
      msgs = basicSetLabel(newLabel, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__LABEL, newLabel, newLabel));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getComments()
  {
    return comments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setComments(String newComments)
  {
    String oldComments = comments;
    comments = newComments;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__COMMENTS, oldComments, comments));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDisplayType()
  {
    return displayType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDisplayType(String newDisplayType)
  {
    String oldDisplayType = displayType;
    displayType = newDisplayType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__DISPLAY_TYPE, oldDisplayType, displayType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Format getFormat()
  {
    return format;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFormat(Format newFormat, NotificationChain msgs)
  {
    Format oldFormat = format;
    format = newFormat;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__FORMAT, oldFormat, newFormat);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFormat(Format newFormat)
  {
    if (newFormat != format)
    {
      NotificationChain msgs = null;
      if (format != null)
        msgs = ((InternalEObject)format).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__FORMAT, null, msgs);
      if (newFormat != null)
        msgs = ((InternalEObject)newFormat).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__FORMAT, null, msgs);
      msgs = basicSetFormat(newFormat, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__FORMAT, newFormat, newFormat));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BreakCondition getBreakCondition()
  {
    return breakCondition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBreakCondition(BreakCondition newBreakCondition, NotificationChain msgs)
  {
    BreakCondition oldBreakCondition = breakCondition;
    breakCondition = newBreakCondition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__BREAK_CONDITION, oldBreakCondition, newBreakCondition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBreakCondition(BreakCondition newBreakCondition)
  {
    if (newBreakCondition != breakCondition)
    {
      NotificationChain msgs = null;
      if (breakCondition != null)
        msgs = ((InternalEObject)breakCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__BREAK_CONDITION, null, msgs);
      if (newBreakCondition != null)
        msgs = ((InternalEObject)newBreakCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__BREAK_CONDITION, null, msgs);
      msgs = basicSetBreakCondition(newBreakCondition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__BREAK_CONDITION, newBreakCondition, newBreakCondition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getLength()
  {
    return length;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLength(Integer newLength)
  {
    Integer oldLength = length;
    length = newLength;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__LENGTH, oldLength, length));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AlignmentKind getAlignment()
  {
    return alignment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAlignment(AlignmentKind newAlignment)
  {
    AlignmentKind oldAlignment = alignment;
    alignment = newAlignment == null ? ALIGNMENT_EDEFAULT : newAlignment;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__ALIGNMENT, oldAlignment, alignment));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getCommaSeparator()
  {
    return commaSeparator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCommaSeparator(Boolean newCommaSeparator)
  {
    Boolean oldCommaSeparator = commaSeparator;
    commaSeparator = newCommaSeparator;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__COMMA_SEPARATOR, oldCommaSeparator, commaSeparator));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getNumberOfDecimals()
  {
    return numberOfDecimals;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumberOfDecimals(Integer newNumberOfDecimals)
  {
    Integer oldNumberOfDecimals = numberOfDecimals;
    numberOfDecimals = newNumberOfDecimals;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__NUMBER_OF_DECIMALS, oldNumberOfDecimals, numberOfDecimals));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EscapeSequence getEscapeSequence()
  {
    return escapeSequence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEscapeSequence(EscapeSequence newEscapeSequence)
  {
    EscapeSequence oldEscapeSequence = escapeSequence;
    escapeSequence = newEscapeSequence == null ? ESCAPE_SEQUENCE_EDEFAULT : newEscapeSequence;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__ESCAPE_SEQUENCE, oldEscapeSequence, escapeSequence));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFmtMask()
  {
    return fmtMask;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFmtMask(String newFmtMask)
  {
    String oldFmtMask = fmtMask;
    fmtMask = newFmtMask;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__FMT_MASK, oldFmtMask, fmtMask));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DisplaySectionKind getDisplaySection()
  {
    return displaySection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDisplaySection(DisplaySectionKind newDisplaySection)
  {
    DisplaySectionKind oldDisplaySection = displaySection;
    displaySection = newDisplaySection == null ? DISPLAY_SECTION_EDEFAULT : newDisplaySection;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__DISPLAY_SECTION, oldDisplaySection, displaySection));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldPosition getPosition()
  {
    return position;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPosition(FieldPosition newPosition, NotificationChain msgs)
  {
    FieldPosition oldPosition = position;
    position = newPosition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__POSITION, oldPosition, newPosition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPosition(FieldPosition newPosition)
  {
    if (newPosition != position)
    {
      NotificationChain msgs = null;
      if (position != null)
        msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__POSITION, null, msgs);
      if (newPosition != null)
        msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__POSITION, null, msgs);
      msgs = basicSetPosition(newPosition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__POSITION, newPosition, newPosition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getColumnWidth()
  {
    return columnWidth;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setColumnWidth(Integer newColumnWidth)
  {
    Integer oldColumnWidth = columnWidth;
    columnWidth = newColumnWidth;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__COLUMN_WIDTH, oldColumnWidth, columnWidth));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getSpoolBreak()
  {
    return spoolBreak;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSpoolBreak(Boolean newSpoolBreak)
  {
    Boolean oldSpoolBreak = spoolBreak;
    spoolBreak = newSpoolBreak;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__SPOOL_BREAK, oldSpoolBreak, spoolBreak));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProcessingMode getSingleMulti()
  {
    return singleMulti;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSingleMulti(ProcessingMode newSingleMulti)
  {
    ProcessingMode oldSingleMulti = singleMulti;
    singleMulti = newSingleMulti == null ? SINGLE_MULTI_EDEFAULT : newSingleMulti;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__SINGLE_MULTI, oldSingleMulti, singleMulti));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getHidden()
  {
    return hidden;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHidden(Boolean newHidden)
  {
    Boolean oldHidden = hidden;
    hidden = newHidden;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__HIDDEN, oldHidden, hidden));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getNoHeader()
  {
    return noHeader;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNoHeader(Boolean newNoHeader)
  {
    Boolean oldNoHeader = noHeader;
    noHeader = newNoHeader;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__NO_HEADER, oldNoHeader, noHeader));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getNoColumnLabel()
  {
    return noColumnLabel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNoColumnLabel(Boolean newNoColumnLabel)
  {
    Boolean oldNoColumnLabel = noColumnLabel;
    noColumnLabel = newNoColumnLabel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__NO_COLUMN_LABEL, oldNoColumnLabel, noColumnLabel));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Operation getOperation()
  {
    return operation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOperation(Operation newOperation, NotificationChain msgs)
  {
    Operation oldOperation = operation;
    operation = newOperation;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__OPERATION, oldOperation, newOperation);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperation(Operation newOperation)
  {
    if (newOperation != operation)
    {
      NotificationChain msgs = null;
      if (operation != null)
        msgs = ((InternalEObject)operation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__OPERATION, null, msgs);
      if (newOperation != null)
        msgs = ((InternalEObject)newOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.FIELD__OPERATION, null, msgs);
      msgs = basicSetOperation(newOperation, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD__OPERATION, newOperation, newOperation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Conversion> getConversion()
  {
    if (conversion == null)
    {
      conversion = new EObjectContainmentEList<Conversion>(Conversion.class, this, EnquiryPackage.FIELD__CONVERSION);
    }
    return conversion;
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
      attributes = new EDataTypeEList<String>(String.class, this, EnquiryPackage.FIELD__ATTRIBUTES);
    }
    return attributes;
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
      case EnquiryPackage.FIELD__LABEL:
        return basicSetLabel(null, msgs);
      case EnquiryPackage.FIELD__FORMAT:
        return basicSetFormat(null, msgs);
      case EnquiryPackage.FIELD__BREAK_CONDITION:
        return basicSetBreakCondition(null, msgs);
      case EnquiryPackage.FIELD__POSITION:
        return basicSetPosition(null, msgs);
      case EnquiryPackage.FIELD__OPERATION:
        return basicSetOperation(null, msgs);
      case EnquiryPackage.FIELD__CONVERSION:
        return ((InternalEList<?>)getConversion()).basicRemove(otherEnd, msgs);
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
      case EnquiryPackage.FIELD__NAME:
        return getName();
      case EnquiryPackage.FIELD__LABEL:
        return getLabel();
      case EnquiryPackage.FIELD__COMMENTS:
        return getComments();
      case EnquiryPackage.FIELD__DISPLAY_TYPE:
        return getDisplayType();
      case EnquiryPackage.FIELD__FORMAT:
        return getFormat();
      case EnquiryPackage.FIELD__BREAK_CONDITION:
        return getBreakCondition();
      case EnquiryPackage.FIELD__LENGTH:
        return getLength();
      case EnquiryPackage.FIELD__ALIGNMENT:
        return getAlignment();
      case EnquiryPackage.FIELD__COMMA_SEPARATOR:
        return getCommaSeparator();
      case EnquiryPackage.FIELD__NUMBER_OF_DECIMALS:
        return getNumberOfDecimals();
      case EnquiryPackage.FIELD__ESCAPE_SEQUENCE:
        return getEscapeSequence();
      case EnquiryPackage.FIELD__FMT_MASK:
        return getFmtMask();
      case EnquiryPackage.FIELD__DISPLAY_SECTION:
        return getDisplaySection();
      case EnquiryPackage.FIELD__POSITION:
        return getPosition();
      case EnquiryPackage.FIELD__COLUMN_WIDTH:
        return getColumnWidth();
      case EnquiryPackage.FIELD__SPOOL_BREAK:
        return getSpoolBreak();
      case EnquiryPackage.FIELD__SINGLE_MULTI:
        return getSingleMulti();
      case EnquiryPackage.FIELD__HIDDEN:
        return getHidden();
      case EnquiryPackage.FIELD__NO_HEADER:
        return getNoHeader();
      case EnquiryPackage.FIELD__NO_COLUMN_LABEL:
        return getNoColumnLabel();
      case EnquiryPackage.FIELD__OPERATION:
        return getOperation();
      case EnquiryPackage.FIELD__CONVERSION:
        return getConversion();
      case EnquiryPackage.FIELD__ATTRIBUTES:
        return getAttributes();
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
      case EnquiryPackage.FIELD__NAME:
        setName((String)newValue);
        return;
      case EnquiryPackage.FIELD__LABEL:
        setLabel((Translations)newValue);
        return;
      case EnquiryPackage.FIELD__COMMENTS:
        setComments((String)newValue);
        return;
      case EnquiryPackage.FIELD__DISPLAY_TYPE:
        setDisplayType((String)newValue);
        return;
      case EnquiryPackage.FIELD__FORMAT:
        setFormat((Format)newValue);
        return;
      case EnquiryPackage.FIELD__BREAK_CONDITION:
        setBreakCondition((BreakCondition)newValue);
        return;
      case EnquiryPackage.FIELD__LENGTH:
        setLength((Integer)newValue);
        return;
      case EnquiryPackage.FIELD__ALIGNMENT:
        setAlignment((AlignmentKind)newValue);
        return;
      case EnquiryPackage.FIELD__COMMA_SEPARATOR:
        setCommaSeparator((Boolean)newValue);
        return;
      case EnquiryPackage.FIELD__NUMBER_OF_DECIMALS:
        setNumberOfDecimals((Integer)newValue);
        return;
      case EnquiryPackage.FIELD__ESCAPE_SEQUENCE:
        setEscapeSequence((EscapeSequence)newValue);
        return;
      case EnquiryPackage.FIELD__FMT_MASK:
        setFmtMask((String)newValue);
        return;
      case EnquiryPackage.FIELD__DISPLAY_SECTION:
        setDisplaySection((DisplaySectionKind)newValue);
        return;
      case EnquiryPackage.FIELD__POSITION:
        setPosition((FieldPosition)newValue);
        return;
      case EnquiryPackage.FIELD__COLUMN_WIDTH:
        setColumnWidth((Integer)newValue);
        return;
      case EnquiryPackage.FIELD__SPOOL_BREAK:
        setSpoolBreak((Boolean)newValue);
        return;
      case EnquiryPackage.FIELD__SINGLE_MULTI:
        setSingleMulti((ProcessingMode)newValue);
        return;
      case EnquiryPackage.FIELD__HIDDEN:
        setHidden((Boolean)newValue);
        return;
      case EnquiryPackage.FIELD__NO_HEADER:
        setNoHeader((Boolean)newValue);
        return;
      case EnquiryPackage.FIELD__NO_COLUMN_LABEL:
        setNoColumnLabel((Boolean)newValue);
        return;
      case EnquiryPackage.FIELD__OPERATION:
        setOperation((Operation)newValue);
        return;
      case EnquiryPackage.FIELD__CONVERSION:
        getConversion().clear();
        getConversion().addAll((Collection<? extends Conversion>)newValue);
        return;
      case EnquiryPackage.FIELD__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends String>)newValue);
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
      case EnquiryPackage.FIELD__NAME:
        setName(NAME_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__LABEL:
        setLabel((Translations)null);
        return;
      case EnquiryPackage.FIELD__COMMENTS:
        setComments(COMMENTS_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__DISPLAY_TYPE:
        setDisplayType(DISPLAY_TYPE_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__FORMAT:
        setFormat((Format)null);
        return;
      case EnquiryPackage.FIELD__BREAK_CONDITION:
        setBreakCondition((BreakCondition)null);
        return;
      case EnquiryPackage.FIELD__LENGTH:
        setLength(LENGTH_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__ALIGNMENT:
        setAlignment(ALIGNMENT_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__COMMA_SEPARATOR:
        setCommaSeparator(COMMA_SEPARATOR_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__NUMBER_OF_DECIMALS:
        setNumberOfDecimals(NUMBER_OF_DECIMALS_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__ESCAPE_SEQUENCE:
        setEscapeSequence(ESCAPE_SEQUENCE_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__FMT_MASK:
        setFmtMask(FMT_MASK_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__DISPLAY_SECTION:
        setDisplaySection(DISPLAY_SECTION_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__POSITION:
        setPosition((FieldPosition)null);
        return;
      case EnquiryPackage.FIELD__COLUMN_WIDTH:
        setColumnWidth(COLUMN_WIDTH_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__SPOOL_BREAK:
        setSpoolBreak(SPOOL_BREAK_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__SINGLE_MULTI:
        setSingleMulti(SINGLE_MULTI_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__HIDDEN:
        setHidden(HIDDEN_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__NO_HEADER:
        setNoHeader(NO_HEADER_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__NO_COLUMN_LABEL:
        setNoColumnLabel(NO_COLUMN_LABEL_EDEFAULT);
        return;
      case EnquiryPackage.FIELD__OPERATION:
        setOperation((Operation)null);
        return;
      case EnquiryPackage.FIELD__CONVERSION:
        getConversion().clear();
        return;
      case EnquiryPackage.FIELD__ATTRIBUTES:
        getAttributes().clear();
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
      case EnquiryPackage.FIELD__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case EnquiryPackage.FIELD__LABEL:
        return label != null;
      case EnquiryPackage.FIELD__COMMENTS:
        return COMMENTS_EDEFAULT == null ? comments != null : !COMMENTS_EDEFAULT.equals(comments);
      case EnquiryPackage.FIELD__DISPLAY_TYPE:
        return DISPLAY_TYPE_EDEFAULT == null ? displayType != null : !DISPLAY_TYPE_EDEFAULT.equals(displayType);
      case EnquiryPackage.FIELD__FORMAT:
        return format != null;
      case EnquiryPackage.FIELD__BREAK_CONDITION:
        return breakCondition != null;
      case EnquiryPackage.FIELD__LENGTH:
        return LENGTH_EDEFAULT == null ? length != null : !LENGTH_EDEFAULT.equals(length);
      case EnquiryPackage.FIELD__ALIGNMENT:
        return alignment != ALIGNMENT_EDEFAULT;
      case EnquiryPackage.FIELD__COMMA_SEPARATOR:
        return COMMA_SEPARATOR_EDEFAULT == null ? commaSeparator != null : !COMMA_SEPARATOR_EDEFAULT.equals(commaSeparator);
      case EnquiryPackage.FIELD__NUMBER_OF_DECIMALS:
        return NUMBER_OF_DECIMALS_EDEFAULT == null ? numberOfDecimals != null : !NUMBER_OF_DECIMALS_EDEFAULT.equals(numberOfDecimals);
      case EnquiryPackage.FIELD__ESCAPE_SEQUENCE:
        return escapeSequence != ESCAPE_SEQUENCE_EDEFAULT;
      case EnquiryPackage.FIELD__FMT_MASK:
        return FMT_MASK_EDEFAULT == null ? fmtMask != null : !FMT_MASK_EDEFAULT.equals(fmtMask);
      case EnquiryPackage.FIELD__DISPLAY_SECTION:
        return displaySection != DISPLAY_SECTION_EDEFAULT;
      case EnquiryPackage.FIELD__POSITION:
        return position != null;
      case EnquiryPackage.FIELD__COLUMN_WIDTH:
        return COLUMN_WIDTH_EDEFAULT == null ? columnWidth != null : !COLUMN_WIDTH_EDEFAULT.equals(columnWidth);
      case EnquiryPackage.FIELD__SPOOL_BREAK:
        return SPOOL_BREAK_EDEFAULT == null ? spoolBreak != null : !SPOOL_BREAK_EDEFAULT.equals(spoolBreak);
      case EnquiryPackage.FIELD__SINGLE_MULTI:
        return singleMulti != SINGLE_MULTI_EDEFAULT;
      case EnquiryPackage.FIELD__HIDDEN:
        return HIDDEN_EDEFAULT == null ? hidden != null : !HIDDEN_EDEFAULT.equals(hidden);
      case EnquiryPackage.FIELD__NO_HEADER:
        return NO_HEADER_EDEFAULT == null ? noHeader != null : !NO_HEADER_EDEFAULT.equals(noHeader);
      case EnquiryPackage.FIELD__NO_COLUMN_LABEL:
        return NO_COLUMN_LABEL_EDEFAULT == null ? noColumnLabel != null : !NO_COLUMN_LABEL_EDEFAULT.equals(noColumnLabel);
      case EnquiryPackage.FIELD__OPERATION:
        return operation != null;
      case EnquiryPackage.FIELD__CONVERSION:
        return conversion != null && !conversion.isEmpty();
      case EnquiryPackage.FIELD__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
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
    result.append(", comments: ");
    result.append(comments);
    result.append(", displayType: ");
    result.append(displayType);
    result.append(", length: ");
    result.append(length);
    result.append(", alignment: ");
    result.append(alignment);
    result.append(", commaSeparator: ");
    result.append(commaSeparator);
    result.append(", numberOfDecimals: ");
    result.append(numberOfDecimals);
    result.append(", escapeSequence: ");
    result.append(escapeSequence);
    result.append(", fmtMask: ");
    result.append(fmtMask);
    result.append(", displaySection: ");
    result.append(displaySection);
    result.append(", columnWidth: ");
    result.append(columnWidth);
    result.append(", spoolBreak: ");
    result.append(spoolBreak);
    result.append(", singleMulti: ");
    result.append(singleMulti);
    result.append(", hidden: ");
    result.append(hidden);
    result.append(", noHeader: ");
    result.append(noHeader);
    result.append(", noColumnLabel: ");
    result.append(noColumnLabel);
    result.append(", attributes: ");
    result.append(attributes);
    result.append(')');
    return result.toString();
  }

} //FieldImpl
