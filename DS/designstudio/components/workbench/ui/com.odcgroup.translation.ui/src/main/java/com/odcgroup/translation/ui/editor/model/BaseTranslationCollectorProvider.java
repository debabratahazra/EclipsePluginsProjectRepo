package com.odcgroup.translation.ui.editor.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;

/**
 *
 * @author pkk
 *
 */
public abstract class BaseTranslationCollectorProvider implements ITranslationCollectorProvider {
	
	private String displayName;
	private List<String> extensionList;
	private boolean defaultActive;

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationCollectorProvider#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationCollectorProvider#getModelExtensions()
	 */
	@Override
	public String[] getModelExtensions() {
		return extensionList.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationCollectorProvider#isDefaultActivated()
	 */
	@Override
	public boolean isDefaultActivated() {
		return defaultActive;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationCollectorProvider#setModelExtensions(java.lang.String)
	 */
	@Override
	public void setModelExtensions(String modelExtensions) {
		String[] extns = modelExtensions.split(",");
		extensionList = new ArrayList<String>();
		for (String string : extns) {
			extensionList.add(string.trim());
		}		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationCollectorProvider#setDefaultActivated(boolean)
	 */
	@Override
	public void setDefaultActivated(boolean defaultActive) {
		this.defaultActive = defaultActive;		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationCollectorProvider#setDisplayName(java.lang.String)
	 */
	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider#getModelLabelProvider()
	 */
	@Override
	public LabelProvider getModelLabelProvider() {
		return null;
	}
	

}
