package com.odcgroup.process.generation.translation;

import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.localization.ProcessLocalizationUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.provider.BaseTranslationkey;

/**
 * @author atr
 */
class TaskTranslationKey extends BaseTranslationkey {

	/** */
	private String prefix;

	@Override
	protected String getKeyPrefix() {
		if (prefix == null) {
			Task task = (Task)getTranslation().getOwner();
			Process process = ProcessLocalizationUtils.getProcess(task);
			StringBuilder builder = new StringBuilder();
			builder.append("process.task.");
			String processName = process.getName();
			if (processName != null) {
				builder.append(process.getName().toLowerCase());
			} else {
				builder.append("undefinedProcessName");
			}
			builder.append(".");
			String taskName = task.getID();
			if (taskName != null) {
				builder.append(taskName.toLowerCase());
			} else {
				builder.append("undefinedTaskName");
			}
			prefix = builder.toString();
		}
		return prefix;
	}

	@Override
	protected String getKeySuffix(ITranslationKind kind) {
		String suffix = "";
		switch (kind) {
			case NAME:
				suffix = "name";
				break;
			case TEXT:
				suffix = "description";
				break;
		}
		return suffix;		
	}	
	/**
	 * @param translation
	 */
	public TaskTranslationKey(ITranslation translation) {
		super(translation);
	}

}
