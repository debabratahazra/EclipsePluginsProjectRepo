package com.odcgroup.t24.menu.editor.commands;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.presentation.MenuEditor;

public class SwitchEMFResourceCommand extends AbstractCommand {
	
	private MenuEditor editor;
	private MenuRoot menuEMFOld;
	private MenuRoot menuXtextNew;
	private MenuRoot menuXtextNewCopy;
	private EList<EObject> contents;

	public SwitchEMFResourceCommand(MenuEditor editor, EList<EObject> contents, MenuRoot menuEMF,
			MenuRoot menuXtext) {
		super();
		this.editor = editor;
		this.contents = contents;
		this.menuEMFOld = menuEMF;
		this.menuXtextNew = menuXtext;

		setLabel("changed in xtext editor");
	}

	@Override
	protected boolean prepare() {
		isExecutable = true;
		return true;
	}

	@Override
	public void execute() {
		// create copy of new xtext contents
		if (menuXtextNewCopy == null)
			menuXtextNewCopy = EcoreUtil.copy(menuXtextNew);

		// replace old emf with copy of new xtext
		contents.clear();
		contents.add(menuXtextNewCopy);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		// replace new copy of new xtext with old emf
		contents.clear();
		contents.add(menuEMFOld);

		editor.getXtextSyncHelper().updateEMFToXtext();
	}

	@Override
	public void dispose() {
		super.dispose();

		menuEMFOld = null;
		menuXtextNew = null;
		contents = null;
		menuXtextNewCopy = null;
	}

}
