package com.odcgroup.domain.validation;

import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public interface DomainValidator {
	
	/**
	 * @param messageAcceptor
	 * @param element
	 */
	void checkFastMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element);

	/**
	 * @param messageAcceptor
	 * @param domain
	 */
	void checkNormalMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element);

	/**
	 * @param messageAcceptor
	 * @param klass
	 */
	void checkExpensiveMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element);

	/**
	 * @param messageAcceptor
	 * @param element
	 */
	void validateTypeName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element);
	
	/**
	 * @param messageAcceptor
	 * @param element
	 */
	void validateFieldName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element);

	/**
     * @param entity
     * @param currentDomain
     * @return true a cyclic dependency if found
     */
    boolean leadsToCyclicDependency(MdfEntity entity, MdfDomain currentDomain);
	
}
