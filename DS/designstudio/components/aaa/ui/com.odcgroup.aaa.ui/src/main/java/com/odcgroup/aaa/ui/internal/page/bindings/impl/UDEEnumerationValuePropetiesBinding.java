package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfEnumValue;

public class UDEEnumerationValuePropetiesBinding extends
		AbstractUDEPropertiesBinding {

	private MdfEnumValue model;
	private Text permittedValueNameField;
	private Text permittedValueRankField;
	
	public UDEEnumerationValuePropetiesBinding(MdfEnumValue model,
			Text permittedValueNameField, Text permittedValueRankField) {
		this.model = model;
		this.permittedValueNameField = permittedValueNameField;
		this.permittedValueRankField = permittedValueRankField;
	}

	@Override
	public String getDescription() {
		return Messages.getString("aaa.mdf.metadict.enumerationvalue.decription");
	}

	@Override
	public void doUpdateModelToText() {
		permittedValueNameField.setText(nullSafeTrimmedString(AAAAspect.getTripleAPermittedValueName(model)));
		permittedValueRankField.setText(nullSafeTrimmedString(AAAAspect.getTripleAPermittedValueRank(model)));
	}

	@Override
	public void doUpdateTextToModel(boolean modifyEvent) {
		AAAAspectDS.setTripleAPermittedValueName(model, nullIfEmptyOrTrimmed(permittedValueNameField.getText()));
		AAAAspectDS.setTripleAPermittedValueRank(model, nullIfEmptyOrTrimmed(permittedValueRankField.getText()));
	}

}
