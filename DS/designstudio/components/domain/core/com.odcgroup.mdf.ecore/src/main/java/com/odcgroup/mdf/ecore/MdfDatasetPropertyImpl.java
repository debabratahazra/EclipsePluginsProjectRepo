/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.mdf.ecore;

import org.eclipse.emf.ecore.EClass;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Dataset Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 */
@SuppressWarnings("serial")
public abstract class MdfDatasetPropertyImpl extends MdfModelElementImpl implements
        MdfDatasetProperty {

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected MdfDatasetPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	abstract public MdfEntity basicGetType();

    /**
     * @see com.odcgroup.mdf.metamodel.MdfModelElement#getQualifiedName()
     */
    public MdfName getQualifiedName() {
        MdfDataset parent = getParentDataset();

        if (parent != null) {
            MdfName parentqn = parent.getQualifiedName();

            if (parentqn != null) {
                StringBuffer localName = new StringBuffer();
                localName.append(parentqn.getLocalName()).append('#').append(getName());
                return MdfNameFactory.createMdfName(parentqn.getDomain(), localName.toString());
            }
        }

        return null;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->

	 */
    protected EClass eStaticClass() {
		return MdfPackage.Literals.MDF_DATASET_PROPERTY;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->

	 */
	public MdfEntity getType() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public int getMultiplicity() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

    /* (non-Javadoc)
     * @see com.odcgroup.mdf.metamodel.MdfDatasetProperty#getParentDataset()
     */
    public MdfDatasetImpl getParentDataset() {
        if (eContainer() instanceof MdfDataset) {
            return (MdfDatasetImpl) eContainer();
        } else {
            return null;
        }
    }

} // MdfDatasetPropertyImpl
