package com.odcgroup.process.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.Task;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class ProcessTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	public BaseTranslation newTranslation(IProject project, Object obj) {
		if (obj instanceof Task) {
			final Task task = (Task)obj;
			return new ProcessTranslation(this, 
					project,
					ProcessElementAdapterFactory.buildAdapter(task));
		} else if (obj instanceof Process) {
			final Process process = (Process)obj;
			return new ProcessTranslation(this,
					project,
					ProcessElementAdapterFactory.buildAdapter(process));
		} else {
			throw new IllegalArgumentException("ProcessTranslationProvider doesn't support " + obj + " input");
		}
	}
	
}
