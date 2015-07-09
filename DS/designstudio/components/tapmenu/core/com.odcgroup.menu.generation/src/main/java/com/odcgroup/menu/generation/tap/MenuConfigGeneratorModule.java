package com.odcgroup.menu.generation.tap;

import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

import com.odcgroup.menu.generation.tap.MenuConfig;

public class MenuConfigGeneratorModule extends AbstractGenericResourceRuntimeModule {
	
	
	public Class<? extends IMenuGenerator> bindIGenerator() {
		return MenuConfig.class;
	}

	@Override
	protected String getLanguageName() {
		return "";
	}

	@Override
	protected String getFileExtensions() {
		return "menu";
	}

}
