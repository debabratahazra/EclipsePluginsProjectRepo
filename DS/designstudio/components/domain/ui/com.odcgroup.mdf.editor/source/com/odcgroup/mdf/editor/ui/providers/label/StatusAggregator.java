package com.odcgroup.mdf.editor.ui.providers.label;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.ValidationListener;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class StatusAggregator implements ValidationListener {
	private final MultiStatus status;

	/**
	 * Constructor for StatusAggregator
	 */
	public StatusAggregator() {
		super();
		status = new MultiStatus(MdfCore.PLUGIN_ID, -1, "", null);
	}

	/**
	 * @see com.odcgroup.mdf.editor.model.validation.ValidationListener#accept(com.odcgroup.mdf.metamodel.MdfModelElement, org.eclipse.core.runtime.IStatus)
	 */
	public boolean onValidation(MdfModelElement model, IStatus status) {
		this.status.merge(status);
		return true;
	}

	/**
	 * Returns the aggregated status
	 *
	 * @return IStatus - the aggregated status
	 */
	public IStatus getStatus() {
		return status;
	}

}
