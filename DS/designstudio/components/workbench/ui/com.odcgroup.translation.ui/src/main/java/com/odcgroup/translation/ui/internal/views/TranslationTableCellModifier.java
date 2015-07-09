package com.odcgroup.translation.ui.internal.views;

import java.util.Locale;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationTableCellModifier;

/**
 * This the translation cell modifier.
 * 
 * @author atr
 */
public class TranslationTableCellModifier implements ITranslationTableCellModifier {
	
	/** The command to invoke to change the translation message*/
	private ITranslationCommand command;
	
	/** The translation model */
	private ITranslationModel model;

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		boolean modifiable = false;
		if (!model.isReadOnly()) {
			if (model.acceptRichText()) {
				String text = model.getText();
				modifiable = ! RichTextUtils.isRichRext(text);
			} else {
				modifiable = true;
			}
		}
		return modifiable;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		String text = model.getText((Locale) element);
		return (text != null) ? text : "";
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {
		String newText = (String)value;
			/* 
			 * we accept the new value only if a non blank text is defined.
			 */
			try {
				command.execute(newText);
			} catch (CoreException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

	@Override
	public void setCommand(ITranslationCommand command) {
		if (null == command) {
			throw new IllegalArgumentException("Argument [command] cannot be null");
		}
		this.command = command;
	}
	
	@Override
	public void setTranslationModel(ITranslationModel model) {
		if (null == model) {
			throw new IllegalArgumentException("Argument [model] cannot be null");
		}
		this.model = model;
	}

	/**
	 * Constructor
	 */
	public TranslationTableCellModifier() {
	}


}
