/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.apache.commons.lang.ObjectUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataTypeImpl#getValues <em>Values</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataTypeImpl#getEditorName <em>Editor Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataTypeImpl#getValidatorName <em>Validator Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataTypeImpl#getConverterName <em>Converter Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.DataTypeImpl#isEditable <em>Editable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataTypeImpl extends NamedTypeImpl implements DataType {
	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<DataValue> values;

	/**
	 * The default value of the '{@link #getEditorName() <em>Editor Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDITOR_NAME_EDEFAULT = "com.odcgroup.page.ui.properties.PropertyTextEditor";

	/**
	 * The cached value of the '{@link #getEditorName() <em>Editor Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorName()
	 * @generated
	 * @ordered
	 */
	protected String editorName = EDITOR_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValidatorName() <em>Validator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidatorName()
	 * @generated
	 * @ordered
	 */
	protected static final String VALIDATOR_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidatorName() <em>Validator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidatorName()
	 * @generated
	 * @ordered
	 */
	protected String validatorName = VALIDATOR_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getConverterName() <em>Converter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverterName()
	 * @generated
	 * @ordered
	 */
	protected static final String CONVERTER_NAME_EDEFAULT = "com.odcgroup.page.model.converter.StringValueConverter";

	/**
	 * The cached value of the '{@link #getConverterName() <em>Converter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverterName()
	 * @generated
	 * @ordered
	 */
	protected String converterName = CONVERTER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isEditable() <em>Editable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEditable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EDITABLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEditable() <em>Editable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEditable()
	 * @generated
	 * @ordered
	 */
	protected boolean editable = EDITABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.DATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataValue> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<DataValue>(DataValue.class, this, MetaModelPackage.DATA_TYPE__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditorName() {
		return editorName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newEditorName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorName(String newEditorName) {
		String oldEditorName = editorName;
		editorName = newEditorName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_TYPE__EDITOR_NAME, oldEditorName, editorName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValidatorName() {
		return validatorName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newValidatorName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidatorName(String newValidatorName) {
		String oldValidatorName = validatorName;
		validatorName = newValidatorName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_TYPE__VALIDATOR_NAME, oldValidatorName, validatorName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConverterName() {
		return converterName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newConverterName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConverterName(String newConverterName) {
		String oldConverterName = converterName;
		converterName = newConverterName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_TYPE__CONVERTER_NAME, oldConverterName, converterName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditable(boolean newEditable) {
		boolean oldEditable = editable;
		editable = newEditable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.DATA_TYPE__EDITABLE, oldEditable, editable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.DATA_TYPE__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.DATA_TYPE__VALUES:
				return getValues();
			case MetaModelPackage.DATA_TYPE__EDITOR_NAME:
				return getEditorName();
			case MetaModelPackage.DATA_TYPE__VALIDATOR_NAME:
				return getValidatorName();
			case MetaModelPackage.DATA_TYPE__CONVERTER_NAME:
				return getConverterName();
			case MetaModelPackage.DATA_TYPE__EDITABLE:
				return isEditable();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.DATA_TYPE__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends DataValue>)newValue);
				return;
			case MetaModelPackage.DATA_TYPE__EDITOR_NAME:
				setEditorName((String)newValue);
				return;
			case MetaModelPackage.DATA_TYPE__VALIDATOR_NAME:
				setValidatorName((String)newValue);
				return;
			case MetaModelPackage.DATA_TYPE__CONVERTER_NAME:
				setConverterName((String)newValue);
				return;
			case MetaModelPackage.DATA_TYPE__EDITABLE:
				setEditable((Boolean)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case MetaModelPackage.DATA_TYPE__VALUES:
				getValues().clear();
				return;
			case MetaModelPackage.DATA_TYPE__EDITOR_NAME:
				setEditorName(EDITOR_NAME_EDEFAULT);
				return;
			case MetaModelPackage.DATA_TYPE__VALIDATOR_NAME:
				setValidatorName(VALIDATOR_NAME_EDEFAULT);
				return;
			case MetaModelPackage.DATA_TYPE__CONVERTER_NAME:
				setConverterName(CONVERTER_NAME_EDEFAULT);
				return;
			case MetaModelPackage.DATA_TYPE__EDITABLE:
				setEditable(EDITABLE_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MetaModelPackage.DATA_TYPE__VALUES:
				return values != null && !values.isEmpty();
			case MetaModelPackage.DATA_TYPE__EDITOR_NAME:
				return EDITOR_NAME_EDEFAULT == null ? editorName != null : !EDITOR_NAME_EDEFAULT.equals(editorName);
			case MetaModelPackage.DATA_TYPE__VALIDATOR_NAME:
				return VALIDATOR_NAME_EDEFAULT == null ? validatorName != null : !VALIDATOR_NAME_EDEFAULT.equals(validatorName);
			case MetaModelPackage.DATA_TYPE__CONVERTER_NAME:
				return CONVERTER_NAME_EDEFAULT == null ? converterName != null : !CONVERTER_NAME_EDEFAULT.equals(converterName);
			case MetaModelPackage.DATA_TYPE__EDITABLE:
				return editable != EDITABLE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (editorName: ");
		result.append(editorName);
		result.append(", validatorName: ");
		result.append(validatorName);
		result.append(", converterName: ");
		result.append(converterName);
		result.append(", editable: ");
		result.append(editable);
		result.append(')');
		return result.toString();
	}
	
    /**
     * Finds the DataValue given the value.
     * 
     * @param value The value to find
     * @return DataValue The DataValue or null if no value could be found
     */
    public DataValue findDataValue(String value) {
        for (DataValue dv : getValues()) {
            if (ObjectUtils.equals(value, dv.getValue())) {
                return dv;
            }
        }
        
        // Not found
        return null;
    }

} //DataTypeImpl