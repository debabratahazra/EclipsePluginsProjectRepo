package com.odcgroup.integrationfwk.ui.model;

import javax.xml.bind.annotation.XmlElement;

public abstract class ExitPointType {

	public ExitPointType()
	{
		
	}
	@XmlElement(name = "ContractName")
	public abstract String getContractName();

	public abstract void setContractName(String name); 
	
	@XmlElement(name = "ExitPoint")
	public abstract String getExitPoint(); 

	public abstract void setExitPoint(String exitPoint);
	
	//@XmlElement(name = "SourceType")
	public abstract String getSourceType();
	

}
