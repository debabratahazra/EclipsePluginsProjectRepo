package com.odcgroup.translation.ui.editor.controls;

import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider;
import com.odcgroup.workbench.core.IOfsProject;

/**
 *
 * @author pkk
 *
 */
public interface ITranslationFilterControl {
	
	/**
	 * @param ofsProject
	 * @param composite
	 * @return
	 */
	List<Button> createAdditionalFilterControls(IOfsProject ofsProject, Composite composite);
	
	/**
	 * @param collectionProvider
	 * @param site
	 * @param ofsProject
	 * @param tabFolder
	 * @return
	 */
	TranslationTab createTranslationTab(ITranslationCollectorProvider collectionProvider, IWorkbenchPartSite site, IOfsProject ofsProject, CTabFolder tabFolder);
	
	
	/**
	 * @param allProviders
	 * @param site
	 * @param ofsProject
	 * @param tabFolder
	 * @return
	 */
	TranslationTab createTranslationTab(List<ITranslationCollectorProvider> allProviders, IWorkbenchPartSite site, IOfsProject ofsProject, CTabFolder tabFolder);
	
	/**
	 * @param folder
	 * @param empty
	 * @param dep
	 * @param search
	 * @param otherFilters
	 */
	void updateFilters(CTabFolder folder, boolean empty, boolean dep, String search, List<Button> otherFilters);
}
