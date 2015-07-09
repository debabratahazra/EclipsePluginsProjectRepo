package com.odcgroup.t24.enquiry.properties.celleditors;

import java.text.MessageFormat;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.ui.util.FieldHelper;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public abstract class ExtendedTextDialogCellEditor extends ExtendedDialogCellEditor{

	private Text textField;
	private EObject model;

	public ExtendedTextDialogCellEditor(Composite composite, ILabelProvider labelProvider) {
		super(composite, labelProvider);
		textField.addMouseListener(doubleClickListener());
	}
	
	public ExtendedTextDialogCellEditor(Composite composite, ILabelProvider labelProvider, EObject model) {
		super(composite, labelProvider);
		textField.addMouseListener(doubleClickListener());
		this.model = model;
	}

	/**
	 * @return the textField
	 */
	public Text getTextField() {
		return textField;
	}

	/**
	 * @param composite
	 * @param labelProvider
	 * @param isDoubleClickEnabled
	 */
	public ExtendedTextDialogCellEditor(Composite composite, ILabelProvider labelProvider, boolean isDoubleClickEnabled) {
		super(composite, labelProvider);
		if(isDoubleClickEnabled){
			textField.addMouseListener(doubleClickListener());
		}
	}

	@Override
	protected Control createContents(Composite cell) {
		
		textField = new Text(cell, SWT.NONE);
		textField.setFont(cell.getFont());
		textField.setBackground(cell.getBackground());
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyReleaseOccured(e);
			}
		});
		textField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent event) {
				setValueToModel();
				fireApplyEditorValue();
			}
		});
		cell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textField.setFocus();
				textField.selectAll();
			}
		});	
		return textField;
	}

	/**
	 * @return
	 */
	private MouseAdapter doubleClickListener() {
		return new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				Object newValue = openDialogBox(textField.getParent());
				doSetValue(newValue);
			}
		};
	}
	
	protected void updateContents(Object value) {
		if (textField == null) {
			return;
		}

		String text = "";
		if (value != null) {
			text = value.toString();
		}
		textField.setText(text);		
	}

	protected void doSetFocus() {
		
	}

	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.character == SWT.CR || keyEvent.keyCode == SWT.KEYPAD_CR) {
			setValueToModel();
		}
		
		super.keyReleaseOccured(keyEvent);
	}

	protected void setValueToModel() {
		String newValue = textField.getText();
		boolean newValidState = isCorrect(newValue);
		if (newValidState) {
			markDirty();
			doSetValue(newValue);
			if (model!=null && (model instanceof Field)) {
				FieldHelper fieldHelper = new FieldHelper();
				fieldHelper.resetField(model, newValue);
			}
		} else {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { newValue.toString() }));
		}
	}

}
