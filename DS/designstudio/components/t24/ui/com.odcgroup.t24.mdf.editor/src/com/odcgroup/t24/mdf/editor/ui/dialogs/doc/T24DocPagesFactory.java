package com.odcgroup.t24.mdf.editor.ui.dialogs.doc;

import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class T24DocPagesFactory implements DialogPagesFactory {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addPages(MdfModelElement model, List pages) {
		pages.add(new T24MdfDocPage(model));			
	}

}
