package com.odcgroup.t24.mdf.validation;

import com.odcgroup.domain.validation.DomainValidationProvider;
import com.odcgroup.domain.validation.DomainValidator;

public class T24DomainValidationProvider implements DomainValidationProvider {

	@Override
	public DomainValidator getDomainValidator() {
		return new T24DomainJavaValidator();
	}



}
