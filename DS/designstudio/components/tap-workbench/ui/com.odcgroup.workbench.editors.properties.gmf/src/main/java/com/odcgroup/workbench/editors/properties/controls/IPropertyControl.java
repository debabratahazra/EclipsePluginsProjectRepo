package com.odcgroup.workbench.editors.properties.controls;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.widgets.Composite;

public interface IPropertyControl {

	public ICommand getUpdateCommand(Object control);
	
	public void updateChanges(Object control);	
	
	public Composite getControl();
}
