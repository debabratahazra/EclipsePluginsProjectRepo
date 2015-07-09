/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.ecore.EClass;

import com.odcgroup.mdf.ecore.util.PrimitiveTypeValidator;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primitive</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 */
@SuppressWarnings("serial")
public class MdfPrimitiveImpl extends MdfEntityImpl implements MdfPrimitive {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected MdfPrimitiveImpl() {
        super();
        setName("NewPrimitive");
    }

    /**
     * @see com.odcgroup.mdf.metamodel.MdfEntity#isPrimitiveType()
     */
    public boolean isPrimitiveType() {
        return true;
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_PRIMITIVE;
	}

    public boolean isValidValue(String value) {
        return PrimitiveTypeValidator.isValidValue(this, value);
    }

} //MdfPrimitiveImpl