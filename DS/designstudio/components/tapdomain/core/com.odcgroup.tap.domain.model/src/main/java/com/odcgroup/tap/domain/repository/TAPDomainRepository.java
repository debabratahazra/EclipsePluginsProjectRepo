package com.odcgroup.tap.domain.repository;

import com.odcgroup.domain.repository.DomainRepositoryX;
import com.odcgroup.domain.repository.IDomainRepository;

public class TAPDomainRepository extends DomainRepositoryX implements ITAPDomainRepository {

	public TAPDomainRepository() {
		super(IDomainRepository.DOMAIN_LANGUAGE_NAME);
	}

}
