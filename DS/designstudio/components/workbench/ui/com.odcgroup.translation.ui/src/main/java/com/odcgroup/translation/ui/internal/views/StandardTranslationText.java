package com.odcgroup.translation.ui.internal.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.translation.ui.views.table.TranslationText;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class StandardTranslationText extends TranslationText {

	/**
	 * @param project
	 * @param parent
	 */
	public StandardTranslationText(IProject project, Composite parent) {
		super(project, parent);
	}

	@Override
	protected TranslationLanguageContentProvider createTranslationLanguageContentProvider() {
		return new TranslationLanguageContentProvider();
	}

	@Override
	protected TranslationLanguageLabelProvider createTranslationLanguageLabelProvider() {
		return new TranslationLanguageLabelProvider();
	}

	@Override
	public void hideButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideOrigin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showOrigin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideTable() {
		// TODO Auto-generated method stub
		
	}
	
}
