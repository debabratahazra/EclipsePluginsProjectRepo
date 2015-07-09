package com.odcgroup.t24.menu.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;

public class MenuTranslationModelVisitor implements ITranslationModelVisitor {
	
	/** */
	private ITranslationManager manager;
	/** */
	private MenuRoot root;
	
	/**
	 * @param project
	 * @param root
	 */
	public MenuTranslationModelVisitor(IProject project, MenuRoot root) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.root = root;
	}
	
	/**
	 * @param handler
	 * @param item
	 */
	private void visit(ITranslationModelVisitorHandler handler, MenuItem item) {
		ITranslation translation = manager.getTranslation(item);
		if (translation != null) {
			handler.notify(translation);
		}
		for (MenuItem sItem : item.getSubmenus()) {
			visit(handler, sItem);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslationModelVisitor#visit(com.odcgroup.translation.core.ITranslationModelVisitorHandler)
	 */
	public void visit(ITranslationModelVisitorHandler handler) {
		MenuItem item = root.getRootItem();
		visit(handler, item);
	}

}
