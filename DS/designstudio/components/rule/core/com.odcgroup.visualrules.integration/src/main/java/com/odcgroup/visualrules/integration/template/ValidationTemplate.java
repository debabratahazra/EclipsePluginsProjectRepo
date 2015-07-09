package com.odcgroup.visualrules.integration.template;

import java.util.ArrayList;
import java.util.Collection;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.helper.StringHelper;

import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.Parameter;

public class ValidationTemplate extends Template {
	
	protected MdfModelElement modelElement;

	public void init(MdfModelElement modelElement) {
		this.modelElement = modelElement;
	}
	
	@Override
	public String getDesc() {
		if(modelElement==null) {
			return null;
		}
		return name + " rule for the data type " + modelElement.getName();
	}
	
	@Override
	public Parameter[] getParams() {
		if(modelElement==null) {
			return null;
		}
		Collection<Parameter> params = new ArrayList<Parameter>();
		Parameter param = IntegrationFactory.eINSTANCE.createParameter();
		param.setName(StringHelper.toFirstLower(modelElement.getName()));
		param.setDescription("the " + modelElement.getName() + " instance as input to operate on");
		param.setTypeName(modelElement.getName());
		param.setInput(true);
		params.add(param);
		
		return params.toArray(new Parameter[0]);
	}
}
