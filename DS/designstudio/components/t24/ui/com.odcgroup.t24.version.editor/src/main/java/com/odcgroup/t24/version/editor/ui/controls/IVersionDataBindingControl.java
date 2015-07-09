package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;



/**
 * @author phanikumark
 *
 */
public interface IVersionDataBindingControl {
	
	
	Composite getControl();
	
	
	void refreshControls();
	
	/**
	 * @param context
	 */
	void initDataBindings(DataBindingContext context);

}
