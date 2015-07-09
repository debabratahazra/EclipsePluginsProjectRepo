package com.odcgroup.aaa.ui.internal.page.bindings;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public interface IUDEPropertiesViewAdapter {

	Text getEntitySQLNameField();
	Text getEntityNameField();
	Text getAttributeSQLNameField();
	Text getAttributeNameField();
	Text getAttributeDataTypeField();
	Text getPermittedValueNameField();
	Text getPermittedValueRankField();
	Text getParentTypeAttribute();
	Text getAttributeOwnerOfEnum();
	Button getSecuredButton();

}
