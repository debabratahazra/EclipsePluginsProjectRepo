package com.odcgroup.tap.mdf.validation;

import com.odcgroup.domain.validation.DomainValidationProvider;
import com.odcgroup.domain.validation.DomainValidator;

public class TAPDomainValidationProvider implements DomainValidationProvider {

	@Override
	public DomainValidator getDomainValidator() {
		return new TAPDomainJavaValidator();
	}



}
