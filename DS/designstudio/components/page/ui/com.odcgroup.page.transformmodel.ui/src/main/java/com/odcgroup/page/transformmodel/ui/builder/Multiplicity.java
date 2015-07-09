package com.odcgroup.page.transformmodel.ui.builder;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.mdf.metamodel.MdfAssociation;

/**
 * A simple class to represent the multiplicity of an association end.
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public class Multiplicity {

	/** Indicates an association by value (dependent object) */
	public static final int CONTAINMENT_BY_VALUE = 0;	
	
	/** are participant required (mandatory) */
	private boolean isRequired = false;
	
	/** participants cardinality */
	private boolean isMany = false;
	
	/** participants containment (true: by value, false:by reference) */
	private boolean byVal = true;
	
	/**
	 * COnstructs a new multiplicity instance
	 * @param association an existing association
	 */
	public Multiplicity(MdfAssociation association) {
		Assert.isNotNull(association);
		byVal = Multiplicity.CONTAINMENT_BY_VALUE == association.getContainment();
		isRequired = association.isRequired();
		isMany = association.getMultiplicity() > 0;
	}
	
	/**
	 * Checks if the containment of the specified association is defined by value or not.
	 * 
	 * @param mdfAssociation the association to check
	 * 
	 * @return <code>true</code> if this association has a containment by value, otherwise it returns <code>false</code>.
	 */
	public static boolean isContainmentByReference(MdfAssociation mdfAssociation) {
		return CONTAINMENT_BY_VALUE != mdfAssociation.getContainment();
	}

	/**
	 * Checks if the containment of the specified association is defined by value or not.
	 * 
	 * @param mdfAssociation the association to check
	 * 
	 * @return <code>true</code> if this association has a containment by value, otherwise it returns <code>false</code>.
	 */
	public static boolean isContainmentByValue(MdfAssociation mdfAssociation) {
		return CONTAINMENT_BY_VALUE == mdfAssociation.getContainment();
	}

	/**
	 * Does the association is by reference ?
	 * 
	 * @return <em>true</em> if the other association end is by reference,
	 *         otherwise it returns <em>false</em>
	 */
	public final boolean isByReference() {
		return !byVal;
	}
	
	/**
	 * Does the association is by value ?
	 * 
	 * @return <em>true</em> if the other association end is by value,
	 *         otherwise it returns <em>false</em>
	 */

	public final boolean isByVal() {
		return byVal;
	}

	/**
	 * @return <em>true</em> if the other association end has a multiplicity
	 *         greater than one otherwise it returns <em>false</em>
	 */
	public final boolean isMany() {
		return isMany;
	}

	/**
	 * @return <em>true</em> if the other association end need a participant
	 *         otherwise it returns <em>false</em>
	 */
	public final boolean isRequired() {
		return isRequired;
	}
}