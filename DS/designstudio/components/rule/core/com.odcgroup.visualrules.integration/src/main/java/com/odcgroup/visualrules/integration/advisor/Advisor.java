package com.odcgroup.visualrules.integration.advisor;

import java.util.ArrayList;
import java.util.Collection;

import de.visualrules.model.provider.advisor.IAdvisor;
import de.visualrules.model.provider.advisor.Restriction;

public class Advisor implements IAdvisor {

	public Collection<Restriction> getRestrictions() {
		Collection<Restriction> restrictions = new ArrayList<Restriction>();
//		restrictions.add(Restriction.ACTION);
		restrictions.add(Restriction.ACTION_TYPE);
		restrictions.add(Restriction.ENVIRONMENT_CONFIGURATION);
		restrictions.add(Restriction.FUNCTION);
		restrictions.add(Restriction.SERVICE);
		restrictions.add(Restriction.SERVICE_TYPE);
//		restrictions.add(Restriction.DATA_TYPE);
		restrictions.add(Restriction.CONSTANT);
		restrictions.add(Restriction.DATA_SOURCE);
		restrictions.add(Restriction.DATA_SOURCE_TYPE);
		restrictions.add(Restriction.RESOURCE);
		restrictions.add(Restriction.RESOURCE_TYPE);
//		restrictions.add(Restriction.IMPORT);
		
		return restrictions;
	}
}
