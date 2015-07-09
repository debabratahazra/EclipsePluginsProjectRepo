/**
 */
package com.odcgroup.t24.application.internal.localref;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.translation.translationDsl.Translations;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getLocalRefID <em>Local Ref ID</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getMaximumChars <em>Maximum Chars</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getMinimumChars <em>Minimum Chars</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getCharType <em>Char Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#isOverridePossible <em>Override Possible</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getNoInputChange <em>No Input Change</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#isDefaultPossible <em>Default Possible</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationEnrich <em>Application Enrich</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getShortName <em>Short Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getVettingTable <em>Vetting Table</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getVirtualTable <em>Virtual Table</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationVET <em>Application VET</em>}</li>
 *   <li>{@link com.odcgroup.t24.application.internal.localref.LocalRef#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef()
 * @model
 * @generated
 */
public interface LocalRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Local Ref ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Ref ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Ref ID</em>' attribute.
	 * @see #setLocalRefID(String)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_LocalRefID()
	 * @model required="true"
	 * @generated
	 */
	String getLocalRefID();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getLocalRefID <em>Local Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Ref ID</em>' attribute.
	 * @see #getLocalRefID()
	 * @generated
	 */
	void setLocalRefID(String value);

	/**
	 * Returns the value of the '<em><b>Maximum Chars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maximum Chars</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maximum Chars</em>' attribute.
	 * @see #setMaximumChars(Integer)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_MaximumChars()
	 * @model required="true"
	 * @generated
	 */
	Integer getMaximumChars();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getMaximumChars <em>Maximum Chars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maximum Chars</em>' attribute.
	 * @see #getMaximumChars()
	 * @generated
	 */
	void setMaximumChars(Integer value);

	/**
	 * Returns the value of the '<em><b>Minimum Chars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minimum Chars</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minimum Chars</em>' attribute.
	 * @see #setMinimumChars(Integer)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_MinimumChars()
	 * @model required="true"
	 * @generated
	 */
	Integer getMinimumChars();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getMinimumChars <em>Minimum Chars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimum Chars</em>' attribute.
	 * @see #getMinimumChars()
	 * @generated
	 */
	void setMinimumChars(Integer value);

	/**
	 * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory</em>' attribute.
	 * @see #setMandatory(boolean)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_Mandatory()
	 * @model default="false"
	 * @generated
	 */
	boolean isMandatory();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#isMandatory <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mandatory</em>' attribute.
	 * @see #isMandatory()
	 * @generated
	 */
	void setMandatory(boolean value);

	/**
	 * Returns the value of the '<em><b>Char Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Char Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Char Type</em>' attribute.
	 * @see #setCharType(String)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_CharType()
	 * @model
	 * @generated
	 */
	String getCharType();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getCharType <em>Char Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Char Type</em>' attribute.
	 * @see #getCharType()
	 * @generated
	 */
	void setCharType(String value);

	/**
	 * Returns the value of the '<em><b>Override Possible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Override Possible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Override Possible</em>' attribute.
	 * @see #setOverridePossible(boolean)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_OverridePossible()
	 * @model
	 * @generated
	 */
	boolean isOverridePossible();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#isOverridePossible <em>Override Possible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Override Possible</em>' attribute.
	 * @see #isOverridePossible()
	 * @generated
	 */
	void setOverridePossible(boolean value);

	/**
	 * Returns the value of the '<em><b>No Input Change</b></em>' attribute.
	 * The literals are from the enumeration {@link com.odcgroup.t24.application.internal.localref.NoInputChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>No Input Change</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>No Input Change</em>' attribute.
	 * @see com.odcgroup.t24.application.internal.localref.NoInputChange
	 * @see #setNoInputChange(NoInputChange)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_NoInputChange()
	 * @model
	 * @generated
	 */
	NoInputChange getNoInputChange();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getNoInputChange <em>No Input Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No Input Change</em>' attribute.
	 * @see com.odcgroup.t24.application.internal.localref.NoInputChange
	 * @see #getNoInputChange()
	 * @generated
	 */
	void setNoInputChange(NoInputChange value);

	/**
	 * Returns the value of the '<em><b>Default Possible</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Possible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Possible</em>' attribute.
	 * @see #setDefaultPossible(boolean)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_DefaultPossible()
	 * @model default="true"
	 * @generated
	 */
	boolean isDefaultPossible();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#isDefaultPossible <em>Default Possible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Possible</em>' attribute.
	 * @see #isDefaultPossible()
	 * @generated
	 */
	void setDefaultPossible(boolean value);

	/**
	 * Returns the value of the '<em><b>Application VET</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application VET</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application VET</em>' reference.
	 * @see #setApplicationVET(MdfClass)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_ApplicationVET()
	 * @model
	 * @generated
	 */
	MdfClass getApplicationVET();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationVET <em>Application VET</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application VET</em>' reference.
	 * @see #getApplicationVET()
	 * @generated
	 */
	void setApplicationVET(MdfClass value);

	/**
	 * Returns the value of the '<em><b>Application Enrich</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Enrich</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Enrich</em>' attribute.
	 * @see #setApplicationEnrich(String)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_ApplicationEnrich()
	 * @model
	 * @generated
	 */
	String getApplicationEnrich();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getApplicationEnrich <em>Application Enrich</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application Enrich</em>' attribute.
	 * @see #getApplicationEnrich()
	 * @generated
	 */
	void setApplicationEnrich(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Vetting Table</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.t24.application.internal.localref.VettingTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vetting Table</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vetting Table</em>' containment reference list.
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_VettingTable()
	 * @model containment="true"
	 * @generated
	 */
	EList<VettingTable> getVettingTable();

	/**
	 * Returns the value of the '<em><b>Short Name</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.translation.translationDsl.Translations}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Short Name</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Short Name</em>' containment reference list.
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_ShortName()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Translations> getShortName();

	/**
	 * Returns the value of the '<em><b>Virtual Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual Table</em>' attribute.
	 * @see #setVirtualTable(String)
	 * @see com.odcgroup.t24.application.internal.localref.LocalrefPackage#getLocalRef_VirtualTable()
	 * @model
	 * @generated
	 */
	String getVirtualTable();

	/**
	 * Sets the value of the '{@link com.odcgroup.t24.application.internal.localref.LocalRef#getVirtualTable <em>Virtual Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual Table</em>' attribute.
	 * @see #getVirtualTable()
	 * @generated
	 */
	void setVirtualTable(String value);

} // LocalRef
