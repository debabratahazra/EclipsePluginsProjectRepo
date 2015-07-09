package com.odcgroup.menu.editor.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorProvider;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;
/**
 * 
 * @author snn
 *
 */
public class MenuTranslationVisitorProvider extends
		BaseTranslationModelVisitorProvider implements
		ITranslationModelVisitorProvider {


	/**
	 * @param project, element
	 */
	protected ITranslationModelVisitor newModelVisitor(IProject project,
			Object element) {
		return new MenuTranslationVisitor(project,(MenuRoot)element);
	}

}
