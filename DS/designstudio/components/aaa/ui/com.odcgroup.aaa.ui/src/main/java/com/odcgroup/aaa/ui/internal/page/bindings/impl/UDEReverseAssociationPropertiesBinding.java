package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

public class UDEReverseAssociationPropertiesBinding extends
		AbstractUDEPropertiesBinding {
	
	private MdfReverseAssociation model;
	private Text attributeSQLNameField;
	private Text attributeNameField;

	public UDEReverseAssociationPropertiesBinding(MdfReverseAssociation model,
			Text attributeSQLNameField, Text attributeNameField) {
		this.model = model;
		this.attributeSQLNameField = attributeSQLNameField;
		this.attributeNameField = attributeNameField;
	}

	@Override
	public String getDescription() {
		return Messages.getString("aaa.mdf.page.attribute.description");
	}

	@Override
	public void doUpdateModelToText() {
		attributeSQLNameField.setText(nullSafeTrimmedString(AAAAspect.getTripleAAttributeSQLName(model)));
		attributeNameField.setText(nullSafeTrimmedString(AAAAspect.getTripleAAttributeName(model)));
	}

	@Override
	public void doUpdateTextToModel(boolean modifyEvent) {
		if (modifyEvent) {
			AAAAspectDS.setTripleAAttributeSQLName(model, nullIfEmptyOrTrimmed(attributeSQLNameField.getText()));
			AAAAspectDS.setTripleAAttributeName(model, nullIfEmptyOrTrimmed(attributeNameField.getText()));			
		} else {
			String sqlName = nullIfEmptyOrTrimmed(attributeSQLNameField.getText());
			String name = nullIfEmptyOrTrimmed(attributeNameField.getText());
			boolean changed = false;
			if (StringUtils.isEmpty(sqlName)) {
				sqlName = getUDESqlName(model);
				changed = true;
			}	
			if (StringUtils.isEmpty(name)) {
				name = getUDEName(model);
				changed = true;	
			}
			AAAAspectDS.setTripleAAttributeSQLName(model, sqlName);
			AAAAspectDS.setTripleAAttributeName(model, name);	
			if (changed) {
				updateModelToText();
			}
		}
	}

}
