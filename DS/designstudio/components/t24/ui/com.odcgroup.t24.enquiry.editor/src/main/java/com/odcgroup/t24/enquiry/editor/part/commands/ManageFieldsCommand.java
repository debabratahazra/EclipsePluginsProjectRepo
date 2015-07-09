package com.odcgroup.t24.enquiry.editor.part.commands;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.Command;

import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.ui.util.FieldHelper;

/**
 *
 * @author phanikumark
 *
 */
public class ManageFieldsCommand extends Command {
	
	private Enquiry enquiry = null;
	private List<MdfProperty> properties = null;
	private String calculatedFieldName = null;

	public ManageFieldsCommand(Enquiry enquiry) {
		this.enquiry = enquiry;
	}
	
	public Enquiry getEnquiry() {
		return enquiry;
	}

	public List<MdfProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<MdfProperty> properties) {
		this.properties = properties;
	}

	@Override
	public boolean canExecute() {
		return ((getProperties() != null && !getProperties().isEmpty()) || !StringUtils.isEmpty(calculatedFieldName));
	}

	@Override
	public void execute() {
		FieldHelper helper = new FieldHelper();
		if (!StringUtils.isEmpty(calculatedFieldName)) {
			Field field = EnquiryFactory.eINSTANCE.createField();
			field.setName(calculatedFieldName);
			enquiry.getFields().add(field);
		} else if (!getProperties().isEmpty()) {
			helper.manageFields(enquiry, getProperties().toArray(new MdfProperty[0]));			
		}
	}

	@Override
	public void redo() {
		super.redo();
	}
	
	public String getCalculatedFieldName() {
		return calculatedFieldName;
	}

	public void setCalculatedFieldName(String calculatedFieldName) {
		this.calculatedFieldName = calculatedFieldName;
	}

}
