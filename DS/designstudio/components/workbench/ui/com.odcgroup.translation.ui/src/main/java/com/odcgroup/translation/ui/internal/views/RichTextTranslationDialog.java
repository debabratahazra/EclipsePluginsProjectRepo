package com.odcgroup.translation.ui.internal.views;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
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
import org.xml.sax.SAXException;

import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.ui.internal.views.richtext.SimpleRichTextEditor;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public class RichTextTranslationDialog extends BaseTranslationDialog {
	
	private ITranslationModel model;
	private Font font;
	private SimpleRichTextEditor editor;
	private String text;
	
	private void updateButtons() {
		Button ok = getButton(IDialogConstants.OK_ID);
		if (ok != null) {
			ok.setEnabled(StringUtils.isNotBlank(editor.getText(false)));
		}
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
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
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));	
		
		createLabel(composite, "Language:");  //$NON-NLS-1$
		Text loc = createReadOnly(composite);
		loc.setEditable(false);
		loc.setText(LanguageUtils.getShortDisplayString(model.getSelectedLocale(), Locale.ENGLISH));
		
		Label vlabel = createLabel(composite, "Translation:");  //$NON-NLS-1$
		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gridData.verticalIndent = 40;
		vlabel.setLayoutData(gridData);
		
		vlabel.setToolTipText("Add/Edit Translation for "+LanguageUtils.getShortDisplayString(model.getSelectedLocale(), Locale.ENGLISH)); //$NON-NLS-1$
		
		editor = new SimpleRichTextEditor(composite, font);
		editor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateButtons();
			}
        });
		String msg = model.getText();
		if (null == msg) msg = "";
		
		boolean isValid = true;
		
		if (RichTextUtils.isRichRext(msg)) {

			// ========================================================================
			// WARNING - if "msg" contains a string similar to this one:
			// <rt>&lt;rt&gt;&lt;p toto&gt;<s ts="b">xxx</s>&lt;/p&gt;&lt;/rt&gt;</rt>
			// The rich-text validator will raise an exception. 
			// The first "&lt;rt&gt;" just after <rt> MUST NOT be unescaped
			// The last  "&lt;/rt&gt;" just before </rt> MUST NOT be unescaped
			// =========================================================================
			
			
			msg = StringEscapeUtils.unescapeXml(msg);		
			// =========================================================================
			
			try {
				RichTextUtils.validateRichText(msg);
			} catch (SAXException ex) {
				String error = ex.getMessage();
				int pos = error.indexOf(":");
				if (pos != -1) {
					error = error.substring(pos);
				}
				String message = "The translation contains invalid rich text decorators that need to be fixed.";
				setMessage(message+'\n'+error, IMessageProvider.ERROR);
				isValid = false;
			} catch (IOException ex) {
				setMessage(ex.getMessage());
				ex.printStackTrace();
				isValid = false;
			}
		}
		
		try {
			editor.setText(msg, isValid);
			editor.setEditable(!model.isReadOnly());
		} catch (Exception ex) {
			// Display an error message, and disable the editor
			// to avoid the edition of the text.
			setMessage(ex.getMessage(), IMessageProvider.ERROR);
			editor.setEditable(false);
		}
		return composite;	
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);		
		shell.setText("Translation"); //$NON-NLS-1$
		shell.setSize(640 /*width*/,500 /*height*/);
	}
	
	@Override
	protected void okPressed() {
		if (editor.getText(false) != null){
			text = editor.getText(true);
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
	public RichTextTranslationDialog(Shell parentShell, ITranslationModel model, Font font) {
		super
		(parentShell);
		if (model == null) {
			throw new IllegalArgumentException("Model cannot be null"); //$NON-NLS-1$
		}
		if (font == null) {
			throw new IllegalArgumentException("Font cannot be null"); //$NON-NLS-1$
		}
		setShellStyle(this.getShellStyle() | SWT.RESIZE);
		this.model = model;
		this.font = font;
	}
	
}
