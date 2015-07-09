package com.odcgroup.translation.ui.internal.views.provider;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.translation.ui.editor.controls.ITranslationFilterControl;
import com.odcgroup.translation.ui.editor.controls.StandardMultiTranslationTable;
import com.odcgroup.translation.ui.editor.model.IMultiTranslationTable;
import com.odcgroup.translation.ui.internal.views.StandardTranslationTable;
import com.odcgroup.translation.ui.internal.views.StandardTranslationText;
import com.odcgroup.translation.ui.views.ITranslationTable;
import com.odcgroup.translation.ui.views.ITranslationText;
import com.odcgroup.translation.ui.views.provider.BaseTranslationTableProvider;

/**
 * The factory 
 *
 * @author atr
 */
public class TranslationTableProvider extends BaseTranslationTableProvider  {

	@Override
	public ITranslationTable getTranslationTable(IProject project, Composite parent) {
		return new StandardTranslationTable(project, parent);
	}
	
	@Override
	public IMultiTranslationTable getMultiTranslationTable() {
		return new StandardMultiTranslationTable();
	}
	
	@Override
	public ITranslationText getTranslationText(IProject project, Composite parent) {
		return new StandardTranslationText(project, parent);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.views.ITranslationTableProvider#getFilterControl()
	 */
	@Override
	public ITranslationFilterControl getFilterControl() {
		return null;
	}

}
