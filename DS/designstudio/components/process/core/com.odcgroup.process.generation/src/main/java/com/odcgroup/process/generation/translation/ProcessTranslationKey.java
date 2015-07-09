package com.odcgroup.process.generation.translation;

import com.odcgroup.process.model.Process;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.provider.BaseTranslationkey;

/**
 * @author atr
 */
class ProcessTranslationKey extends BaseTranslationkey {

	/** */
	private String prefix;

	@Override
	protected String getKeyPrefix() {
		if (prefix == null) {
			Process process = (Process)getTranslation().getOwner();
			StringBuilder builder = new StringBuilder();
			builder.append("process.");
			String processName = process.getName();
			if (processName != null) {
				builder.append(process.getName().toLowerCase());
			} else {
				builder.append("undefined");
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
	public ProcessTranslationKey(ITranslation translation) {
		super(translation);
	}
}
