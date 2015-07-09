package com.odcgroup.domain.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;

public class DomainDSLContentOutlinePage extends OutlinePage {
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		getTreeViewer().expandToLevel(2);
	}
}
