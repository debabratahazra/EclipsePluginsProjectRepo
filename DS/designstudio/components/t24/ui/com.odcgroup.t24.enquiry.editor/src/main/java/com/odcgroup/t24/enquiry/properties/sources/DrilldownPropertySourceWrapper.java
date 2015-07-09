package com.odcgroup.t24.enquiry.properties.sources;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.google.common.collect.Lists;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownStringType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;
import com.odcgroup.t24.enquiry.enquiry.Parameters;

/**
 *
 * @author phanikumark
 */
public class DrilldownPropertySourceWrapper implements IPropertySource {
	
	private IPropertySource source;
	private IPropertySourceProvider sourceProvider;
	private IPropertySource paramSource;
	private DrillDown model;
	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;
	private Parameters param;
	
	public DrilldownPropertySourceWrapper(IPropertySource source, IPropertySourceProvider sourceProvider, EObject model) {
		this.source = source;
		this.sourceProvider = sourceProvider;
		this.model = (DrillDown) model;		
		initPropertySources();
	}
	
	private void initPropertySources() {
		param = model.getParameters();
		if (param == null) {
			param = EnquiryFactory.eINSTANCE.createParameters();
		}
		paramSource = sourceProvider.getPropertySource(param);
	}

	@Override
	public Object getEditableValue() {
		return source.getEditableValue();
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return source.getPropertyDescriptors();
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(pkg.getDrillDown_Type())) {
			DrillDownType type = model.getType();
			if (type != null) {
				String val = type.getProperty()+" ";
				if (type instanceof DrillDownStringType) {
					val += ((DrillDownStringType) type).getValue();
				} else if (type instanceof BlankType){
					val += ((BlankType) type).getValue().toString();
				}
				return val;				
			} else {
				return "";
			}
		} else if (id.equals(pkg.getDrillDown_LabelField())) {
			return source.getPropertyValue(id);
		} else if (id.equals(pkg.getDrillDown_Image().getName())) {
			return source.getPropertyValue(id);
		}

		Parameters  param = model.getParameters();
		if (param != null) {			
			if (id.equals(pkg.getParameters_Function())) {
				FunctionKind type = model.getParameters().getFunction();				
				return FunctionKind.get(type.getValue());
			} else if (id.equals(pkg.getParameters_Auto())) {
				return model.getParameters().isAuto();		
			} else if (id.equals(pkg.getParameters_Variable())) {
				List<String> params =  model.getParameters().getVariable();
				return StringUtils.join(params, ',');					
			} else if (id.equals(pkg.getParameters_RunImmediately())) {
				return model.getParameters().isRunImmediately();			
			} else if (id.equals(pkg.getParameters_PwActivity())) {
				return model.getParameters().getPwActivity();			
			} else if (id.equals(pkg.getParameters_FieldName())) {
				List<String> list = model.getParameters().getFieldName();
				return StringUtils.join(list, ',');					
			}
		} else {
			return "";
		}
		return source.getPropertyValue(id);
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals(pkg.getParameters_Function()) 
				|| id.equals(pkg.getParameters_Auto()) 
				|| id.equals(pkg.getParameters_Variable()) 
				|| id.equals(pkg.getParameters_RunImmediately())
				|| id.equals(pkg.getParameters_PwActivity())
				|| id.equals(pkg.getParameters_FieldName())) {
			return paramSource.isPropertySet(id);
		} else if (id.equals(pkg.getDrillDown_Type())) {
			return true;
		}
		return source.isPropertySet(id);
	}

	@Override
	public void resetPropertyValue(Object id) {
		source.resetPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id.equals(pkg.getParameters_Function()) 
				|| id.equals(pkg.getParameters_Auto()) 
				|| id.equals(pkg.getParameters_Variable()) 
				|| id.equals(pkg.getParameters_RunImmediately())
				|| id.equals(pkg.getParameters_PwActivity())
				|| id.equals(pkg.getParameters_FieldName())) {
			Parameters  parameters = model.getParameters();
			if (parameters == null) {
				model.setParameters(param);
			}
			if (id.equals(pkg.getParameters_RunImmediately())) {
				boolean val = (value != null && value.equals("true")) ? true : false;
				model.getParameters().setRunImmediately(val);
			} else if(id.equals(pkg.getParameters_Auto())) { 
				boolean val = false;
				if (value != null) {
					val = (value.equals("true")) ? true : false;
				}
				model.getParameters().setAuto(val);				
			} else if (id.equals(pkg.getParameters_Variable()) 
					|| id.equals(pkg.getParameters_FieldName())) {
				if (value != null) {
					if (value instanceof List) {
						paramSource.setPropertyValue(id, value);
					} else {
						String val = (String) value;
						List<String> list = Lists.newArrayList(val.split(","));
						paramSource.setPropertyValue(id, list);
					}
				}
			} else {
				if (value != null)
					paramSource.setPropertyValue(id, value);
			}
		} else if (id.equals(pkg.getDrillDown_Type())) { 
			model.setType((DrillDownType)value);
		} else {
			source.setPropertyValue(id, value);
		}
	}

}
