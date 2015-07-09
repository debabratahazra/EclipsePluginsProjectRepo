package com.odcgroup.translation.ui.internal.views;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public class TranslationDialog extends BaseTranslationDialog {
	
	private ITranslationModel model;
	private Text msgField;
	private Font font;
	private String text;
	
	private void updateButtons() {
		Button ok = getButton(IDialogConstants.OK_ID);
		if (ok != null) {
			ok.setEnabled(StringUtils.isNotBlank(msgField.getText()));
		}
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		if (!model.isReadOnly()) {
			createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		}
		updateButtons();
	}
	
	@Override
	protected Button getButton(int id) {
		if (IDialogConstants.CANCEL_ID == id) {
			if (model.isReadOnly())
				return null;
		}
		return super.getButton(id);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Add/Edit Translation"); //$NON-NLS-1$
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite body = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(body, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 15;
		layout.marginHeight = 10;
		layout.numColumns = 2;	
		composite.setLayout(layout);
		
//		Label nlabel = createLabel(composite, "Tech. Info");  //$NON-NLS-1$
//		nlabel.setToolTipText("Technical Information"); //$NON-NLS-1$
//		locField = createReadOnly(composite);
//		locField.setEditable(false);
//		locField.setText("What should be the value of this field ??"); //$NON-NLS-1$
		
		createLabel(composite, "Language");  //$NON-NLS-1$
		Text loc = createReadOnly(composite);
		loc.setEditable(false);
		loc.setText(LanguageUtils.getShortDisplayString(model.getSelectedLocale(), Locale.ENGLISH));
		
		Label vlabel = createLabel(composite, "Translation");  //$NON-NLS-1$
		vlabel.setToolTipText("Add/Edit Translation for "+LanguageUtils.getShortDisplayString(model.getSelectedLocale(), Locale.ENGLISH)); //$NON-NLS-1$
		msgField = createTextField(composite);
		msgField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
            	if (null == msgField.getText()) {
            		msgField.setText("");
            	}
            }
        });
		msgField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateButtons();
			}
        });
		msgField.setFont(font);
//		if (model.getTranslation().isInheritable()) {
//			ITranslationKind kind = model.getSelectedKind();
//			Locale locale = model.getSelectedLocale();
//			try {
//				if (model.getTranslation().isInherited(kind, locale)) {
//					msgField.setForeground(ColorConstants.blue);
//				} else {
//					msgField.setForeground(ColorConstants.black);
//				}
//			} catch (TranslationException ex) {
//				msgField.setForeground(ColorConstants.black);
//			}
//		}
		String msg = model.getText();
		if (null == msg) msg = "";
		msgField.setText(msg);
		msgField.setEditable(!model.isReadOnly());
		msgField.setFocus();
		
		return composite;	
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);		
		shell.setText("Translation"); //$NON-NLS-1$
	}
	
	@Override
	protected void okPressed() {
		if (msgField.getText() != null){
			text = msgField.getText();
			super.okPressed();
		} else {
			setErrorMessage("Missing Translation");  //$NON-NLS-1$
		}
	}
	
	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.widthHint = 100;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Text createTextField(Composite parent) {
		Text description = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 300;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.heightHint = 150;
		description.setLayoutData(gridData);
		return description;	
	}
	
	/**
	 * @param parent
	 * @return
	 */
	protected Text createReadOnly(Composite parent){
		Text text = new Text(parent, SWT.SINGLE);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = GridData.CENTER;
		data.grabExcessVerticalSpace = false;
		text.setLayoutData(data);
		text.setEnabled(false);
		return text;
	}
	
	public String getText() {
		return this.text;
	}

	/**
	 * @param parentShell
	 * @param model
	 * @param font
	 */
	public TranslationDialog(Shell parentShell, ITranslationModel model, Font font) {
		super(parentShell);
		if (model == null) {
			throw new IllegalArgumentException("Model cannot be null"); //$NON-NLS-1$
		}
		if (font == null) {
			throw new IllegalArgumentException("Font cannot be null"); //$NON-NLS-1$
		}
		this.model = model;
		this.font = font;
	}
	
}
