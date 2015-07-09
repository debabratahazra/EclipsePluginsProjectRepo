package com.odcgroup.domain.validation;

import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class DefaultDomainValidator implements DomainValidator {
	
	public DefaultDomainValidator() {}
	
	@Override
	public void checkFastMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}
	
	@Override
	public void checkNormalMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

	@Override
	public void checkExpensiveMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

	@Override
	public boolean leadsToCyclicDependency(MdfEntity entity, MdfDomain currentDomain) {
		return false;
	}

	@Override
	public void validateTypeName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

	@Override
	public void validateFieldName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

}
