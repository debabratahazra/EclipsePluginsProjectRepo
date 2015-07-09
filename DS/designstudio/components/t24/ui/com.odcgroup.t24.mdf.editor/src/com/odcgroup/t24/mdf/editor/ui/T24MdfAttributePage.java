package com.odcgroup.t24.mdf.editor.ui;

import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfAttributePage;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfEntity;

public class T24MdfAttributePage extends MdfAttributePage {

	public T24MdfAttributePage(MdfAttribute attr) {
		super(attr);
	}
	
	@Override
	protected void setAnnotaitons(MdfEntity entity) {
		// We do not want any AAA specific annotations in T24 EDS and hence doing nothing in this method
		// DS-5981
	}

}
