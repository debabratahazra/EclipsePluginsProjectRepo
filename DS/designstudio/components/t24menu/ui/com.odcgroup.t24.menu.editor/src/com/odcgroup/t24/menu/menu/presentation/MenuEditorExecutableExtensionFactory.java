package com.odcgroup.t24.menu.menu.presentation;

import org.osgi.framework.Bundle;

import com.odcgroup.t24.menu.ui.MenuExecutableExtensionFactory;

/**
 * TODO: Document me!
 *
 * @author atripod
 *
 */
public class MenuEditorExecutableExtensionFactory extends MenuExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return MenuEditorPlugin.getPlugin().getBundle();
	}

}
