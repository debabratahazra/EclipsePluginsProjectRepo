package com.odcgroup.aaa.ui.internal.page.bindings;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.ui.internal.page.bindings.impl.UDEAssociationPropertiesBinding;
import com.odcgroup.aaa.ui.internal.page.bindings.impl.UDEAttributePropertiesBinding;
import com.odcgroup.aaa.ui.internal.page.bindings.impl.UDEClassPropertiesBinding;
import com.odcgroup.aaa.ui.internal.page.bindings.impl.UDEEnumerationPropertiesBinding;
import com.odcgroup.aaa.ui.internal.page.bindings.impl.UDEEnumerationValuePropetiesBinding;
import com.odcgroup.aaa.ui.internal.page.bindings.impl.UDEReverseAssociationPropertiesBinding;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

public class UDEPropertiesFactory {
	
	public IUDEPropertiesBinding createTripleAPropertiesBinding(IUDEPropertiesViewAdapter viewAdapter, MdfModelElement model) {
		if (model instanceof MdfClass) {
			return new UDEClassPropertiesBinding((MdfClass)model, viewAdapter.getEntitySQLNameField(), viewAdapter.getEntityNameField(), viewAdapter.getSecuredButton());
		} else if (model instanceof MdfAttribute) {
			return new UDEAttributePropertiesBinding((MdfAttribute)model, viewAdapter.getAttributeSQLNameField(), viewAdapter.getAttributeNameField());
		} else if (model instanceof MdfAssociation) {
			return new UDEAssociationPropertiesBinding((MdfAssociation)model, viewAdapter.getAttributeSQLNameField(), viewAdapter.getAttributeNameField(), viewAdapter.getAttributeDataTypeField(), viewAdapter.getParentTypeAttribute());
		} else if (model instanceof MdfReverseAssociation) {
			return new UDEReverseAssociationPropertiesBinding((MdfReverseAssociation)model, viewAdapter.getAttributeSQLNameField(), viewAdapter.getAttributeNameField());
		} else if (model instanceof MdfEnumeration) {
			return new UDEEnumerationPropertiesBinding((MdfEnumeration)model, viewAdapter.getAttributeOwnerOfEnum());
		} else if (model instanceof MdfEnumValue) {
			return new UDEEnumerationValuePropetiesBinding((MdfEnumValue)model, viewAdapter.getPermittedValueNameField(), viewAdapter.getPermittedValueRankField());
		}
		return null;
	}
	
	public boolean hasTripleAPropertyBinding(MdfModelElement model) {
		IUDEPropertiesViewAdapter dummy = new IUDEPropertiesViewAdapter() {
			public Text getEntitySQLNameField()      { return null; }
			public Text getEntityNameField()         { return null; }
			public Text getAttributeSQLNameField()   { return null; }
			public Text getAttributeNameField()      { return null; }
			public Text getPermittedValueNameField() { return null; }
			public Text getPermittedValueRankField() { return null; }
			public Text getParentTypeAttribute()     { return null; }
			public Text getAttributeOwnerOfEnum()    { return null; }
			public Button getSecuredButton()    { return null; }
			public Text getAttributeDataTypeField() { return null;	}
		};
		return createTripleAPropertiesBinding(dummy, model) != null;
	}

}
