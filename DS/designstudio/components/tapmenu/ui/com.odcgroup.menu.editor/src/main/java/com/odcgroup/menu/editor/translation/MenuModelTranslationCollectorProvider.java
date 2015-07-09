package com.odcgroup.menu.editor.translation;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author snn
 *
 */
public class MenuModelTranslationCollectorProvider extends
		BaseTranslationCollectorProvider {
 
	/**
	 * MenuModelTranslationCollectorProvider constructor.
	 */
	public MenuModelTranslationCollectorProvider() {
		
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider#getTranslationCollector(com.odcgroup.workbench.core.IOfsProject)
	 */
	public ITranslationCollector getTranslationCollector(IOfsProject ofsProject) {
		return new MenuModelTranslationCollector(ofsProject, getModelExtensions());
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider#getTranslationOwnerSelector()
	 */
	public ITranslationOwnerSelector getTranslationOwnerSelector() {
		return new MenuModelTranslationSelector();
	}

}
