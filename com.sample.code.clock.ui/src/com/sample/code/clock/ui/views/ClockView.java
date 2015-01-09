package com.sample.code.clock.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ClockView extends ViewPart {
	public ClockView() {
	}

	public void createPartControl(Composite parent) {
		new ClockWidget(parent, SWT.NONE);
	}

	public void setFocus() {
	}
}