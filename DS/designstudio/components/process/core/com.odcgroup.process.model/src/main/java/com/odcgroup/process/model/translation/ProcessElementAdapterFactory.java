package com.odcgroup.process.model.translation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.Task;

/**
 * @author yan
 */
public class ProcessElementAdapterFactory {

	public static IProcessElementAdapter getAdapter(final Object obj) {
		if (obj instanceof Task) {
			return buildAdapter((Task)obj);
		} else if (obj instanceof Process) {
			return buildAdapter((Process)obj);
		} else {
			return null;
		}
	}
	public static IProcessElementAdapter buildAdapter(final Task task) {
		return new IProcessElementAdapter() {
			public EObject getAdaptee() {
				return task;
			}
			@SuppressWarnings("unchecked")
			public EList getTranslations() {
				return task.getTranslations();
			}
		};
	}
	
	public static IProcessElementAdapter buildAdapter(final Process process) {
		return new IProcessElementAdapter() {
			public EObject getAdaptee() {
				return process;
			}
			@SuppressWarnings("unchecked")
			public EList getTranslations() {
				return process.getTranslations();
			}
		};
	}


}
