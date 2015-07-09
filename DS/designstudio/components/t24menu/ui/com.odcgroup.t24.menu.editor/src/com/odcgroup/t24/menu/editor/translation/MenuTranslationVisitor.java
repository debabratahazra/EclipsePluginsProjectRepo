package com.odcgroup.t24.menu.editor.translation;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;
/**
 * 
 * @author snn
 *
 */
public class MenuTranslationVisitor implements ITranslationModelVisitor {

	private MenuRoot menuRoot = null;
	private ITranslationManager manager = null;
	private ITranslationModelVisitorHandler handler = null;
    /**
     * MenuTranslationVisitor constructor.
     * @param project
     * @param root
     */
	public MenuTranslationVisitor(IProject project, MenuRoot root) {
		this.menuRoot = root;
		this.manager = TranslationCore.getTranslationManager(project);

	}

	/**
	 * visit method calss notify the Root item and sub menu items .
	 * 
	 * @param ITranslationModelVisitorHandler
	 */
	public void visit(ITranslationModelVisitorHandler handler) {
		this.handler = handler;
		MenuItem rootItem = menuRoot.getRootItem();
		ITranslation translation = manager.getTranslation(rootItem);
		if (translation != null) {
			handler.notify(translation);
		}
		visit(rootItem.getSubmenus());
	}

	/**
	 * vist method notify the sub menu items.
	 * 
	 * @param items
	 */
	private void visit(List<MenuItem> items) {
		for (MenuItem menu : items) {
			ITranslation translation = manager.getTranslation(menu);
			if (translation != null) {
				handler.notify(translation);
			}
			visit(menu.getSubmenus());
		}
	}

}
