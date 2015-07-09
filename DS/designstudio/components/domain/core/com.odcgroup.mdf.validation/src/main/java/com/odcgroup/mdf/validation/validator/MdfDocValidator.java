package com.odcgroup.mdf.validation.validator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.MdfValidationCore;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfDocValidator implements ModelValidator {

	private ValidationListener listener = null;

	/**
	 * Constructor for MdfDocValidator
	 */
	public MdfDocValidator() {
		super();
	}

	public void setValidationListener(ValidationListener listener) {
		this.listener = listener;
	}

	/**
	 * @see com.odcgroup.mdf.editor.model.ModelVisitor#accept(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public boolean accept(MdfModelElement model) {
		return listener.onValidation(model, validate(model));
	}

	public static IStatus validate(MdfModelElement model) {
        /*
		String documentation = model.getDocumentation();
		if (documentation == null) {
			return makeStatus(model);
		} else {
            return validate(model, documentation);
		}
        */
        
        return MdfValidationCore.STATUS_OK;
	}

	public static IStatus validate(MdfModelElement model, String doc) {
		doc = doc.trim();

		if (doc.length() == 0) {
			return makeStatus(model);
		} else {
			doc = doc.toLowerCase();
			if (doc.startsWith("todo")) {
				return makeStatus(model);
			}
		}

		return MdfValidationCore.STATUS_OK;
	}

	private static IStatus makeStatus(MdfModelElement model) {
		if (model != null) {
			return new Status(
				IStatus.WARNING,
				MdfValidationCore.PLUGIN_ID,
				-1,
				"Documentation is missing or incomplete for element "
					+ model.getQualifiedName(),
				null);
		} else {
			return new Status(
				IStatus.WARNING,
				MdfValidationCore.PLUGIN_ID,
				-1,
				"Documentation is missing or incomplete",
				null);
		}
	}
}
