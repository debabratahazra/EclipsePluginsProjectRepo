package com.odcgroup.mdf.editor;

import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfAttributePage;
import com.odcgroup.mdf.metamodel.MdfAttribute;

public interface MdfAnnotationProvider {
	public MdfAttributePage getAttributePage(MdfAttribute attribute);
}
