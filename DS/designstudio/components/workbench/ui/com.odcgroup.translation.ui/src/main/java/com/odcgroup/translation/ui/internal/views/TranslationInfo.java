package com.odcgroup.translation.ui.internal.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.odcgroup.translation.ui.views.ITranslationInfo;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * Display an information message regarding the current selected message in the
 * translation table. It listen to information change in the translation model
 * 
 * @author atr
 */
public class TranslationInfo implements ITranslationInfo, PropertyChangeListener {

	/** The translations */
	private ITranslationModel model;

	/** */
	private Label information;

	/**
	 * @param parent
	 */
	private void createControls(Composite parent) {
		information = new Label(parent, SWT.NONE);
		information.setLayoutData(new GridData(GridData.FILL_BOTH));
		information.setBackground(parent.getBackground());
	}

	/**
	 * 
	 */
	protected void refresh() {
		if (model != null) {
			information.setText(model.getInformation());
		} else {
			information.setText("");
		}
	}

	public void setTranslationModel(ITranslationModel newModel) {
		if (null == newModel) {
			throw new IllegalArgumentException("Argument cannot be null");
		}

		if (this.model != null) {
			this.model.removePropertyChangeListener(ITranslationModel.TRANSLATION_INFO_PROPERTY, this);
		}

		this.model = newModel;
		this.model.addPropertyChangeListener(ITranslationModel.TRANSLATION_INFO_PROPERTY, this);
		refresh();
	}

	/**
	 * 
	 */
	public void dispose() {
		if (model != null) {
			model.removePropertyChangeListener(ITranslationModel.TRANSLATION_INFO_PROPERTY, this);
			model = null;
		}
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		refresh();
	}

	/**
	 * @param model
	 * @param parent
	 */
	public TranslationInfo(Composite parent) {
		createControls(parent);
	}

}
