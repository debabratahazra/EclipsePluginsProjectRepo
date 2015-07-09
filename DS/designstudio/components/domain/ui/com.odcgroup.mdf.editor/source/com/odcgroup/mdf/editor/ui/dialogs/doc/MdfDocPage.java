package com.odcgroup.mdf.editor.ui.dialogs.doc;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.MdfDocValidator;


public class MdfDocPage extends DialogPage
	implements ModifyListener {
	private Text documentationText;
	private final MdfModelElement element;


	/**
	 * Constructor for SampleNewWizardPage.
	 * @param pageName
	 */
	public MdfDocPage(MdfModelElement element) {
		super();
		setTitle("Documentation");
		setDescription("Edits the documentation for this element.");
		this.element = element;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		WidgetFactory factory = getWidgetFactory();

		Composite container = factory.createComposite(parent);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new GridLayout(1, true));

		documentationText = factory.createText(container, null, SWT.MULTI | SWT.WRAP);
		documentationText.setLayoutData(new GridData(GridData.FILL_BOTH));

		setText(documentationText, StringEscapeUtils.unescapeHtml(element.getDocumentation()));
		updateStatus();

		documentationText.addModifyListener(this);

		setControl(container);
	}

	public void doSave(MdfModelElement model) {
        String documentation = StringEscapeUtils.escapeHtml(documentationText.getText().trim());
        if (documentation.length() == 0) documentation = null;
		ModelFactory.INSTANCE.setDocumentation(model, documentation);
	}

	private void updateStatus() {
		String doc = documentationText.getText();
		setStatus(MdfDocValidator.validate(null, doc));
	}

	/**
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
		setDirty(true);
		updateStatus();
	}
}